package com.snh.chat;

import com.snh.chat.core.server.ChatServer;
import org.apache.log4j.PropertyConfigurator;

/**
 * 启动入口
 * Created by xuhaifeng on 2016/6/7.
 */
public class Application {
    public static void main(String[] args) throws Exception {
//        BasicConfigurator.configure();
        PropertyConfigurator.configure("/etc/chat/log4j.properties");
        new ChatServer(8080).run();
    }
}
