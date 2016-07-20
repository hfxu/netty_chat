package com.snh.chat.protobuf;

import com.snh.chat.core.server.ChatCore;

import java.util.UUID;

/**
 * 发送消息的方法
 * Created by xuhaifeng on 2016/6/20.
 */
public class ChatMessageCore {
    /**
     * 构造系统消息
     *
     * @param content 系统消息内容
     * @return {@link ChatMessageProto.ChatMessage}
     */
    public static ChatMessageProto.ChatMessage createSystemMessage(String content) {
        ChatMessageProto.ChatMessage.Builder builder = ChatMessageProto.ChatMessage.newBuilder();
        builder.setAction(1);
        /**
         * "S-" + UUID.randomUUID() 系统消息
         * "U-{userId}-{groupId}"+UUID.randomUUID() 用户消息
         */
        builder.setUuid("S-" + UUID.randomUUID());
        builder.setUserId("-1");
        builder.setGroupId("-1");
        builder.setNickname("系统消息");
        builder.setContent(content);
        builder.setTimestamp(System.currentTimeMillis());
        return builder.build();
    }

    /**
     * 构造加入群组消息
     *
     * @param userId   用户ID
     * @param nickname 用户昵称
     * @param groupId  群组ID
     * @param pic      头像
     * @return {@link ChatMessageProto.ChatMessage}
     */
    public static ChatMessageProto.ChatMessage createJoinGroupMessage(String userId, String nickname, String pic, String groupId) {
        ChatMessageProto.ChatMessage.Builder builder = ChatMessageProto.ChatMessage.newBuilder();
        builder.setAction(2);
        /**
         * "S-" + UUID.randomUUID() 系统消息
         * "U-{userId}-{groupId}-"+UUID.randomUUID() 用户消息
         */
        builder.setUuid("U-" + userId + "-" + groupId + UUID.randomUUID());
        builder.setUserId(userId);
        builder.setGroupId(groupId);
        builder.setNickname(nickname);
        builder.setPic(pic);
        builder.setContent(nickname + " 加入了群组");
        builder.setTimestamp(System.currentTimeMillis());
        return builder.build();
    }

    /**
     * 构造离开群组消息
     *
     * @param userId   用户ID
     * @param nickname 用户昵称
     * @param groupId  群组ID
     * @param pic      头像
     * @return {@link ChatMessageProto.ChatMessage}
     */
    public static ChatMessageProto.ChatMessage createLeaveGroupMessage(String userId, String nickname, String pic, String groupId) {
        ChatMessageProto.ChatMessage.Builder builder = ChatMessageProto.ChatMessage.newBuilder();
        builder.setAction(3);
        /**
         * "S-" + UUID.randomUUID() 系统消息
         * "U-{userId}-{groupId}-"+UUID.randomUUID() 用户消息
         */
        builder.setUuid("U-" + userId + "-" + groupId + UUID.randomUUID());
        builder.setUserId(userId);
        builder.setGroupId(groupId);
        builder.setNickname(nickname);
        builder.setPic(pic);
        builder.setContent(nickname + " 离开了了群组");
        builder.setTimestamp(System.currentTimeMillis());
        return builder.build();
    }

    /**
     * 构造普通群组聊天消息
     *
     * @param userId   用户ID
     * @param nickname 用户昵称
     * @param groupId  群组ID
     * @param pic      头像
     * @param content  内容
     * @return {@link ChatMessageProto.ChatMessage}
     */
    public static ChatMessageProto.ChatMessage createUserChatMessage(String userId, String nickname, String pic, String groupId, String content) {
        ChatMessageProto.ChatMessage.Builder builder = ChatMessageProto.ChatMessage.newBuilder();
        builder.setAction(0);
        /**
         * "S-" + UUID.randomUUID() 系统消息
         * "U-{userId}-{groupId}-"+UUID.randomUUID() 用户消息
         */
        builder.setUuid("U-" + userId + "-" + groupId + UUID.randomUUID());
        builder.setUserId(userId);
        builder.setGroupId(groupId);
        builder.setNickname(nickname);
        builder.setPic(pic);
        builder.setContent(content);
        builder.setTimestamp(System.currentTimeMillis());
        return builder.build();
    }


}
