package com.github.fmjsjx.netty.codec.redis;

public class DefaultSimpleStringMessage extends AbstractInlineRedisContent implements SimpleStringMessage {

	public DefaultSimpleStringMessage(String content) {
		super(content);
	}

}
