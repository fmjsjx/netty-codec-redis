package com.github.fmjsjx.netty.codec.redis;

public interface RedisRequest extends ArrayMessage<BulkStringMessage> {

	default String command() {
		return element(0).stringValue();
	}
	
	default BulkStringMessage element(int index) {
		return elements().get(index);
	}

}
