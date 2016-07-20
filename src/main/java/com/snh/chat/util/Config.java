package com.snh.chat.util;

import java.io.File;
import java.util.Objects;

/**
 * 配置工具
 * Created by xuhaifeng on 2016/6/22.
 */
public class Config {
    private static boolean isWindows;

    static {
        isWindows = Objects.equals(File.separator, "\\");
    }

    /**
     * 是否是Windows操作系统
     *
     * @return boolean
     */
    public static boolean isWindows() {
        return isWindows;
    }
}
