package me.netty.codec.redis;

import static me.netty.codec.redis.RedisConstants.*;
import static java.util.Objects.nonNull;

import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public final class RedisCodecUtil {

	/*
	 * cache 1 => 1024
	 */
	static final int MAX_CACHED_NUMBER = 1024;
	private static final byte[][] NUMBER_CACHE = new byte[MAX_CACHED_NUMBER + 1][];
	private static final ByteBuf[] BULK_STRING_HEADER_BUF_CACHE = new ByteBuf[MAX_CACHED_NUMBER + 1];
	private static final ByteBuf[] ARRAY_HEADER_BUF_CACHE = new ByteBuf[MAX_CACHED_NUMBER + 1];

	private static final ByteBuf EOL_BUF = Unpooled
			.unreleasableBuffer(ByteBufAllocator.DEFAULT.buffer(2).writeShort(EOL_SHORT));

	private static final boolean BIG_ENDIAN_NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;

	static {
		for (int i = 0; i <= MAX_CACHED_NUMBER; i++) {
			byte[] b = NUMBER_CACHE[i] = longToAsciiBytes(i);
			ByteBuf bulkStringBuf = ByteBufAllocator.DEFAULT.buffer(TYPE_LENGTH + b.length + EOL_LENGTH)
					.writeByte(BULK_STRING).writeBytes(b).writeShort(EOL_SHORT);
			ByteBuf arrayHeaderBuf = ByteBufAllocator.DEFAULT.buffer(TYPE_LENGTH + b.length + EOL_LENGTH)
					.writeByte(ARRAY_HEADER).writeBytes(b).writeShort(EOL_SHORT);
			BULK_STRING_HEADER_BUF_CACHE[i] = Unpooled.unreleasableBuffer(bulkStringBuf);
			ARRAY_HEADER_BUF_CACHE[i] = Unpooled.unreleasableBuffer(arrayHeaderBuf);
		}
	}

	public static byte[] longToAsciiBytes(long value) {
		return Long.toString(value).getBytes(CharsetUtil.US_ASCII);
	}

	public static byte[] cachedLongBytes(long value) {
		if (value >= 0 && value < MAX_CACHED_NUMBER) {
			return NUMBER_CACHE[(int) value];
		}
		return null;
	}

	public static byte[] numberToBytes(long value) {
		byte[] bytes = cachedLongBytes(value);
		return nonNull(bytes) ? bytes : longToAsciiBytes(value);
	}

	public static short makeShort(char first, char second) {
		return BIG_ENDIAN_NATIVE_ORDER ? (short) ((second << 8) | first) : (short) ((first << 8) | second);
	}

	public static byte[] shortToBytes(short value) {
		byte[] bytes = new byte[2];
		if (BIG_ENDIAN_NATIVE_ORDER) {
			bytes[1] = (byte) ((value >> 8) & 0xff);
			bytes[0] = (byte) (value & 0xff);
		} else {
			bytes[0] = (byte) ((value >> 8) & 0xff);
			bytes[1] = (byte) (value & 0xff);
		}
		return bytes;
	}

	public static final ByteBuf cachedBulkStringHeaderBuf(int length) {
		if (length >= 0 && length <= MAX_CACHED_NUMBER) {
			return BULK_STRING_HEADER_BUF_CACHE[length].duplicate();
		}
		return null;
	}

	public static final ByteBuf eolBuf() {
		return EOL_BUF.duplicate();
	}

	public static final ByteBuf cachedArrayHeaderBuf(int length) {
		if (length >= 0 && length <= MAX_CACHED_NUMBER) {
			return ARRAY_HEADER_BUF_CACHE[length].duplicate();
		}
		return null;
	}

	public static final void readEndOfLine(final ByteBuf in) {
		short delim = in.readShort();
		if (EOL_SHORT == delim) {
			return;
		}
		throw new RedisDecoderException("end of line must be CRLF");
	}

	private RedisCodecUtil() {
	}

}
