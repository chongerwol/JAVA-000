package io.github.kimmking.gateway.outbound.netty4;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HttpNettyOutboundHandler{
    
    private String backendUrl;
    
    public HttpNettyOutboundHandler(String backendUrl){
        this.backendUrl = backendUrl.endsWith("/")?backendUrl.substring(0,backendUrl.length()-1):backendUrl;
    }
    
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) throws Exception {
        final String url = this.backendUrl + fullRequest.uri();
        NettyHttpClient nettyClient = new NettyHttpClient(url, fullRequest, ctx);
        nettyClient.connect();
    }
    
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //System.out.println(this.getClass().getName() + " exception:" + cause.getMessage());
        ctx.close();
    }
    
}
