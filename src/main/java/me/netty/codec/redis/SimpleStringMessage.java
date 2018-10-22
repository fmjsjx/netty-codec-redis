package me.netty.codec.redis;

public interface SimpleStringMessage extends RedisMessage {

	String content();

}
