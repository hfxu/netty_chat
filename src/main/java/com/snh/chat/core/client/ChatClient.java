package com.snh.chat.core.client;

import com.snh.chat.protobuf.ChatMessageCore;
import com.snh.chat.protobuf.ChatMessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.*;
import org.apache.log4j.Logger;

import java.net.URI;

/**
 * 聊天客户端线程
 * Created by xuhaifeng on 2016/6/8.
 */
public class ChatClient {
    private static final Logger logger=Logger.getLogger(ChatClient.class);

    //    private static final String URL = System.getProperty("url", "ws://127.0.0.1:8080/websocket");
    private static final String URL = System.getProperty("url", "ws://feedback.snh48.com:8080/websocket");

    public static void main(String[] args) throws Exception {
        URI uri = new URI(URL);
        String scheme = uri.getScheme() == null ? "ws" : uri.getScheme();
        final int port;
        if (uri.getPort() == -1) {
            if ("ws".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("wss".equalsIgnoreCase(scheme)) {
                port = 443;
            } else {
                port = -1;
            }
        } else {
            port = uri.getPort();
        }

        if (!"ws".equalsIgnoreCase(scheme)) {
            logger.error("Only WS is supported.");
            return;
        }
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            for (int i = 0; i < 62000; i++) {
                // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
                // If you change it to V00, ping is not supported and remember to change
                // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
                final ChatClientHandler handler =
                        new ChatClientHandler(
                                WebSocketClientHandshakerFactory.newHandshaker(
                                        uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders()));

                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new ChatClientInitializer(handler));

                Channel ch = b.connect(uri.getHost(), port).sync().channel();
                handler.handshakeFuture().sync();


                ChatMessageProto.ChatMessage chatMessage = ChatMessageCore.createJoinGroupMessage("A-" + Integer.toString(i), "A-" + Integer.toString(i), "img/ayato.jpeg", "1");
                ByteBuf byteBuf = Unpooled.copiedBuffer(chatMessage.toByteArray());
                WebSocketFrame joinFrame = new BinaryWebSocketFrame(byteBuf);
                ch.writeAndFlush(joinFrame);


//                BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
//                while (true) {
//                    System.out.println("Input:");
//                    String msg = console.readLine();
//                    if (msg == null) {
//                        break;
//                    } else if ("bye".equals(msg.toLowerCase())) {
//                        ch.writeAndFlush(new CloseWebSocketFrame());
//                        ch.closeFuture().sync();
//                        break;
//                    } else if ("ping".equals(msg.toLowerCase())) {
//                        WebSocketFrame frame = new PingWebSocketFrame(Unpooled.wrappedBuffer(new byte[]{8, 1, 8, 1}));
//                        ch.writeAndFlush(frame);
//                    } else {
//                        ChatMessageProto.ChatMessage message = ChatMessageCore.createSystemMessage("测试");
//                        ByteBuf buf = Unpooled.copiedBuffer(message.toByteArray());
//                        WebSocketFrame frame = new BinaryWebSocketFrame(buf);
//                        ch.writeAndFlush(frame);
//                    }
//                }
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
