package me.netty.codec.redis;

public class DefaultErrorMessage extends AbstractInlineRedisContent implements ErrorMessage {

	public DefaultErrorMessage(String content) {
		super(content);
	}

}
