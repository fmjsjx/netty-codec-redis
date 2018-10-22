package me.netty.codec.redis;

import static me.netty.codec.redis.RedisConstants.*;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;

public abstract class AbstractCachedRedisMessage implements CachedRedisMessage {

	protected static ByteBuf buffer(int contentLength) {
		return UnpooledByteBufAllocator.DEFAULT.buffer(contentLength + TYPE_LENGTH + EOL_LENGTH);
	}

	protected final ByteBuf encodedContent;

	protected AbstractCachedRedisMessage(ByteBuf encodedContent) {
		this.encodedContent = Unpooled.unreleasableBuffer(encodedContent);
	}

	@Override
	public ByteBuf encodedContent() {
		return encodedContent;
	}

}
