package me.netty.codec.redis;

import static io.netty.channel.ChannelFutureListener.CLOSE;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;

public class RedisServerHandlerAdapter extends SimpleChannelInboundHandler<RedisRequest> {

	private final String passwd;

	private boolean authenticated;

	public RedisServerHandlerAdapter(String passwd) {
		this.passwd = passwd;
		authenticated = StringUtil.isNullOrEmpty(passwd);
	}

	public RedisServerHandlerAdapter() {
		this(null);
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RedisRequest msg) throws Exception {
		String command = msg.command();
		if (isAuthenticated()) {
			switch (command.toUpperCase()) {
			case "APPEND":
				append(ctx, msg);
				break;
			case "ASKING":
				asking(ctx, msg);
				break;
			case "AUTH":
				ctx.writeAndFlush(CachedRedisMessages.OK);
				break;
			case "BGREWRITEAOF":
				bgrewriteaof(ctx, msg);
				break;
			case "BGSAVE":
				bgsave(ctx, msg);
				break;
			case "BITCOUNT":
				bitcount(ctx, msg);
				break;
			case "BITFIELD":
				bitfield(ctx, msg);
				break;
			case "BITOP":
				bitop(ctx, msg);
				break;
			case "BITPOS":
				bitpos(ctx, msg);
				break;
			case "BLPOP":
				blpop(ctx, msg);
				break;
			case "BRPOP":
				brpop(ctx, msg);
				break;
			case "BRPOPLPUSH":
				brpoplpush(ctx, msg);
				break;
			case "CLIENT":
				client(ctx, msg);
				break;
			case "CLUSTER":
				cluster(ctx, msg);
				break;
			case "CONFIG":
				config(ctx, msg);
				break;
			case "DBSIZE":
				dbsize(ctx, msg);
				break;
			case "DEBUG":
				debug(ctx, msg);
				break;
			case "DECR":
				decr(ctx, msg);
				break;
			case "DECRBY":
				decrby(ctx, msg);
				break;
			case "DEL":
				del(ctx, msg);
				break;
			case "DISCARD":
				discard(ctx, msg);
				break;
			case "DUMP":
				dump(ctx, msg);
				break;
			case "ECHO":
				echo(ctx, msg);
				break;
			case "EVAL":
				eval(ctx, msg);
				break;
			case "EVALSHA":
				evalsha(ctx, msg);
				break;
			case "EXEC":
				exec(ctx, msg);
				break;
			case "EXISTS":
				exists(ctx, msg);
				break;
			case "EXPIRE":
				expire(ctx, msg);
				break;
			case "EXPIREAT":
				expireat(ctx, msg);
				break;
			case "FLUSHALL":
				flushall(ctx, msg);
				break;
			case "FLUSHDB":
				flushdb(ctx, msg);
				break;
			case "GEOADD":
				geoadd(ctx, msg);
				break;
			case "GEODIST":
				geodist(ctx, msg);
				break;
			case "GEOHASH":
				geohash(ctx, msg);
				break;
			case "GEOPOS":
				geopos(ctx, msg);
				break;
			case "GEORADIUS":
				georadius(ctx, msg);
				break;
			case "GEORADIUSBYMEMBER":
				georadiusbymember(ctx, msg);
				break;
			case "GET":
				get(ctx, msg);
				break;
			case "GETBIT":
				getbit(ctx, msg);
				break;
			case "GETRANGE":
				getrange(ctx, msg);
				break;
			case "GETSET":
				getset(ctx, msg);
				break;
			case "HDEL":
				hdel(ctx, msg);
				break;
			case "HEXISTS":
				hexists(ctx, msg);
				break;
			case "HGET":
				hget(ctx, msg);
				break;
			case "HGETALL":
				hgetall(ctx, msg);
				break;
			case "HINCRBY":
				hincrby(ctx, msg);
				break;
			case "HINCRBYFLOAT":
				hincrbyfloat(ctx, msg);
				break;
			case "HKEYS":
				hkeys(ctx, msg);
				break;
			case "HLEN":
				hlen(ctx, msg);
				break;
			case "HMGET":
				hmget(ctx, msg);
				break;
			case "HMSET":
				hmset(ctx, msg);
				break;
			case "HSCAN":
				hscan(ctx, msg);
				break;
			case "HSET":
				hset(ctx, msg);
				break;
			case "HSETNX":
				hsetnx(ctx, msg);
				break;
			case "HVALS":
				hvals(ctx, msg);
				break;
			case "INCR":
				incr(ctx, msg);
				break;
			case "INCRBY":
				incrby(ctx, msg);
				break;
			case "INCRBYFLOAT":
				incrbyfloat(ctx, msg);
				break;
			case "INFO":
				info(ctx, msg);
				break;
			case "KEYS":
				keys(ctx, msg);
				break;
			case "LASTSAVE":
				lastsave(ctx, msg);
				break;
			case "LINDEX":
				lindex(ctx, msg);
				break;
			case "LINSERT":
				linsert(ctx, msg);
				break;
			case "LLEN":
				llen(ctx, msg);
				break;
			case "LPOP":
				lpop(ctx, msg);
				break;
			case "LPUSH":
				lpush(ctx, msg);
				break;
			case "LPUSHX":
				lpushx(ctx, msg);
				break;
			case "LRANGE":
				lrange(ctx, msg);
				break;
			case "LREM":
				lrem(ctx, msg);
				break;
			case "LSET":
				lset(ctx, msg);
				break;
			case "LTRIM":
				ltrim(ctx, msg);
				break;
			case "MGET":
				mget(ctx, msg);
				break;
			case "MIGRATE":
				migrate(ctx, msg);
				break;
			case "MONITOR":
				monitor(ctx, msg);
				break;
			case "MOVE":
				move(ctx, msg);
				break;
			case "MSET":
				mset(ctx, msg);
				break;
			case "MSETNX":
				msetnx(ctx, msg);
				break;
			case "MULTI":
				multi(ctx, msg);
				break;
			case "OBJECT":
				object(ctx, msg);
				break;
			case "PERSIST":
				persist(ctx, msg);
				break;
			case "PEXPIRE":
				pexpire(ctx, msg);
				break;
			case "PEXPIREAT":
				pexpireat(ctx, msg);
				break;
			case "PFADD":
				pfadd(ctx, msg);
				break;
			case "PFCOUNT":
				pfcount(ctx, msg);
				break;
			case "PFMERGE":
				pfmerge(ctx, msg);
				break;
			case "PING":
				ping(ctx, msg);
				break;
			case "PSETEX":
				psetex(ctx, msg);
				break;
			case "PSUBSCRIBE":
				psubscribe(ctx, msg);
				break;
			case "PTTL":
				pttl(ctx, msg);
				break;
			case "PUBLISH":
				publish(ctx, msg);
				break;
			case "PUBSUB":
				pubsub(ctx, msg);
				break;
			case "PUNSUBSCRIBE":
				punsubscribe(ctx, msg);
				break;
			case "QUIT":
				quit(ctx, msg);
				break;
			case "RANDOMKEY":
				randomkey(ctx, msg);
				break;
			case "READONLY":
				readonly(ctx, msg);
				break;
			case "RENAME":
				rename(ctx, msg);
				break;
			case "RENAMENX":
				renamenx(ctx, msg);
				break;
			case "RENAMEX":
				renamex(ctx, msg);
				break;
			case "RESTORE":
				restore(ctx, msg);
				break;
			case "RPOP":
				rpop(ctx, msg);
				break;
			case "RPOPLPUSH":
				rpoplpush(ctx, msg);
				break;
			case "RPUSH":
				rpush(ctx, msg);
				break;
			case "RPUSHX":
				rpushx(ctx, msg);
				break;
			case "SADD":
				sadd(ctx, msg);
				break;
			case "SAVE":
				save(ctx, msg);
				break;
			case "SCAN":
				scan(ctx, msg);
				break;
			case "SCARD":
				scard(ctx, msg);
				break;
			case "SCRIPT":
				script(ctx, msg);
				break;
			case "SDIFF":
				sdiff(ctx, msg);
				break;
			case "SDIFFSTORE":
				sdiffstore(ctx, msg);
				break;
			case "SELECT":
				select(ctx, msg);
				break;
			case "SENTINEL":
				sentinel(ctx, msg);
				break;
			case "SET":
				set(ctx, msg);
				break;
			case "SETBIT":
				setbit(ctx, msg);
				break;
			case "SETEX":
				setex(ctx, msg);
				break;
			case "SETNX":
				setnx(ctx, msg);
				break;
			case "SETRANGE":
				setrange(ctx, msg);
				break;
			case "SHUTDOWN":
				shutdown(ctx, msg);
				break;
			case "SINTER":
				sinter(ctx, msg);
				break;
			case "SINTERSTORE":
				sinterstore(ctx, msg);
				break;
			case "SISMEMBER":
				sismember(ctx, msg);
				break;
			case "SLAVEOF":
				slaveof(ctx, msg);
				break;
			case "SLOWLOG":
				slowlog(ctx, msg);
				break;
			case "SMEMBERS":
				smembers(ctx, msg);
				break;
			case "SMOVE":
				smove(ctx, msg);
				break;
			case "SORT":
				sort(ctx, msg);
				break;
			case "SPOP":
				spop(ctx, msg);
				break;
			case "SRANDMEMBER":
				srandmember(ctx, msg);
				break;
			case "SREM":
				srem(ctx, msg);
				break;
			case "SSCAN":
				sscan(ctx, msg);
				break;
			case "STRLEN":
				strlen(ctx, msg);
				break;
			case "SUBSCRIBE":
				subscribe(ctx, msg);
				break;
			case "SUBSTR":
				substr(ctx, msg);
				break;
			case "SUNION":
				sunion(ctx, msg);
				break;
			case "SUNIONSTORE":
				sunionstore(ctx, msg);
				break;
			case "SYNC":
				sync(ctx, msg);
				break;
			case "TIME":
				time(ctx, msg);
				break;
			case "TTL":
				ttl(ctx, msg);
				break;
			case "TYPE":
				type(ctx, msg);
				break;
			case "UNSUBSCRIBE":
				unsubscribe(ctx, msg);
				break;
			case "UNWATCH":
				unwatch(ctx, msg);
				break;
			case "WAIT":
				wait(ctx, msg);
				break;
			case "WATCH":
				watch(ctx, msg);
				break;
			case "ZADD":
				zadd(ctx, msg);
				break;
			case "ZCARD":
				zcard(ctx, msg);
				break;
			case "ZCOUNT":
				zcount(ctx, msg);
				break;
			case "ZINCRBY":
				zincrby(ctx, msg);
				break;
			case "ZINTERSTORE":
				zinterstore(ctx, msg);
				break;
			case "ZLEXCOUNT":
				zlexcount(ctx, msg);
				break;
			case "ZRANGE":
				zrange(ctx, msg);
				break;
			case "ZRANGEBYLEX":
				zrangebylex(ctx, msg);
				break;
			case "ZRANGEBYSCORE":
				zrangebyscore(ctx, msg);
				break;
			case "ZRANK":
				zrank(ctx, msg);
				break;
			case "ZREM":
				zrem(ctx, msg);
				break;
			case "ZREMRANGEBYLEX":
				zremrangebylex(ctx, msg);
				break;
			case "ZREMRANGEBYRANK":
				zremrangebyrank(ctx, msg);
				break;
			case "ZREMRANGEBYSCORE":
				zremrangebyscore(ctx, msg);
				break;
			case "ZREVRANGE":
				zrevrange(ctx, msg);
				break;
			case "ZREVRANGEBYLEX":
				zrevrangebylex(ctx, msg);
				break;
			case "ZREVRANGEBYSCORE":
				zrevrangebyscore(ctx, msg);
				break;
			case "ZREVRANK":
				zrevrank(ctx, msg);
				break;
			case "ZSCAN":
				zscan(ctx, msg);
				break;
			case "ZSCORE":
				zscore(ctx, msg);
				break;
			case "ZUNIONSTORE":
				zunionstore(ctx, msg);
				break;
			default:
				ctx.writeAndFlush(CachedRedisMessages.ERR_UNKNOWN_COMMAND);
			}
		} else {
			if ("AUTH".equalsIgnoreCase(command)) {
				auth(ctx, msg);
			} else {
				ctx.writeAndFlush(CachedRedisMessages.NOAUTH).addListener(CLOSE);
			}
		}
	}

	protected void ping(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.PONG);
	}

	protected void quit(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.OK).addListener(CLOSE);
	}

	protected void auth(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		String passwd = req.element(1).stringValue();
		if (passwd != null && passwd.equals(this.passwd)) {
			this.authenticated = true;
			ctx.writeAndFlush(CachedRedisMessages.OK);
		} else {
			ctx.writeAndFlush(CachedRedisMessages.NOAUTH).addListener(CLOSE);
		}
	}

	protected void append(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void asking(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void bgrewriteaof(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void bgsave(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void bitcount(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void bitfield(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void bitop(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void bitpos(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void blpop(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void brpop(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void brpoplpush(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void client(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void cluster(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void config(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void dbsize(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void debug(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void decr(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void decrby(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void del(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void discard(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void dump(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void echo(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(req.element(1).retainedDuplicate());
	}

	protected void eval(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void evalsha(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void exec(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void exists(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void expire(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void expireat(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void flushall(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void flushdb(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void geoadd(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void geodist(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void geohash(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void geopos(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void georadius(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void georadiusbymember(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void get(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void getbit(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void getrange(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void getset(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hdel(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hexists(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hget(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hgetall(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hincrby(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hincrbyfloat(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hkeys(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hlen(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hmget(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hmset(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hscan(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hset(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hsetnx(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void hvals(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void incr(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void incrby(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void incrbyfloat(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void info(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void keys(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void lastsave(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void lindex(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void linsert(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void llen(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void lpop(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void lpush(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void lpushx(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void lrange(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void lrem(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void lset(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void ltrim(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void mget(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void migrate(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void monitor(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void move(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void mset(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void msetnx(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void multi(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void object(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void persist(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void pexpire(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void pexpireat(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void pfadd(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void pfcount(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void pfmerge(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void psetex(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void psubscribe(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void pttl(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void publish(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void pubsub(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void punsubscribe(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void randomkey(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void readonly(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void rename(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void renamenx(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void renamex(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void restore(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void rpop(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void rpoplpush(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void rpush(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void rpushx(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void sadd(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void save(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void scan(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void scard(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void script(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void sdiff(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void sdiffstore(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void select(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void sentinel(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void set(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void setbit(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void setex(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void setnx(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void setrange(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void shutdown(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void sinter(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void sinterstore(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void sismember(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void slaveof(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void slowlog(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void smembers(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void smove(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void sort(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void spop(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void srandmember(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void srem(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void sscan(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void strlen(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void subscribe(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void substr(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void sunion(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void sunionstore(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void sync(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void time(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void ttl(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void type(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void unsubscribe(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void unwatch(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void wait(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void watch(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zadd(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zcard(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zcount(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zincrby(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zinterstore(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zlexcount(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zrange(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zrangebylex(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zrangebyscore(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zrank(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zrem(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zremrangebylex(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zremrangebyrank(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zremrangebyscore(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zrevrange(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zrevrangebylex(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zrevrangebyscore(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zrevrank(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zscan(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zscore(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

	protected void zunionstore(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
		ctx.writeAndFlush(CachedRedisMessages.ERR_UNSUPPORTED_COMMAND);
	}

}
