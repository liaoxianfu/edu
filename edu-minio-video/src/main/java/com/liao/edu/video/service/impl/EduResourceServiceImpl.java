package com.liao.edu.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.edu.common.constants.ResultCodeEnum;
import com.liao.edu.common.exception.EduException;
import com.liao.edu.video.entity.EduResource;
import com.liao.edu.video.mapper.EduResourceMapper;
import com.liao.edu.video.service.EduResourceService;
import com.liao.edu.video.util.MinioClientPropertiesUtil;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liao
 * @since 2019-12-21
 */
@Service
public class EduResourceServiceImpl extends ServiceImpl<EduResourceMapper, EduResource> implements EduResourceService {

    @Resource
    private MinioClient minioClient;


    @Override
    public String uploadResource(String courseId, MultipartFile file) {
        // 存储规则
        String objectName = MinioClientPropertiesUtil.VIDEO_PATH + "/" + courseId + "/" + System.currentTimeMillis() + "/" + file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            String bucketName = MinioClientPropertiesUtil.BUCKET;
            minioClient.putObject(bucketName, objectName, inputStream, inputStream.available(), "application/octet-stream");
            EduResource eduResource = new EduResource().setVideoUrl(bucketName + "/" + objectName);
            boolean save = this.save(eduResource);
            if (save) {
                return eduResource.getId();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EduException(ResultCodeEnum.UPLOAD_ERROR);
        }
    }

    @Override
    public boolean deleteResource(EduResource eduResource) {
        String videoUrl = eduResource.getVideoUrl();
        String[] split = videoUrl.split("/");
        String bucketName = split[0];
        String fileName = videoUrl.replace(bucketName + "/", "");
        try {
            boolean b = this.removeById(eduResource.getId());
            if (b) {
                minioClient.removeObject(bucketName, fileName);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EduException(ResultCodeEnum.DELETE_FILE_ERROR);
        }

    }

    @Override
    public String getUploadFileNameById(String id) {
        EduResource eduResource = this.getById(id);
        String videoUrl = eduResource.getVideoUrl();
        String[] split = videoUrl.split("/");
        return split[split.length - 1];
    }

    @Override
    public InputStream getFileObjectById(String id) {
        // 先获取eduSource
        EduResource eduResource = this.getById(id);
        String videoUrl = eduResource.getVideoUrl();
        // 如果获取的videoURL不为空
        if (!StringUtils.isEmpty(videoUrl)) {
            String[] split = videoUrl.split("/");
            String bucket = split[0];
            String fileName = videoUrl.replace(bucket + "/", "");
            try {
                return minioClient.getObject(bucket, fileName);
            } catch (Exception e) {
                // 获取报错信息
                log.error(e.getMessage());
                throw new EduException(ResultCodeEnum.UNKNOWN_REASON);
            }
        } else {
            return null;
        }
    }

    @Override
    public InputStream getFileObject(EduResource eduResource) {
        String videoUrl = eduResource.getVideoUrl();
        // 如果获取的videoURL不为空
        if (!StringUtils.isEmpty(videoUrl)) {
            String[] split = videoUrl.split("/");
            String bucket = split[0];
            String fileName = videoUrl.replace(bucket + "/", "");
            try {
                return minioClient.getObject(bucket, fileName);
            } catch (Exception e) {
                // 获取报错信息
                log.error(e.getMessage());
                throw new EduException(ResultCodeEnum.UNKNOWN_REASON);
            }
        } else {
            return null;
        }
    }
}


