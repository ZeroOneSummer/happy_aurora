package com.aurora.happy.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by pijiang on 2021/5/14.
 * Properties解析
 */
@Slf4j
public class PropertyUtil {

    private static Properties props;

    static {
        loadProps();
    }

    private synchronized static void loadProps() {
        log.info("加载config.properties");
        props = new Properties();
        InputStream is = null;
        try {
            is = PropertyUtil.class.getClassLoader().getResourceAsStream("config.properties");
            props.load(is);
        } catch (FileNotFoundException e) {
            log.error("properties文件未找到");
        } catch (IOException e) {
            log.error("IOException");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("输入流关闭异常");
                }
            }
        }
        log.info("properties文件内容：" + props);
    }

    public static String getProperty(String key) {
        if (props == null) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
