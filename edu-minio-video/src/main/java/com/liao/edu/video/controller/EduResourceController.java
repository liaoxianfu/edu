package com.liao.edu.video.controller;


import com.liao.edu.common.vo.R;
import com.liao.edu.video.entity.EduResource;
import com.liao.edu.video.service.EduResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liao
 * @since 2019-12-21
 */

@Api(description = "Minio文件存储")
@RestController
@RequestMapping("/edu/edu-resource")
public class EduResourceController {

    private final EduResourceService eduResourceService;

    public EduResourceController(EduResourceService eduResourceService) {
        this.eduResourceService = eduResourceService;
    }

    @ApiOperation(value = "视频文件上传")
    @PostMapping("/video")
    public R uploadFile(String courseId, MultipartFile file) {
        String id = eduResourceService.uploadResource(courseId, file);
        if (StringUtils.isEmpty(id)) {
            return R.error();
        } else {
            return R.ok().data("id", id);
        }
    }


    @ApiOperation(value = "删除视频")
    @DeleteMapping("/video/{courseId}")
    public R deleteFile(@PathVariable String courseId) {
        EduResource eduResource = eduResourceService.getById(courseId);
        String videoUrl = eduResource.getVideoUrl();
        if (StringUtils.isEmpty(videoUrl)) {
            boolean b = eduResourceService.removeById(eduResource);
            if (b) {
                return R.ok();
            } else {
                return R.error();
            }
        } else {
            boolean b = eduResourceService.deleteResource(eduResource);
            if (b) {
                return R.ok();
            } else {
                return R.error();
            }
        }
    }

    @ApiOperation(value = "获取上传文件的原文名")
    @GetMapping("/video/{id}")
    public R getVideoNameById(@PathVariable String id) {
        String name = eduResourceService.getUploadFileNameById(id);
        return R.ok().data("name", name);
    }


}

