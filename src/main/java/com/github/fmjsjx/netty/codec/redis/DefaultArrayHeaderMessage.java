package com.github.fmjsjx.netty.codec.redis;

public class DefaultArrayHeaderMessage implements ArrayHeaderMessage {

	private final int length;

	public DefaultArrayHeaderMessage(int length) {
		this.length = length;
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public String toString() {
		return "DefaultArrayHeaderMessage[length=" + length + "]";
	}

}
