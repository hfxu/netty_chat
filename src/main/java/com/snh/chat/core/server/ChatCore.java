package com.snh.chat.core.server;

import com.google.common.collect.RowSortedTable;
import com.google.common.collect.TreeBasedTable;
import com.snh.chat.protobuf.ChatMessageProto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.ChannelMatchers;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 聊天群组管理
 * Created by xuhaifeng on 2016/6/22.
 */
public class ChatCore {
    private static final Logger logger = Logger.getLogger(ChatCore.class.getName());

    //创建信道群组
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * 创建群组与用户与信息之间的关系
     * Table <群组ID,信道ID,用户ID>
     */
    private static RowSortedTable<String, String, String> groups = TreeBasedTable.create();

    /**
     * 消息解码
     */
    public static MessageDecoder decoder = new MessageDecoder();


    /**
     * 从群组中移除
     * 只应该在关闭链接或异常时调用
     *
     * @param channelId 信道ID
     */
    public static void remove(String channelId) {
        Map<String, String> map = groups.column(channelId);
        if (map.size() > 0) {
            map.forEach((k, v) -> groups.remove(k, channelId));
        }
    }

    /**
     * 从群组中移除
     * 推荐使用此方法
     *
     * @param groupId   群组ID
     * @param channelId 信道ID
     */
    public static void remove(String groupId, String channelId) {
        groups.remove(groupId, channelId);
    }

    /**
     * 添加到群组中
     * 其中groupId和channelId唯一，当有2组相同数据时，新数据会覆盖旧数据
     *
     * @param groupId   群组ID
     * @param channelId 信道ID
     * @param userId    用户ID
     */
    public static void put(String groupId, String channelId, String userId) {
        groups.put(groupId, channelId, userId);
    }

    /**
     * 处理聊天消息
     */
    public static void process(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
            List<Object> list = new ArrayList<>(1);
            decoder.decodeMessage(ctx, frame.content(), list);
            if (list.size() > 0) {
                ChatMessageProto.ChatMessage message = (ChatMessageProto.ChatMessage) list.get(0);
                if (message == null) {
                    logger.warn("传入的消息为null");
                    return;
                }
                ChannelMatcher matcher;
                if (message.getAction() != 1) {
                    Map<String, String> map = groups.row(message.getGroupId());
                    matcher = new ChatChannelMatcher(map.keySet());
                } else {
                    matcher = ChannelMatchers.all();
                }
                String channelId = ctx.channel().id().asLongText();
                //消息类型 0-普通消息/回复消息 1-系统消息  2-加入群组 3-离开群组
                switch (message.getAction()) {
                    case 2:
                        put(message.getGroupId(), channelId, message.getUserId());
                        break;
                    case 3:
                        remove(message.getGroupId(), channelId);
                        break;
                    case 0:
                        //普通消息不作额外处理
                        break;
                    case 1:
                        //系统消息暂不做任何额外处理
                        break;
                }
                ChatMessageProto.ChatMessage.Builder builder = message.toBuilder();
                builder.setTimestamp(System.currentTimeMillis());
                message = builder.build();
                ByteBuf byteBuf = Unpooled.copiedBuffer(message.toByteArray());
                frame = new BinaryWebSocketFrame(byteBuf);
                channels.writeAndFlush(frame, matcher);
            }
        } else if (msg instanceof CloseWebSocketFrame) {
            ctx.close();
        } else {
            String message = "unsupported frame type: " + msg.getClass().getName();
            logger.error(message);
        }
    }
}
