package com.liao.edu.oss.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.liao.edu.oss.entity.OssEntity;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author liao
 * create at 2019/12/4 19:37
 */
public class OssEntityBuilder {

    private String endpoint = ConstantPropertiesUtil.END_POINT;

    private String keyId = ConstantPropertiesUtil.ACCESS_KEY_ID;

    private String keySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

    private String bucketName = ConstantPropertiesUtil.BUCKET_NAME;



    public OssEntity build() {
        OssEntity ossEntity = new OssEntity();
        ossEntity.setEndpoint(endpoint);
        ossEntity.setBucketName(bucketName);
        ossEntity.setKeyId(keyId);
        ossEntity.setKeySecret(keySecret);
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);
        ossEntity.setOssClient(ossClient);
        return ossEntity;
    }

}
