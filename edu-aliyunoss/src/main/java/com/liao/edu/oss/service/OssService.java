package com.liao.edu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author liao
 * create at 2019/12/4 19:22
 */
public interface OssService {
    /**
     * 上传文件到阿里云oss
     *
     * @param file 文件
     * @return url
     */
    String upload(MultipartFile file);
}
