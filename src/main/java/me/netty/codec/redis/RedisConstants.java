package me.netty.codec.redis;

public final class RedisConstants {

	private RedisConstants() {
	}

	public static final int TYPE_LENGTH = 1;

	public static final int EOL_LENGTH = 2;

	public static final int NULL_LENGTH = 2;

	public static final int NULL_VALUE = -1;

	public static final int REDIS_MESSAGE_MAX_LENGTH = 512 * 1024 * 1024; // 512MB

	public static final int REDIS_INLINE_MESSAGE_MAX_LENGTH = 64 * 1024; // 64KB
	
	public static final int POSITIVE_INT_MAX_LENGTH = 10;
	
	public static final int INT_MAX_LENGTH = POSITIVE_INT_MAX_LENGTH + 1;

	public static final int POSITIVE_LONG_MAX_LENGTH = 19; // length of Long.MAX_VALUE

	public static final int LONG_MAX_LENGTH = POSITIVE_LONG_MAX_LENGTH + 1; // +1 is sign

	public static final short NULL_SHORT = RedisCodecUtil.makeShort('-', '1');

	public static final short EOL_SHORT = RedisCodecUtil.makeShort('\r', '\n');

	public static final byte[] EOL_BYTES = RedisCodecUtil.shortToBytes(EOL_SHORT);
	
	public static final byte SIMPLE_STRING = '+';

	public static final byte ERROR = '-';

	public static final byte INTEGER = ':';

	public static final byte BULK_STRING = '$';

	public static final byte ARRAY_HEADER = '*';

}
