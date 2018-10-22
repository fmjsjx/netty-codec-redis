package me.netty.codec.redis;

public abstract class AbstractInlineRedisContent {

	protected final String content;

	protected AbstractInlineRedisContent(String content) {
		this.content = content;
	}

	public String content() {
		return content;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[content=" + content + "]";
	}

}
