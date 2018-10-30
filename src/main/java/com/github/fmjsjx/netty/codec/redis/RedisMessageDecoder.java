package com.github.fmjsjx.netty.codec.redis;

import static com.github.fmjsjx.netty.codec.redis.RedisConstants.*;

import java.util.LinkedList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;

public class RedisMessageDecoder extends ByteToMessageDecoder {

	private final ToPositiveLongProcessor toPositiveLongProcessor = new ToPositiveLongProcessor();

	private State state = State.DECODE_TYPE;
	private int currentBulkStringLength;
	private LinkedList<DefaultArrayMessage<RedisMessage>> arrays = new LinkedList<>();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			for (;;) {
				if (state == State.DECODE_TYPE) {
					if (!decodeType(in)) {
						return;
					}
				} else if (state == State.DECODE_SIMPLE_STRING) {
					if (!decodeSimpleString(in, out)) {
						return;
					}
				} else if (state == State.DECODE_INTEGER) {
					if (!decodeInteger(in, out)) {
						return;
					}
				} else if (state == State.DECODE_BULK_STRING_LENGTH) {
					if (!decodeBulkStringLength(in, out)) {
						return;
					}
				} else if (state == State.DECODE_BULK_STRING_CONTENT) {
					if (!decodeBulkStringContent(in, out)) {
						return;
					}
				} else if (state == State.DECODE_ARRAY_HEADER) {
					if (!decodeArrayHeader(in, out)) {
						return;
					}
				} else { // State.DECODE_ERROR
					if (!decodeError(in, out)) {
						return;
					}
				}
			}
		} catch (RedisCodecException e) {
			resetDecoder();
			throw e;
		} catch (Exception e) {
			resetDecoder();
			throw new RedisCodecException(e);
		}
	}

	private boolean decodeType(ByteBuf in) {
		if (!in.isReadable()) {
			return false;
		}
		byte type = in.readByte();
		if (type == SIMPLE_STRING) {
			state = State.DECODE_SIMPLE_STRING;
		} else if (type == INTEGER) {
			state = State.DECODE_INTEGER;
		} else if (type == BULK_STRING) {
			state = State.DECODE_BULK_STRING_LENGTH;
		} else if (type == ERROR) {
			state = State.DECODE_ERROR;
		} else { // array
			state = State.DECODE_ARRAY_HEADER;
		}
		return true;
	}

	private boolean decodeSimpleString(ByteBuf in, List<Object> out) {
		if (!in.isReadable(EOL_LENGTH)) {
			return false;
		}
		int lfIndex = in.forEachByte(ByteProcessor.FIND_LF);
		if (lfIndex > 0) {
			ByteBuf lineByteBuf = in.readSlice(lfIndex - in.readerIndex() - 1);
			RedisCodecUtil.readEndOfLine(in);
			out.add(new DefaultSimpleStringMessage(lineByteBuf.toString(CharsetUtil.UTF_8)));
			resetDecoder();
			return true;
		} else if (lfIndex == -1) {
			return false;
		} else {
			throw new RedisDecoderException("error format for Simple String");
		}
	}

	private boolean decodeInteger(ByteBuf in, List<Object> out) {
		if (!in.isReadable(EOL_LENGTH)) {
			return false;
		}
		int lfIndex = in.forEachByte(ByteProcessor.FIND_LF);
		if (lfIndex > 0) {
			ByteBuf lineByteBuf = in.readSlice(lfIndex - in.readerIndex() - 1);
			RedisCodecUtil.readEndOfLine(in);
			long number = parseNumber(lineByteBuf);
			IntegerMessage im = CachedRedisMessages.cachedInteger((int) number);
			if (im == null) {
				im = new DefaultIntegerMessage(number);
			}
			if (arrays.isEmpty()) {
				out.add(im);
			} else {
				addArrayElement(out, im);
			}
			resetDecoder();
			return true;
		} else if (lfIndex == -1) {
			return false;
		} else {
			throw new RedisDecoderException("error format for Integer");
		}
	}

	private boolean addArrayElement(List<Object> out, RedisMessage element) {
		DefaultArrayMessage<RedisMessage> array = arrays.getLast();
		List<RedisMessage> elements = array.elements();
		elements.add(element);
		for (; elements.size() == array.length();) {
			DefaultArrayMessage<RedisMessage> last = arrays.removeLast();
			if (arrays.isEmpty()) {
				out.add(last);
				return true;
			}
			array = arrays.getLast();
			elements = array.elements();
			elements.add(last);
		}
		return false;
	}

	private long parseNumber(ByteBuf byteBuf) {
		int readableBytes = byteBuf.readableBytes();
		boolean negative = readableBytes > 0 && byteBuf.getByte(byteBuf.readerIndex()) == '-';
		if (negative) {
			if (readableBytes < 2) {
				throw new RedisDecoderException("no number to parse");
			}
			if (readableBytes > INT_MAX_LENGTH) {
				throw new RedisCodecException("too many characters to be a valid RESP Integer");
			}
			return -parsePositiveNumber(byteBuf.skipBytes(1));
		} else {
			if (readableBytes < 1) {
				throw new RedisDecoderException("no number to parse");
			}
			if (readableBytes > POSITIVE_INT_MAX_LENGTH) {
				throw new RedisCodecException("too many characters to be a valid RESP Integer");
			}
			return parsePositiveNumber(byteBuf);
		}
	}

	private long parsePositiveNumber(ByteBuf byteBuf) {
		toPositiveLongProcessor.reset();
		byteBuf.forEachByte(toPositiveLongProcessor);
		return toPositiveLongProcessor.content();
	}

	private boolean decodeBulkStringLength(ByteBuf in, List<Object> out) {
		if (!in.isReadable(EOL_LENGTH)) {
			return false;
		}
		int lfIndex = in.forEachByte(ByteProcessor.FIND_LF);
		if (lfIndex > 0) {
			ByteBuf lineByteBuf = in.readSlice(lfIndex - in.readerIndex() - 1);
			RedisCodecUtil.readEndOfLine(in);
			long length = parseNumber(lineByteBuf);
			if (length < 0) {
				if (arrays.isEmpty()) {
					out.add(CachedRedisMessages.NULL_BULK_STRING);
				} else {
					addArrayElement(out, CachedRedisMessages.NULL_BULK_STRING);
				}
				resetDecoder();
				return true;
			} else {
				if (length > REDIS_MESSAGE_MAX_LENGTH) {
					throw new RedisDecoderException("too long Bulk String message");
				}
				currentBulkStringLength = (int) length;
			}
			state = State.DECODE_BULK_STRING_CONTENT;
			return true;
		} else if (lfIndex == -1) {
			return false;
		} else {
			throw new RedisDecoderException("error format for Bulk String");
		}
	}

	private boolean decodeBulkStringContent(ByteBuf in, List<Object> out) {
		if (!in.isReadable(currentBulkStringLength + EOL_LENGTH)) {
			return false;
		}
		ByteBuf content = in.readRetainedSlice(currentBulkStringLength);
		RedisCodecUtil.readEndOfLine(in);
		BulkStringMessage str = new DefaultBulkStringMessage(content);
		if (arrays.isEmpty()) {
			out.add(str);
		} else {
			addArrayElement(out, str);
		}
		resetDecoder();
		return true;
	}

	private boolean decodeArrayHeader(ByteBuf in, List<Object> out) {
		if (!in.isReadable(EOL_LENGTH)) {
			return false;
		}
		int lfIndex = in.forEachByte(ByteProcessor.FIND_LF);
		if (lfIndex > 0) {
			ByteBuf lineByteBuf = in.readSlice(lfIndex - in.readerIndex() - 1);
			RedisCodecUtil.readEndOfLine(in);
			long length = parseNumber(lineByteBuf);
			if (length < 0) {
				if (arrays.isEmpty()) {
					out.add(CachedRedisMessages.NULL_ARRAY);
				} else {
					addArrayElement(out, CachedRedisMessages.NULL_ARRAY);
				}
			} else if (length == 0) {
				if (arrays.isEmpty()) {
					out.add(CachedRedisMessages.EMPTY_ARRAY);
				} else {
					addArrayElement(out, CachedRedisMessages.EMPTY_ARRAY);
				}
			} else {
				if (length > Integer.MAX_VALUE) {
					throw new RedisDecoderException("too many elements for Array");
				}
				DefaultArrayHeaderMessage header = new DefaultArrayHeaderMessage((int) length);
				arrays.addLast(new DefaultArrayMessage<>(header));
			}
			resetDecoder();
			return true;
		} else if (lfIndex == -1) {
			return false;
		} else {
			throw new RedisDecoderException("error format for Array");
		}
	}

	private boolean decodeError(ByteBuf in, List<Object> out) {
		if (!in.isReadable(EOL_LENGTH)) {
			return false;
		}
		int lfIndex = in.forEachByte(ByteProcessor.FIND_LF);
		if (lfIndex > 0) {
			ByteBuf lineByteBuf = in.readSlice(lfIndex - in.readerIndex() - 1);
			RedisCodecUtil.readEndOfLine(in);
			out.add(new DefaultErrorMessage(lineByteBuf.toString(CharsetUtil.UTF_8)));
			resetDecoder();
			return true;
		} else if (lfIndex == -1) {
			return false;
		} else {
			throw new RedisDecoderException("error format for Simple String");
		}
	}

	private void resetDecoder() {
		state = State.DECODE_TYPE;
		currentBulkStringLength = 0;
	}

	private static final class ToPositiveLongProcessor implements ByteProcessor {

		private long content;

		@Override
		public boolean process(byte value) throws Exception {
			if (value < '0' || value > '9') {
				throw new RedisCodecException("bad byte in number: " + value);
			}
			content = content * 10 + (value - '0');
			return true;
		}

		public long content() {
			return content;
		}

		public void reset() {
			content = 0;
		}
	}

	private enum State {
		DECODE_TYPE, DECODE_INTEGER, DECODE_ERROR, DECODE_SIMPLE_STRING, DECODE_ARRAY_HEADER, DECODE_BULK_STRING_LENGTH, DECODE_BULK_STRING_CONTENT
	}

}
