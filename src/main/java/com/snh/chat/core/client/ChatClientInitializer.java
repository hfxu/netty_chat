package com.snh.chat.core.client;

import com.snh.chat.protobuf.ChatMessageProto;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LoggingHandler;


/**
 * 聊天客户端编解码初始化
 * Created by xuhaifeng on 2016/6/8.
 */
public class ChatClientInitializer extends ChannelInitializer<NioSocketChannel> {
    private ChatClientHandler handler;

    public ChatClientInitializer(ChatClientHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("Logger", new LoggingHandler());
        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(WebSocketClientCompressionHandler.INSTANCE);
        //解码
        pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());

        //构造函数传递要解码的类型
        pipeline.addLast("protobufDecoder", new ProtobufDecoder(ChatMessageProto.ChatMessage.getDefaultInstance()));

        //编码
        pipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast("protobufEncoder", new ProtobufEncoder());

        //业务逻辑处理
        pipeline.addLast(handler);
    }
}
