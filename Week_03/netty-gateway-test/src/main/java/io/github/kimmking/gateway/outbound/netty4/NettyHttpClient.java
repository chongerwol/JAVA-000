package io.github.kimmking.gateway.outbound.netty4;

import java.net.URI;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

public class NettyHttpClient {
    private String url;
    private FullHttpRequest httpRequest;
    private ChannelHandlerContext ctx;

    public NettyHttpClient(String url, FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        this.url = url;
        this.httpRequest = fullRequest;
        this.ctx = ctx;
    }

    public void connect() throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                            ch.pipeline().addLast(new HttpResponseDecoder());
                            // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                            ch.pipeline().addLast(new HttpRequestEncoder());
                            ch.pipeline().addLast(new NettyHttpClientInboundHandler(ctx));
                        }
                    });
            
            URI uri = new URI(url);
            String host = uri.getHost();
            int port = uri.getPort();
            //System.out.println("netty client connect host:" + host + ",port:" + port + ",uri:" + httpRequest.uri());
            //System.out.println("http header filter NIO=" + httpRequest.headers().get("NIO"));
            ChannelFuture f = bootstrap.connect(host, port).sync();
            f.channel().write(httpRequest);
            f.channel().flush();

            f.channel().closeFuture().sync();
        } finally {
            bootstrap.config().group().shutdownGracefully();
        }

    }
}