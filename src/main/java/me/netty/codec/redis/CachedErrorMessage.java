package me.netty.codec.redis;

import static me.netty.codec.redis.RedisConstants.*;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.util.CharsetUtil;

public class CachedErrorMessage extends AbstractCachedRedisMessage implements ErrorMessage, CachedRedisMessage {

	private static ByteBuf encodeContent(String content) {
		byte[] b = content.getBytes(CharsetUtil.UTF_8);
		return UnpooledByteBufAllocator.DEFAULT.buffer(b.length + TYPE_LENGTH + EOL_LENGTH).writeByte(ERROR)
				.writeBytes(b).writeShort(EOL_SHORT);
	}

	private final String content;

	public CachedErrorMessage(String content) {
		super(encodeContent(content));
		this.content = content;
	}

	@Override
	public String content() {
		return content;
	}

	@Override
	public String toString() {
		return "CachedErrorMessage[content=" + content + "]";
	}

}
