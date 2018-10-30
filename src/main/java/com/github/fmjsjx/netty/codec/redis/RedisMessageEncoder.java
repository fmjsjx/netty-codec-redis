package com.github.fmjsjx.netty.codec.redis;

import static com.github.fmjsjx.netty.codec.redis.RedisConstants.*;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
public class RedisMessageEncoder extends MessageToMessageEncoder<RedisMessage> {

	@SuppressWarnings("unchecked")
	@Override
	protected void encode(ChannelHandlerContext ctx, RedisMessage msg, List<Object> out) throws Exception {
		if (msg instanceof CachedRedisMessage) {
			encode(ctx, (CachedRedisMessage) msg, out);
		} else if (msg instanceof BulkStringMessage) {
			encode(ctx, (BulkStringMessage) msg, out);
		} else if (msg instanceof IntegerMessage) {
			encode(ctx, (IntegerMessage) msg, out);
		} else if (msg instanceof ArrayMessage) {
			encode(ctx, (ArrayMessage<? extends RedisMessage>) msg, out);
		} else if (msg instanceof ArrayHeaderMessage) {
			encode(ctx, (ArrayHeaderMessage) msg, out);
		} else if (msg instanceof ErrorMessage) {
			encode(ctx, (ErrorMessage) msg, out);
		} else if (msg instanceof SimpleStringMessage) {
			encode(ctx, (SimpleStringMessage) msg, out);
		} else {
			throw new RedisEncoderException("unsupported message class " + msg.getClass());
		}
	}

	protected void encode(ChannelHandlerContext ctx, CachedRedisMessage msg, List<Object> out) {
		out.add(msg.encodedContent().duplicate());
	}

	protected void encode(ChannelHandlerContext ctx, BulkStringMessage msg, List<Object> out) {
		int length = msg.length();
		ByteBuf header = RedisCodecUtil.cachedBulkStringHeaderBuf(length);
		if (header == null) {
			byte[] b = RedisCodecUtil.longToAsciiBytes(length);
			header = ctx.alloc().buffer(TYPE_LENGTH + b.length + EOL_LENGTH).writeByte(BULK_STRING).writeBytes(b)
					.writeShort(EOL_SHORT);
		}
		out.add(header);
		out.add(msg.content().retainedDuplicate());
		out.add(RedisCodecUtil.eolBuf());
	}

	protected void encode(ChannelHandlerContext ctx, IntegerMessage msg, List<Object> out) {
		long value = msg.value();
		byte[] b = RedisCodecUtil.longToAsciiBytes(value);
		ByteBuf buf = ctx.alloc().buffer(TYPE_LENGTH + b.length + EOL_LENGTH).writeByte(INTEGER).writeBytes(b)
				.writeShort(EOL_SHORT);
		out.add(buf);
	}

	protected void encode(ChannelHandlerContext ctx, ArrayMessage<? extends RedisMessage> array, List<Object> out)
			throws Exception {
		encode(ctx, array.header(), out);
		for (RedisMessage element : array.elements()) {
			encode(ctx, element, out);
		}
	}

	protected void encode(ChannelHandlerContext ctx, ArrayHeaderMessage msg, List<Object> out) {
		int length = msg.length();
		ByteBuf buf = RedisCodecUtil.cachedArrayHeaderBuf(length);
		if (buf == null) {
			byte[] b = RedisCodecUtil.longToAsciiBytes(length);
			buf = ctx.alloc().buffer(TYPE_LENGTH + b.length + EOL_LENGTH).writeByte(ARRAY_HEADER).writeBytes(b)
					.writeShort(EOL_SHORT);
		}
		out.add(buf);
	}

	protected void encode(ChannelHandlerContext ctx, ErrorMessage msg, List<Object> out) {
		ByteBuf buf = ctx.alloc().buffer().writeByte(ERROR);
		ByteBufUtil.writeUtf8(buf, msg.content());
		buf.writeShort(EOL_SHORT);
		out.add(buf);
	}

	protected void encode(ChannelHandlerContext ctx, SimpleStringMessage msg, List<Object> out) {
		ByteBuf buf = ctx.alloc().buffer().writeByte(SIMPLE_STRING);
		ByteBufUtil.writeUtf8(buf, msg.content());
		buf.writeShort(EOL_SHORT);
		out.add(buf);
	}

}
