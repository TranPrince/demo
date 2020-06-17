package com.prince.util;

import java.util.ResourceBundle;

/**
 * 读取配置文件
 *
 * @author Prince
 * @date 2020/6/18 0:45
 */
public class ResourceUtil {
    private static final ResourceBundle resourceBundle;

    static{
        resourceBundle = ResourceBundle.getBundle("config");
    }

    public static String getKey(String key){
        return resourceBundle.getString(key);
    }
}
