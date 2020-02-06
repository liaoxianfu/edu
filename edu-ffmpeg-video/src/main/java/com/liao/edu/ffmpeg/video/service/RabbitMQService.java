package com.liao.edu.ffmpeg.video.service;

import com.alibaba.fastjson.JSON;
import com.liao.edu.common.entity.EduResource;
import com.liao.edu.common.vo.R;
import com.liao.edu.ffmpeg.video.config.Ffmpeg;
import com.liao.edu.ffmpeg.video.config.WebSocketServer;
import com.liao.edu.ffmpeg.video.exception.BucketException;
import com.liao.edu.ffmpeg.video.utils.FfmpegToMp4VideoUtils;
import com.liao.edu.ffmpeg.video.utils.FileStorage;
import com.liao.edu.ffmpeg.video.utils.FileSystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author liao
 * @since 2020/2/4 17:41
 */

@Slf4j
@Service
public class RabbitMQService {
    @Resource
    private Ffmpeg ffmpeg;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.video-path}")
    private String videoPath;


    @Autowired
    private FileStorage fileStorage;

    @Autowired
    private EduResourceService eduResourceService;

    @RabbitListener(queues = "${ffmpeg.queues}")
    public void receive(Map<String, String> map) {
        String uploadFile = map.get("in");
        String encodeFile = map.get("out");
        String token = map.get("token");
        String courseId = map.get("courseId");
        // 通知开始转码
        try {
            log.info("开始转码userId:{},uploadFile:{},encodeFile:{}", token, uploadFile, encodeFile);
            WebSocketServer.sendInfo("开始转码", token);
        } catch (IOException e) {
            log.error("发送转码失败{}", e.getMessage());
        }
        boolean b = FfmpegToMp4VideoUtils.processVideo(ffmpeg.getExePath(), uploadFile, encodeFile);
        log.info("视频转码将结果:{}", b);
        // 删除上传文件
        boolean delete = FileSystemUtils.removeFile(uploadFile);
        log.info("删除上传文件{},结果{}", uploadFile, delete);
        //  上传到分布式文件系统 删除文件
        try {
            log.info("上传转码文件{}", encodeFile);
            // 上传文件的路径规划
            String videoName = encodeFile.substring(encodeFile.lastIndexOf("/"));
            String fullVideoName = videoPath + "/" + courseId + videoName;
            log.info("获取到上传视频文件名称为{}", fullVideoName);
            fileStorage.uploadFile(bucket, encodeFile, fullVideoName);
            FileSystemUtils.removeFile(encodeFile);
            log.info("删除本地文件{}", encodeFile);
            log.info("上传文件成功 token:{},uploadFile:{},encodeFile:{}", token, uploadFile, fullVideoName);
            EduResource eduResource = new EduResource();

//             利用bucket与fullVideoName拼接保存到数据库
            String videoUrl = bucket + "/" + fullVideoName;
            eduResource.setVideoUrl(videoUrl);
            eduResourceService.save(eduResource);
            log.info("保存视频信息到数据库成功  {}", videoUrl);
            WebSocketServer.sendInfo(eduResource.getId(), token);
        } catch (BucketException | IOException e) {
            try {
                log.error("转码失败{}", e.getMessage());
                WebSocketServer.sendInfo("转码失败", token);
            } catch (IOException ex) {
                log.error("发送转码失败信息失败{}", e.getMessage());
            }
        }
    }

}
