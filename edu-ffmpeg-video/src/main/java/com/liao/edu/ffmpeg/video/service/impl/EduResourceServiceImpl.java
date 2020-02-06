package com.liao.edu.ffmpeg.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.edu.common.constants.ResultCodeEnum;
import com.liao.edu.common.entity.EduResource;
import com.liao.edu.common.exception.EduException;
import com.liao.edu.ffmpeg.video.config.Ffmpeg;
import com.liao.edu.ffmpeg.video.mapper.EduResourceMapper;
import com.liao.edu.ffmpeg.video.service.EduResourceService;
import com.liao.edu.ffmpeg.video.utils.FileStorage;
import com.liao.edu.ffmpeg.video.utils.FileSystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liao
 * @since 2020/2/6 18:34
 */
@Slf4j
@Service
public class EduResourceServiceImpl extends ServiceImpl<EduResourceMapper, EduResource> implements EduResourceService {

    @Resource
    private FileStorage fileStorage;

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private Ffmpeg ffmpeg;


    @Override
    public void uploadResource(String courseId, String token, MultipartFile file) {
        // 获取带扩展名的文件名
        String originalFilename = file.getOriginalFilename();
        log.info("originalFilename  {}", originalFilename);
        FileSystemUtils fileSystemUtils = FileSystemUtils.getInstance();
        assert originalFilename != null;
        String fileName = fileSystemUtils.getInputFile(ffmpeg.getUploadPath(), originalFilename);
        log.info("fileName  {}", fileName);

        try {
            // 上传文件
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(fileName));
            // 创建转码文件
            String outPutPath = fileSystemUtils.getFfmpegOutPutPath(ffmpeg.getUploadPath(), originalFilename);
            // 推送到rabbitmq进行转码
            Map<String, String> map = new HashMap<>(3);
            map.put("in", fileName);
            map.put("out", outPutPath);
            map.put("token", token);
            map.put("courseId", courseId);
            rabbitTemplate.convertAndSend(ffmpeg.getExchange(), ffmpeg.getRoutingKey(), map);
        } catch (IOException e) {
            log.error("发送转码到队列错误{}", e.getMessage());
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
                fileStorage.deleteFile(bucketName, fileName);
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
                return fileStorage.downloadFile(bucket, fileName);
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
                return fileStorage.downloadFile(bucket, fileName);
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
