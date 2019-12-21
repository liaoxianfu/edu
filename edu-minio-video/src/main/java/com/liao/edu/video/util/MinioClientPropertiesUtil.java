package com.liao.edu.video.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author liao
 * @since 2019/12/21 15:30
 */

@Component
public class MinioClientPropertiesUtil implements InitializingBean {

    @Value("${minio.oss.endpoint}")
    private String endpoint;
    @Value("${minio.oss.accessKey}")
    private String accessKey;
    @Value("${minio.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${minio.oss.bucket}")
    private String bucket;
    @Value("${minio.oss.videoPath}")
    private String videoPath;
    @Value("${minio.oss.imgPath}")
    private String imgPath;
    @Value("${minio.oss.otherPath}")
    private String otherPath;

    public static String ENDPOINT;
    public static String ACCESSKEY;
    public static String ACCESSKEY_SECRET;
    public static String BUCKET;
    public static String VIDEO_PATH;
    public static String IMG_PATH;
    public static String OTHER_PATH;


    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        ACCESSKEY = accessKey;
        ACCESSKEY_SECRET = accessKeySecret;
        BUCKET = bucket;
        VIDEO_PATH = videoPath;
        IMG_PATH = imgPath;
        OTHER_PATH = otherPath;
    }
}
