package com.liao.edu.ffmpeg.video.utils.impl;

import com.liao.edu.ffmpeg.video.exception.BucketException;
import com.liao.edu.ffmpeg.video.utils.FileStorage;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author liao
 * @since 2020/2/5 13:33
 */
@Slf4j
public class MinioFileStorageImpl implements FileStorage {

    @Resource
    private MinioClient client;

    private boolean bucketIsExistAndCreate(String bucket) {
        try {
            boolean exists = client.bucketExists(bucket);
            // 如果不存在就创建该存储桶
            if (!exists) {
                client.makeBucket(bucket);
            }
            return true;
        } catch (Exception e) {
            log.error("检查bucket失败   {}", e.getMessage());
        }
        return false;
    }


    @Override
    public boolean uploadFile(String bucket, String srcFile, String desFile) throws BucketException {
        // 判断minio是否准备好存储桶
        boolean b = bucketIsExistAndCreate(bucket);
        boolean status = false;
        if (!b) {
            throw new BucketException("检查或者创建bucket失败");
        }
        try {
            InputStream inputStream = new FileInputStream(srcFile);
            status = uploadFile(bucket, desFile, inputStream);
        } catch (Exception e) {
            log.error("上传失败{}", e.getMessage());
            return false;
        }
        return status;
    }

    @Override
    public boolean uploadFile(String bucket, String descFile, InputStream inputStream) throws BucketException {
        // 判断minio是否准备好存储桶
        boolean b = bucketIsExistAndCreate(bucket);
        if (!b) {
            throw new BucketException("检查或者创建bucket失败");
        }
        try {
            client.putObject(bucket, descFile, inputStream, (long) inputStream.available(), null, null, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteFile(String bucket, String objName) {
        try {
            client.removeObject(bucket, objName);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public InputStream downloadFile(String bucket, String objName) {
        InputStream inputStream = null;
        try {
            log.info("下载文件{}，{}", bucket, objName);
            inputStream = client.getObject(bucket, objName);
        } catch (Exception e) {
            log.error("下载文件{},{}出错", bucket, objName);
            log.error(e.getMessage());
        }
        return inputStream;
    }
}
