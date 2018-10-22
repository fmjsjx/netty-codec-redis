package me.netty.codec.redis;

public interface ArrayHeaderMessage extends RedisMessage {

	int length();

}
