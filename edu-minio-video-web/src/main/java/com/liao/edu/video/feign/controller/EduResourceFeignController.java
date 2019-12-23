package com.liao.edu.video.feign.controller;

import com.liao.edu.common.vo.R;
import com.liao.edu.video.feign.service.EduResourceFeignService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author liao
 * @since 2019/12/23 18:38
 */
@RestController
@RequestMapping("/api/edu/edu-resource")
public class EduResourceFeignController {
    @Resource
    private EduResourceFeignService service;

    @PostMapping(value = "/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R uploadFile(@RequestParam String courseId, @RequestPart MultipartFile file) {

        return service.uploadFile(courseId, file);
    }

    @DeleteMapping("/video/{courseId}")
    public R deleteFile(@PathVariable String courseId){
        return service.deleteFile(courseId);
    }

}
