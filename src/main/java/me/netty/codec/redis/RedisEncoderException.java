package me.netty.codec.redis;

public class RedisEncoderException extends RedisCodecException {

	private static final long serialVersionUID = 1L;

	public RedisEncoderException(String message) {
		super(message);
	}

	public RedisEncoderException(Throwable cause) {
		super(cause);
	}

}
