package me.netty.codec.redis;

import static me.netty.codec.redis.RedisConstants.*;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NullBulkStringMessage extends AbstractCachedRedisMessage implements BulkStringMessage {

	public NullBulkStringMessage() {
		super(buffer(TYPE_LENGTH + NULL_LENGTH + EOL_LENGTH).writeByte(BULK_STRING).writeShort(NULL_SHORT)
				.writeShort(EOL_SHORT));
	}

	@Override
	public int length() {
		return -1;
	}

	@Override
	public boolean isNull() {
		return true;
	}

	@Override
	public ByteBuf content() {
		return Unpooled.EMPTY_BUFFER;
	}

	@Override
	public String stringValue() {
		return null;
	}

	@Override
	public long longValue() {
		return 0;
	}

	@Override
	public int intValue() {
		return 0;
	}

	@Override
	public boolean booleanValue() {
		return false;
	}

	@Override
	public NullBulkStringMessage copy() {
		return this;
	}

	@Override
	public NullBulkStringMessage duplicate() {
		return this;
	}

	@Override
	public NullBulkStringMessage retainedDuplicate() {
		return this;
	}

	@Override
	public NullBulkStringMessage replace(ByteBuf content) {
		return this;
	}

	@Override
	public NullBulkStringMessage retain() {
		return this;
	}

	@Override
	public NullBulkStringMessage retain(int increment) {
		return this;
	}

	@Override
	public NullBulkStringMessage touch() {
		return this;
	}

	@Override
	public NullBulkStringMessage touch(Object hint) {
		return this;
	}

	@Override
	public int refCnt() {
		return 0;
	}

	@Override
	public boolean release() {
		return false;
	}

	@Override
	public boolean release(int decrement) {
		return false;
	}

}
