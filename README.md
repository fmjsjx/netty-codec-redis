# Unmaintained!
This project is archived and unmaintained.

A new `Fast-CGI` codec component has been provided by [fmjsjx/libnetty](https://github.com/fmjsjx/libnetty).

# netty-codec-redis
A REdis Serialization Protocol(RESP) codec component based on netty-4.1.x.

# quick start:
RESPServer.java
```java
import com.github.fmjsjx.netty.codec.redis.RedisMessageEncoder;
import com.github.fmjsjx.netty.codec.redis.RedisRequestDecoder;
...

public class RESPServer {
...
    public static void main(String[] args) throw Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        RedisMessageEncoder redisMessageEncoder = new RedisMessageEncoder();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ReadTimeoutHandler(300), new RedisRequestDecoder(), redisMessageEncoder,
                        new RESPServerHandler());
                }
            }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.TCP_NODELAY, true);
            ChannelFuture future = b.bind(address).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
...
}
```

RESPServerHandler.java
```java
import com.github.fmjsjx.netty.codec.redis.RedisRequest;
import com.github.fmjsjx.netty.codec.redis.RedisResponses;
import com.github.fmjsjx.netty.codec.redis.RedisServerHandlerAdapter;
...

public class RESPServerHandler extands RedisServerHandlerAdapter {
...
    // Implements redis command SET
    @Override
    protected void set(ChannelHandlerContext ctx, RedisRequest req) throws Exception {
        // get elements from index 1, index 0 is command
        String key = req.element(1).stringValue();
        String value = req.element(2).stringValue();
        // do something...
        ...
        // returns "+OK"
        ctx.writeAndFlush(RedisResponses.ok())
    }
...
}
```
