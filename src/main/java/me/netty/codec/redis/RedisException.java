package me.netty.codec.redis;

import static java.util.Objects.*;

public class RedisException extends Exception {

	private static final long serialVersionUID = 1L;

	private static String errorMessage(Throwable cause) {
		String msg = cause.getLocalizedMessage();
		return nonNull(msg) ? msg : cause.getClass().getSimpleName();
	}

	public RedisException(String message) {
		super("ERR " + message);
	}

	public RedisException(Throwable cause) {
		super("ERR " + errorMessage(cause), cause);
	}

	public RedisException(String message, Throwable cause) {
		super("ERR " + message, cause);
	}

}
