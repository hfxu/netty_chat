package com.snh.chat.core.server;

import com.snh.chat.protobuf.ChatMessageProto;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LoggingHandler;


/**
 * 聊天服务器编解码初始化
 * Created by xuhaifeng on 2016/6/8.
 */
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
    private static final String WEB_SOCKET_PATH = "/websocket";

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("Logger", new LoggingHandler());
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(WEB_SOCKET_PATH, null, true));
        //解码
        pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());

        //构造函数传递要解码的类型
        pipeline.addLast("protobufDecoder", new ProtobufDecoder(ChatMessageProto.ChatMessage.getDefaultInstance()));

        //编码
        pipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast("protobufEncoder", new ProtobufEncoder());


        //业务逻辑处理
//        pipeline.addLast(new WebSocketIndexPageHandler(WEB_SOCKET_PATH));
        pipeline.addLast(new ChatServerHandler());
    }
}
