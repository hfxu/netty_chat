package com.snh.chat.protobuf;

import java.io.IOException;

/**
 * Proto Java类生成
 * Created by xuhaifeng on 2016/6/17.
 */
public class GenerateProto {
    public static void main(String[] args) throws IOException {
        String[] protos = {"chatmessage.proto"};
        String PROJECT_PATH = "F:/data/projects/chat/";
        String rootPath = PROJECT_PATH + "src/main/java/";
//        String jsPath = PROJECT_PATH + "client/web/js/proto/";
        for (String proto : protos) {
            Process proccess = Runtime.getRuntime().exec(
                    "F:/env/protoc-2.6.1-win32/protoc.exe" +
                            " -I=" + rootPath + "com/snh/chat/protobuf/proto/" +
                            " --java_out=" + rootPath +
                            " " + rootPath + "com/snh/chat/protobuf/proto/" + proto);
        }
        System.out.println("生成完毕");
    }
}
