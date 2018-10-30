package com.github.fmjsjx.netty.codec.redis;

import static com.github.fmjsjx.netty.codec.redis.RedisConstants.*;

import io.netty.buffer.ByteBuf;

public class CachedIntegerMessage extends AbstractCachedRedisMessage implements IntegerMessage {

	private static ByteBuf encodedContent(long value) {
		byte[] b = RedisCodecUtil.longToAsciiBytes(value);
		return buffer(b.length).writeByte(INTEGER).writeBytes(b).writeShort(EOL_SHORT);
	}

	private final long value;

	public CachedIntegerMessage(long value) {
		super(encodedContent(value));
		this.value = value;
	}

	public long value() {
		return value;
	}

	@Override
	public String toString() {
		return "CachedIntegerMessage[value=" + value + "]";
	}

}
