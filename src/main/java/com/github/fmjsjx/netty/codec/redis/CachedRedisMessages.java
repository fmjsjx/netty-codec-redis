package com.github.fmjsjx.netty.codec.redis;

import static com.github.fmjsjx.netty.codec.redis.RedisCodecUtil.*;

public final class CachedRedisMessages {

	public static final CachedSimpleStringMessage OK = new CachedSimpleStringMessage("OK");
	public static final CachedSimpleStringMessage PONG = new CachedSimpleStringMessage("PONG");
	public static final CachedSimpleStringMessage QUEUED = new CachedSimpleStringMessage("QUEUED");

	public static final CachedErrorMessage NOAUTH = new CachedErrorMessage("NOAUTH Authentication required.");
	public static final CachedErrorMessage ERR = new CachedErrorMessage("ERR");
	public static final CachedErrorMessage ERR_INDEX_OUT_OF_RANGE = new CachedErrorMessage("ERR index out of range");
	public static final CachedErrorMessage ERR_NO_SUCH_KEY = new CachedErrorMessage("ERR no such key");
	public static final CachedErrorMessage ERR_UNKNOWN_COMMAND = new CachedErrorMessage("ERR unknown command");
	public static final CachedErrorMessage ERR_UNSUPPORTED_COMMAND = new CachedErrorMessage("ERR unsupported command");

	public static final NullBulkStringMessage NULL_BULK_STRING = new NullBulkStringMessage();
	
	public static final NullArrayMessage NULL_ARRAY = new NullArrayMessage();
	public static final ArrayMessage<? extends RedisMessage> EMPTY_ARRAY = new DefaultArrayMessage<>();

	public static final CachedIntegerMessage ZERO = new CachedIntegerMessage(0);
	public static final CachedIntegerMessage ONE = new CachedIntegerMessage(1);

	public static final CachedIntegerMessage[] CACHED_INTEGERS = new CachedIntegerMessage[MAX_CACHED_NUMBER + 1];

	static {
		CACHED_INTEGERS[0] = ZERO;
		CACHED_INTEGERS[1] = ONE;
		for (int i = 2; i < CACHED_INTEGERS.length; i++) {
			CACHED_INTEGERS[i] = new CachedIntegerMessage(i);
		}
	}

	public static final CachedIntegerMessage cachedInteger(int value) {
		if (value >= 0 && value <= MAX_CACHED_NUMBER) {
			return CACHED_INTEGERS[value];
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static final <E extends RedisMessage> ArrayMessage<E> emptyArray() {
		return (ArrayMessage<E>) EMPTY_ARRAY;
	}

	private CachedRedisMessages() {
	}

}
