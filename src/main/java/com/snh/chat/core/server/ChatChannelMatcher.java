package com.snh.chat.core.server;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;

import java.util.Set;

/**
 * 实现ChannelMatcher
 * Created by xuhaifeng on 2016/6/22.
 */
public class ChatChannelMatcher implements ChannelMatcher {
    /**
     * 匹配的信道ID集合
     */
    private Set<String> set;

    public ChatChannelMatcher(Set<String> set) {
        this.set = set;
    }

    @Override
    public boolean matches(Channel channel) {
        return set.contains(channel.id().asLongText());
    }
}
