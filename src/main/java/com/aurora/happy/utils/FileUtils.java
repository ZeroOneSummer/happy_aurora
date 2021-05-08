package com.tencent.oa.fm.mdm.customer.common.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by pijiang on 2021/5/8.
 * 文件工具类
 */
public class FileUtils {

    /**
     * 创建文件
     */
    public static File createFile(String path){
        File file = getFile(path);
        if (!file.exists() && file.isFile()){
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 文件删除
     */
    public static boolean deleteFile(String path) {
        File file = getFile(path);
        return (file.exists() && file.isFile()) ? file.delete() : false;
    }

    /**
     * 指定文件或文件夹删除
     */
    public static boolean deleteAnyone(String path) {
        File file = getFile(path);
        if (!file.exists()) return false;
        return file.isFile() ? deleteFile(path) : deleteDir(path);
    }

    /**
     * 指定的目录以及目录下的所有子文件删除
     */
    public static boolean deleteDir(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists() || (!dir.isDirectory())) return false;
        //递归删除子文件
        File[] files = dir.listFiles();
        if (files != null && files.length > 0){
            for (File file : files) {
                deleteAnyone(file.getAbsolutePath());
            }
        }
        //删除空目录
        return dir.delete();
    }

    private static File getFile(String path) {
        return new File(path);
    }
}