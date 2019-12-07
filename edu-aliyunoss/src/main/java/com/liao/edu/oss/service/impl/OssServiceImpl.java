package com.liao.edu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.liao.edu.oss.entity.OssEntity;
import com.liao.edu.oss.service.OssService;
import com.liao.edu.oss.util.OssEntityBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author liao
 * create at 2019/12/4 19:23
 */

@Service
@Slf4j
public class OssServiceImpl implements OssService {
    @Override
    public String upload(MultipartFile file, String fileHost) {
        OssEntity ossEntity = new OssEntityBuilder().build();
        ossEntity.setFileHost(fileHost);
        String uploadUrl = null;
        try {
            // 文件名 UUID+文件扩展名
            // 获取文件扩展名
            String originalFilename = file.getOriginalFilename();
            log.info("文件名{}", originalFilename);
            assert originalFilename != null;
            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
            log.info("文件扩展名{}", fileType);
            // 获取UUID
            String fileName = UUID.randomUUID().toString();
            String newFileName = ossEntity.getFileHost() + "/" + fileName + fileType;
            InputStream inputStream = file.getInputStream();
            // 获取oss客户端
            OSS ossClient = ossEntity.getOssClient();
            String bucketName = ossEntity.getBucketName();
            ossClient.putObject(bucketName, newFileName, inputStream);
            String endpoint = ossEntity.getEndpoint();
            uploadUrl = "https://" + bucketName + "." + endpoint + "/" + newFileName;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭对象存储
            ossEntity.close();
        }
        return uploadUrl;
    }
}
