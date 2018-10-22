package me.netty.codec.redis;

import java.util.Arrays;
import java.util.List;

public class DefaultRedisRequest extends DefaultArrayMessage<BulkStringMessage> implements RedisRequest {

	public DefaultRedisRequest() {
		super();
	}

	public DefaultRedisRequest(ArrayHeaderMessage header, List<BulkStringMessage> elements) {
		super(header, elements);
	}

	public DefaultRedisRequest(ArrayHeaderMessage header) {
		super(header);
	}

	public DefaultRedisRequest(List<BulkStringMessage> elements) {
		super(elements);
	}

	public DefaultRedisRequest(String command, String... args) {
		this(new DefaultArrayHeaderMessage(args.length + 1));
		List<BulkStringMessage> elements = elements();
		elements.add(new DefaultBulkStringMessage(command));
		Arrays.stream(args).map(DefaultBulkStringMessage::new).forEach(elements::add);
	}

	@Override
	public String toString() {
		return "DefaultRedisRequest[> "
				+ String.join(" ", elements().stream().map(BulkStringMessage::stringValue).toArray(String[]::new))
				+ "]";
	}

}
