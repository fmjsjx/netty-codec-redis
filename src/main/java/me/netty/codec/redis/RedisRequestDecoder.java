package me.netty.codec.redis;

import static me.netty.codec.redis.RedisConstants.*;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ByteProcessor;

public class RedisRequestDecoder extends ByteToMessageDecoder {

	private final ToPositiveLongProcessor toPositiveLongProcessor = new ToPositiveLongProcessor();

	private DefaultRedisRequest current;
	private int currentBulkStringLength;

	private State state = State.DECODE_TYPE;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			for (;;) {
				if (state == State.DECODE_TYPE) {
					if (!decodeType(in)) {
						return;
					}
				} else if (state == State.DECODE_ARRAY_HEADER) {
					if (!decodeArrayHeader(in)) {
						return;
					}
				} else if (state == State.DECODE_BULK_STRING_LENGTH) {
					if (!decodeBulkStringLength(in, out)) {
						return;
					}
				} else { // State.DECODE_BULK_STRING_CONTENT
					if (!decodeBulkStringContent(in, out)) {
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
		if (current == null) {
			if (type != ARRAY_HEADER) {
				throw new RedisDecoderException("request first byte must be *");
			}
			state = State.DECODE_ARRAY_HEADER;
		} else {
			if (type != BULK_STRING) {
				throw new RedisDecoderException("request elements only support Bulk Strings");
			}
			state = State.DECODE_BULK_STRING_LENGTH;
		}
		return true;
	}

	private boolean decodeArrayHeader(ByteBuf in) {
		if (!in.isReadable(EOL_LENGTH)) {
			return false;
		}
		int lfIndex = in.forEachByte(ByteProcessor.FIND_LF);
		if (lfIndex > 0) {
			ByteBuf lineByteBuf = in.readSlice(lfIndex - in.readerIndex() - 1);
			RedisCodecUtil.readEndOfLine(in);
			int length = parseLength(lineByteBuf);
			if (length < 1) {
				throw new RedisDecoderException("request elements length must >= 1");
			}
			current = new DefaultRedisRequest(new DefaultArrayHeaderMessage(length));
			state = State.DECODE_TYPE;
			return true;
		} else if (lfIndex == -1) {
			return false;
		} else { // lfIndex == 0
			throw new RedisDecoderException("error number format for Array Header");
		}
	}

	private int parseLength(ByteBuf byteBuf) {
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

	private int parsePositiveNumber(ByteBuf byteBuf) {
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
			int length = parseLength(lineByteBuf);
			if (length < 0) {
				List<BulkStringMessage> elements = current.elements();
				elements.add(CachedRedisMessages.NULL_BULK_STRING);
				if (elements.size() == current.length()) {
					out.add(current);
					resetDecoder();
				} else {
					state = State.DECODE_TYPE;
				}
				return true;
			}
			currentBulkStringLength = length;
			if (currentBulkStringLength > REDIS_MESSAGE_MAX_LENGTH) {
				throw new RedisDecoderException("too long Bulk String message");
			}
			state = State.DECODE_BULK_STRING_CONTENT;
			return true;
		} else if (lfIndex == -1) {
			return false;
		} else { // lfIndex == 0
			throw new RedisDecoderException("error format for Bulk String");
		}
	}

	private boolean decodeBulkStringContent(ByteBuf in, List<Object> out) {
		if (!in.isReadable(currentBulkStringLength + EOL_LENGTH)) {
			return false;
		}
		ByteBuf content = in.readRetainedSlice(currentBulkStringLength);
		RedisCodecUtil.readEndOfLine(in);
		List<BulkStringMessage> elements = current.elements();
		elements.add(new DefaultBulkStringMessage(content));
		if (elements.size() == current.length()) {
			out.add(current);
			resetDecoder();
		} else {
			state = State.DECODE_TYPE;
		}
		return true;
	}

	private void resetDecoder() {
		state = State.DECODE_TYPE;
		current = null;
	}

	private static final class ToPositiveLongProcessor implements ByteProcessor {
		private int result;

		@Override
		public boolean process(byte value) throws Exception {
			if (value < '0' || value > '9') {
				throw new RedisCodecException("bad byte in number: " + value);
			}
			result = result * 10 + (value - '0');
			return true;
		}

		public int content() {
			return result;
		}

		public void reset() {
			result = 0;
		}
	}

	private enum State {
		DECODE_TYPE, DECODE_ARRAY_HEADER, DECODE_BULK_STRING_LENGTH, DECODE_BULK_STRING_CONTENT
	}

}
