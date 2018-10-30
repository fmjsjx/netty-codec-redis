package com.github.fmjsjx.netty.codec.redis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCountUtil;

public class DefaultArrayMessage<E extends RedisMessage> extends AbstractReferenceCounted implements ArrayMessage<E> {

	private final ArrayHeaderMessage header;
	private final List<E> elements;

	public DefaultArrayMessage() {
		this(Collections.emptyList());
	}

	@SuppressWarnings("unchecked")
	public DefaultArrayMessage(List<? extends RedisMessage> elements) {
		this.elements = (List<E>) Objects.requireNonNull(elements, "elements");
		this.header = new DefaultArrayHeaderMessage(elements.size());
	}

	@SuppressWarnings("unchecked")
	public DefaultArrayMessage(ArrayHeaderMessage header, List<? extends RedisMessage> elements) {
		this.header = Objects.requireNonNull(header, "header");
		this.elements = (List<E>) Objects.requireNonNull(elements, "elements");
	}

	public DefaultArrayMessage(ArrayHeaderMessage header) {
		this.header = Objects.requireNonNull(header, "header");
		this.elements = new ArrayList<>(header.length());
	}

	public DefaultArrayMessage(int length) {
		this(new DefaultArrayHeaderMessage(length));
	}

	@Override
	public ArrayHeaderMessage header() {
		return header;
	}

	@Override
	public List<E> elements() {
		return elements;
	}

	@Override
	public DefaultArrayMessage<E> addElement(E element) {
		ArrayMessage.super.addElement(element);
		return this;
	}

	@Override
	public DefaultArrayMessage<E> retain() {
		super.retain();
		return this;
	}

	@Override
	public DefaultArrayMessage<E> retain(int increment) {
		super.retain(increment);
		return this;
	}

	@Override
	public DefaultArrayMessage<E> touch() {
		super.touch();
		return this;
	}

	@Override
	public DefaultArrayMessage<E> touch(Object hint) {
		elements.forEach(e -> ReferenceCountUtil.touch(e, hint));
		return this;
	}

	@Override
	protected void deallocate() {
		elements.forEach(ReferenceCountUtil::release);
	}

	@Override
	public String toString() {
		return "DefaultArrayMessage[header=" + header + ", elements=" + elements + "]";
	}

}
