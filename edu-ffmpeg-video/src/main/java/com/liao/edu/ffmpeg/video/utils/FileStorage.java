package com.liao.edu.ffmpeg.video.utils;

import com.liao.edu.ffmpeg.video.exception.BucketException;

import java.io.InputStream;

/**
 * 实现文件上传的文件接口
 *
 * @author liao
 * @since 2020/2/5 13:18
 */
public interface FileStorage {
    /**
     * 通过源文件的路径地址上传到文件系统
     *
     * @param bucket  存储桶
     * @param srcFile 上传文件
     * @param desFile 目标路径
     */
    boolean uploadFile(String bucket, String srcFile, String desFile) throws BucketException;

    /**
     * 通过文件流的路径地址上传到文件系统
     *
     * @param bucket      存储桶
     * @param descFile    上传文件名
     * @param inputStream 输入流
     * @return 是否创建成功
     * @throws BucketException 异常
     */
    boolean uploadFile(String bucket, String descFile, InputStream inputStream) throws BucketException;

    /**
     * 删除文件
     *
     * @param bucket  存储桶
     * @param objName 文件名
     * @return 是否删除
     */
    boolean deleteFile(String bucket, String objName);

    /**
     * 下载文件
     *
     * @param bucket  存储桶
     * @param objName 文件对象
     */
    InputStream downloadFile(String bucket, String objName);

}
