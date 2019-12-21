package com.liao.edu.video.service;

import com.liao.edu.video.entity.EduResource;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liao
 * @since 2019-12-21
 */
public interface EduResourceService extends IService<EduResource> {
    /**
     * 上传文件
     * @param file 文件
     * @return eduResource
     */
     String uploadResource(String course,MultipartFile file);

     boolean deleteResource(EduResource eduResource);

     String getUploadFileNameById(String id);
}
