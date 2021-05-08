package com.aurora.happy.utils;

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
        File file = new File(path);
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
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            return file.delete();
        } else {
            return false;
        }
    }

    /**
     * 指定文件或文件夹删除
     */
    public static boolean deleteAnyone(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) return false;
        return file.isFile() ? deleteFile(fileName) : deleteDir(fileName);
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
}