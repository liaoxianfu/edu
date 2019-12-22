package com.liao.edu.video.controller;


import com.liao.edu.common.constants.ResultCodeEnum;
import com.liao.edu.common.exception.EduException;
import com.liao.edu.common.vo.R;
import com.liao.edu.video.entity.EduResource;
import com.liao.edu.video.service.EduResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liao
 * @since 2019-12-21
 */

@Api(description = "Minio文件存储")
@Slf4j
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

    @ApiOperation(value = "通过文件id获取视频的文件")
    @GetMapping("/video/file/{id}")
    public void getVideoFileByName(@PathVariable String id, HttpServletResponse response) {
        // 通过id获取文件对象
        EduResource eduResource = eduResourceService.getById(id);
        // 定义文件缓存
        byte[] buffer = new byte[1024];
        //输出流
        OutputStream os = null;
        // 获取名称
        String[] split = eduResource.getVideoUrl().split("/");
        String fileName = split[split.length - 1];
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            os = response.getOutputStream();
            InputStream inputStream = eduResourceService.getFileObject(eduResource);
            int i = inputStream.read(buffer);
            while (i > 0) {
                os.write(buffer, 0, i);
                i = inputStream.read(buffer);
            }
            inputStream.close();
            os.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new EduException(ResultCodeEnum.DOWNLOAD_FILE_ERROR);
        }
    }
}

