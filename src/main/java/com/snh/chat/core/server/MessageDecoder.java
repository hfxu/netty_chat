package com.snh.chat.core.server;

import com.snh.chat.protobuf.ChatMessageProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

import java.util.List;

/**
 * 消息解码
 * Created by xuhaifeng on 2016/6/22.
 */
public class MessageDecoder extends ProtobufDecoder {
    public MessageDecoder() {
        super(ChatMessageProto.ChatMessage.getDefaultInstance());
    }

    /**
     * 解码消息
     *
     * @param ctx 信道处理实例
     * @param msg ByteBuf
     * @param out 输出
     * @throws Exception
     */
    public void decodeMessage(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        decode(ctx, msg, out);
    }
}
