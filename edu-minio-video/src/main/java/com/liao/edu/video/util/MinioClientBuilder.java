package com.liao.edu.video.util;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author liao
 * @since 2019/12/21 15:28
 */
public class MinioClientBuilder {
    public MinioClient build() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(MinioClientPropertiesUtil.ENDPOINT, MinioClientPropertiesUtil.ACCESSKEY, MinioClientPropertiesUtil.ACCESSKEY_SECRET);
    }
}
