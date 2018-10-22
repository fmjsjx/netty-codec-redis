package me.netty.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.util.CharsetUtil;

@SuppressWarnings("unchecked")
public abstract class AbstractRedisContent<T extends AbstractRedisContent<T>> extends DefaultByteBufHolder
		implements RedisMessage {

	public AbstractRedisContent(ByteBuf content) {
		super(content);
	}

	@Override
	public T copy() {
		return (T) super.copy();
	}

	@Override
	public T duplicate() {
		return (T) super.duplicate();
	}

	@Override
	public T retainedDuplicate() {
		return (T) super.retainedDuplicate();
	}

	@Override
	public T replace(ByteBuf content) {
		return (T) super.replace(content);
	}

	@Override
	public T retain() {
		return (T) super.retain();
	}

	@Override
	public T retain(int increment) {
		return (T) super.retain(increment);
	}

	@Override
	public T touch() {
		return (T) super.touch();
	}

	@Override
	public T touch(Object hint) {
		return (T) super.touch(hint);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[content=" + content().toString(CharsetUtil.UTF_8) + "]";
	}

}
