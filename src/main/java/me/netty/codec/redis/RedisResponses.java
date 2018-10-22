package me.netty.codec.redis;

import java.nio.charset.Charset;
import java.util.Arrays;

public class RedisResponses {

	public static final SimpleStringMessage ok() {
		return CachedRedisMessages.OK;
	}

	public static final SimpleStringMessage pong() {
		return CachedRedisMessages.PONG;
	}

	public static final SimpleStringMessage ququed() {
		return CachedRedisMessages.QUEUED;
	}

	public static final ErrorMessage noauth() {
		return CachedRedisMessages.NOAUTH;
	}

	public static final ErrorMessage err() {
		return CachedRedisMessages.ERR;
	}

	public static final ErrorMessage indexOutOfRange() {
		return CachedRedisMessages.ERR_INDEX_OUT_OF_RANGE;
	}

	public static final ErrorMessage noSuchKey() {
		return CachedRedisMessages.ERR_NO_SUCH_KEY;
	}

	public static final ErrorMessage unknownCommand() {
		return CachedRedisMessages.ERR_UNKNOWN_COMMAND;
	}

	public static final ErrorMessage unsupportedCommand() {
		return CachedRedisMessages.ERR_UNSUPPORTED_COMMAND;
	}

	public static final ErrorMessage error(String errorMessage) {
		return new DefaultErrorMessage(errorMessage);
	}

	public static final IntegerMessage one() {
		return CachedRedisMessages.ONE;
	}
	
	public static final IntegerMessage zero() {
		return CachedRedisMessages.ZERO;
	}
	
	public static final IntegerMessage integer(long value) {
		if (value >= 0 && value <= RedisCodecUtil.MAX_CACHED_NUMBER) {
			return CachedRedisMessages.cachedInteger((int) value);
		}
		return new DefaultIntegerMessage(value);
	}

	public static final BulkStringMessage bulkString(String value) {
		if (value == null) {
			return CachedRedisMessages.NULL_BULK_STRING;
		}
		return new DefaultBulkStringMessage(value);
	}

	public static final BulkStringMessage bulkString(String value, Charset charset) {
		if (value == null) {
			return CachedRedisMessages.NULL_BULK_STRING;
		}
		return new DefaultBulkStringMessage(value, charset);
	}

	public static final ArrayMessage<BulkStringMessage> stringArray(String... values) {
		if (values.length == 0) {
			return CachedRedisMessages.emptyArray();
		}
		DefaultArrayMessage<BulkStringMessage> array = new DefaultArrayMessage<>(values.length);
		Arrays.stream(values).map(DefaultBulkStringMessage::new).forEach(array.elements()::add);
		return array;
	}

	private RedisResponses() {
	}

}
