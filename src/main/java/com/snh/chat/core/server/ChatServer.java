package com.snh.chat.core.server;

import com.snh.chat.util.Config;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 聊天服务器线程
 *
 * Created by xuhaifeng on 2016/6/8.
 */
public class ChatServer {
    private static int port = 8080;

    public ChatServer(int port) {
        ChatServer.port = port;
    }

    public void run() throws Exception {
        /**
         * Because the native transport is compatible with the NIO transport, you can just do the following search-and-replace:
         * NioEventLoopGroup → EpollEventLoopGroup
         * NioEventLoop → EpollEventLoop
         * NioServerSocketChannel → EpollServerSocketChannel
         * NioSocketChannel → EpollSocketChannel
         */
        EventLoopGroup bossGroup;
        EventLoopGroup workerGroup;
        if (Config.isWindows()) {
            //Windows IOCP模型
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
        } else {
            //Linux Epoll模型 不需要安装Epoll Jar包已包含Epoll的类库
            bossGroup = new EpollEventLoopGroup();
            workerGroup = new EpollEventLoopGroup();
        }
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            if (Config.isWindows()) {
                bootstrap.channel(NioServerSocketChannel.class);
            } else {
                bootstrap.channel(EpollServerSocketChannel.class);
            }
            bootstrap.childHandler(new ChatServerInitializer());
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture channelFuture = bootstrap.bind(port).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
