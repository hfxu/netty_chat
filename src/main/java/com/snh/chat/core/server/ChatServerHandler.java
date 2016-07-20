package com.snh.chat.core.server;

import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.apache.log4j.Logger;

/**
 * 处理服务端聊天通道
 * Created by xuhaifeng on 2016/6/8.
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private static final Logger logger=Logger.getLogger(ChatServerHandler.class.getName());

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel in = ctx.channel();
        ChatCore.channels.add(in);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel in = ctx.channel();
        logger.debug("Remove Channel:" + in.id().asLongText());

        ChatCore.remove(in.id().asLongText());

//        String ip = in.remoteAddress().toString();
        ChatCore.channels.remove(in);
        in.close();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        ChatCore.process(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("client " + ctx.channel().remoteAddress() + " 异常", cause);
        ChatCore.remove(ctx.channel().id().asLongText());
        ChatCore.channels.remove(ctx.channel());
        ctx.close();
    }
}
