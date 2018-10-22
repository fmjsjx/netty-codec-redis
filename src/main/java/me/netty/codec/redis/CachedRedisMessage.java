package me.netty.codec.redis;

import io.netty.buffer.ByteBuf;

public interface CachedRedisMessage extends RedisMessage {

	ByteBuf encodedContent();

}
