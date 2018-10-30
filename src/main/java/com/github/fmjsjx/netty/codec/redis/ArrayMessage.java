package com.github.fmjsjx.netty.codec.redis;

import java.util.List;

public interface ArrayMessage<E extends RedisMessage> extends RedisMessage {

	ArrayHeaderMessage header();

	default int length() {
		return header().length();
	}

	List<E> elements();
	
	default ArrayMessage<E> addElement(E element) {
		elements().add(element);
		return this;
	}
	
	default boolean isNull() {
		return false;
	}

}
