package me.netty.codec.redis;

public class RedisDecoderException extends RedisCodecException {

	private static final long serialVersionUID = 1L;

	public RedisDecoderException(String message) {
		super(message);
	}

	public RedisDecoderException(Throwable cause) {
		super(cause);
	}

}
