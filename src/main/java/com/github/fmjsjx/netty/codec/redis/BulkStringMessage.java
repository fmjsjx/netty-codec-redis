package com.github.fmjsjx.netty.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface BulkStringMessage extends RedisMessage, ByteBufHolder {

	default int length() {
		return content().readableBytes();
	}

	default boolean isNull() {
		return false;
	}

	ByteBuf content();

	String stringValue();

	long longValue();

	int intValue();

	boolean booleanValue();

}
