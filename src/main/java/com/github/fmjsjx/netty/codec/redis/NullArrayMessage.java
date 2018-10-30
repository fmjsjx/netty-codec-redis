package com.github.fmjsjx.netty.codec.redis;

import static com.github.fmjsjx.netty.codec.redis.RedisConstants.*;

import java.util.List;

public class NullArrayMessage extends AbstractCachedRedisMessage implements ArrayMessage<RedisMessage> {

	public NullArrayMessage() {
		super(buffer(3).writeByte(ARRAY_HEADER).writeShort(NULL_SHORT));
	}

	@Override
	public ArrayHeaderMessage header() {
		return NullArrayHeaderMessage.INSTANCE;
	}

	@Override
	public List<RedisMessage> elements() {
		return null;
	}

	@Override
	public boolean isNull() {
		return true;
	}

	private static final class NullArrayHeaderMessage implements ArrayHeaderMessage {

		private static final NullArrayHeaderMessage INSTANCE = new NullArrayHeaderMessage();

		@Override
		public int length() {
			return -1;
		}

	}

}
