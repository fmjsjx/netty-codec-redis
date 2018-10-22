package me.netty.codec.redis;

public interface IntegerMessage extends RedisMessage {

	long value();

}
