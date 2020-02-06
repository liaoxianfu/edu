package com.liao.edu.ffmpeg.video.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 单例模式 懒汉式 非线程安全
 *
 * @author liao
 * @since 2020/2/4 20:22
 */
public class FileSystemUtils {


    private static FileSystemUtils fileSystemUtils = null;

    private FileSystemUtils() {
    }

    public static FileSystemUtils getInstance() {
        if (fileSystemUtils == null) {
            return new FileSystemUtils();
        }
        return fileSystemUtils;
    }

    /**
     * 判断文件夹时候存在 如果不存在就创建文件夹
     *
     * @param path 路径
     * @throws IOException 抛出异常
     */
    public static void checkOrMakeDir(String path) throws IOException {
        FileUtils.forceMkdir(new File(path));
    }

    public static boolean removeFile(String pathWithFileName) {
        return new File(pathWithFileName).delete();
    }

    /**
     * 获取距离1970年1月1日的毫秒数
     *
     * @return 毫秒数
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 创建上传时缓存的文件路径
     *
     * @param uploadPath       上传文件的路径
     * @param originalFilename 带扩展的文件名
     * @return file
     */
    public String getInputFile(String uploadPath, String originalFilename) {
        String original = originalFilename.substring(originalFilename.lastIndexOf("."));
        return uploadPath + "/in/" + getCurrentTimeMillis() + original;
    }

    /**
     * 获取输出的ffmpeg的路径
     *
     * @param uploadPath       上传文件路径
     * @param originalFilename 扩展的文件名
     * @return file
     */
    public String getFfmpegOutPutPath(String uploadPath, String originalFilename) throws IOException {
        // 创建文件夹
        String dir = uploadPath + "/out/";
        checkOrMakeDir(dir);
        String original = originalFilename.substring(originalFilename.lastIndexOf("."));
        return dir + getCurrentTimeMillis() + original;
    }

}
