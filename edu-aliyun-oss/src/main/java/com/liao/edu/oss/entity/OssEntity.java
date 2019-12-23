package com.liao.edu.oss.entity;

import com.aliyun.oss.OSS;
import lombok.Data;

/**
 * @author liao
 * create at 2019/12/4 19:46
 */
@Data
public class OssEntity {
    private String endpoint;

    private String keyId;

    private String keySecret;

    private String bucketName;

    private String fileHost;

    private OSS ossClient;

    public void close() {
        if (ossClient!=null) {
            ossClient.shutdown();
        }
    }
}
