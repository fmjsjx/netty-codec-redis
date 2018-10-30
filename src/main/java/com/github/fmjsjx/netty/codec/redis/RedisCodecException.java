package com.github.fmjsjx.netty.codec.redis;

import io.netty.handler.codec.CodecException;

public class RedisCodecException extends CodecException {

	private static final long serialVersionUID = 1L;

	public RedisCodecException(String message) {
		super(message);
	}

	public RedisCodecException(Throwable cause) {
		super(cause);
	}

}
