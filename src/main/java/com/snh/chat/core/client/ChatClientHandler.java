package com.snh.chat.core.client;

import com.snh.chat.core.server.ChatCore;
import com.snh.chat.protobuf.ChatMessageProto;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理客户端聊天通道
 * Created by xuhaifeng on 2016/6/8.
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger=Logger.getLogger(ChatClient.class.getName());

    private final WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;

    public ChatClientHandler(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("WebSocket Client disconnected!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        if (msg instanceof BinaryWebSocketFrame) {
//            BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
//            List<Object> list = new ArrayList<>(1);
//            ChatCore.decoder.decodeMessage(ctx, frame.content(), list);
//            if (list.size() > 0) {
//                ChatMessageProto.ChatMessage message = (ChatMessageProto.ChatMessage) list.get(0);
//                if (message == null) {
//                    Log.warn("传入的消息为null");
//                    return;
//                }
//                Log.debug(message.getNickname() + " Say : " + message.getContent());
//            }
//        } else if (msg instanceof CloseWebSocketFrame) {
//            Log.debug("WebSocket Client received closing");
//            ctx.close();
//        } else if (msg instanceof FullHttpResponse) {
//            FullHttpResponse response = (FullHttpResponse) msg;
//            Log.warn(response.decoderResult());
//        } else {
//            String message = ">>>unsupported frame type: " + msg.getClass().getName();
//            Log.error(message);
//        }
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(ch, (FullHttpResponse) msg);
            logger.debug("WebSocket Client connected!");
            handshakeFuture.setSuccess();
            return;
        }

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException(
                    "Unexpected FullHttpResponse (getStatus=" + response.status() +
                            ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        } else if (msg instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
            List<Object> list = new ArrayList<>(1);
            ChatCore.decoder.decodeMessage(ctx, frame.content(), list);
            if (list.size() > 0) {
                ChatMessageProto.ChatMessage message = (ChatMessageProto.ChatMessage) list.get(0);
                if (message == null) {
                    logger.warn("传入的消息为null");
                    return;
                }
                logger.debug(message.getNickname() + " Say : " + message.getContent());
            }
        } else if (msg instanceof CloseWebSocketFrame) {
            logger.debug("WebSocket Client received closing");
            ctx.close();
        } else if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) msg;
            System.out.println("WebSocket Client received message: " + textFrame.text());
        } else if (msg instanceof PongWebSocketFrame) {
            System.out.println("WebSocket Client received pong");
        } else {
            String message = ">>>unsupported frame type: " + msg.getClass().getName();
            logger.error(message);
        }
    }
}
