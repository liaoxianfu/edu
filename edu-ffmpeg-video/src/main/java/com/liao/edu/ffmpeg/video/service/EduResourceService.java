package com.liao.edu.ffmpeg.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liao.edu.common.entity.EduResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liao
 * @since 2019-12-21
 */
public interface EduResourceService extends IService<EduResource> {
    /**
     * 上传视频文件
     *
     * @param courseId 课程ID
     * @param file     文件
     */
    void uploadResource(String courseId,String token, MultipartFile file);

    /**
     * 删除数据
     *
     * @param eduResource eduSource对象
     * @return
     */
    boolean deleteResource(EduResource eduResource);

    /**
     * 通过id获取文件名
     *
     * @param id id
     * @return 文件名
     */
    String getUploadFileNameById(String id);


    /**
     * 通过文件id获取文件
     *
     * @param id id
     * @return inputstream
     */
    InputStream getFileObjectById(String id);

    /**
     * 通过eduSource获取文件
     *
     * @param eduResource 对象
     * @return 输入流
     */
    InputStream getFileObject(EduResource eduResource);


}
