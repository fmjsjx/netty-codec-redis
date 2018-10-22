package me.netty.codec.redis;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class DefaultBulkStringMessage extends AbstractRedisContent<DefaultBulkStringMessage>
		implements BulkStringMessage {

	private String stringCache;
	private Long numberCache;
	private Boolean booleanCache;

	public DefaultBulkStringMessage(ByteBuf content) {
		super(content);
	}

	public DefaultBulkStringMessage(String content, Charset charset) {
		super(Unpooled.copiedBuffer(content, charset));
		this.stringCache = content;
	}

	public DefaultBulkStringMessage(String content) {
		this(content, CharsetUtil.UTF_8);
	}

	@Override
	public String stringValue() {
		return stringCache != null ? stringCache : (stringCache = content().toString(CharsetUtil.UTF_8));
	}

	@Override
	public long longValue() {
		return numberCache != null ? numberCache.longValue() : (numberCache = decodeNumber()).longValue();
	}

	@Override
	public int intValue() {
		return numberCache != null ? numberCache.intValue() : (numberCache = decodeNumber()).intValue();
	}

	@Override
	public boolean booleanValue() {
		return booleanCache != null ? booleanCache.booleanValue()
				: (booleanCache = Boolean.parseBoolean(content().toString(CharsetUtil.UTF_8))).booleanValue();
	}

	private long decodeNumber() {
		ByteBuf content = content();
		long value = 0;
		int begin = content.readerIndex();
		int end = content.writerIndex();
		boolean negative = content.getByte(begin) == '-' ? true : false;
		if (negative) {
			begin++;
		}
		for (int i = begin; i < end; i++) {
			byte b = content.getByte(i);
			if (b >= '0' && b <= '9') {
				value = value * 10 + (b - '0');
			} else {
				break;
			}
		}
		return negative ? value * -1 : value;
	}

}
