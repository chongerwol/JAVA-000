package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HttpRequestHeaderFilterImpl implements HttpRequestFilter {
	
	@Override
	public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
		fullRequest.headers().add("NIO", "JIANGWEIHAI001");
	}

}
