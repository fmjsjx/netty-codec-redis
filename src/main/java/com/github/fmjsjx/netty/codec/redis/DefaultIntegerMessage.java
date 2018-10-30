package com.github.fmjsjx.netty.codec.redis;

public class DefaultIntegerMessage implements IntegerMessage {

	private final long value;

	public DefaultIntegerMessage(long value) {
		this.value = value;
	}

	@Override
	public long value() {
		return value;
	}

	@Override
	public String toString() {
		return "DefaultIntegerMessage[value=" + value + "]";
	}

}
