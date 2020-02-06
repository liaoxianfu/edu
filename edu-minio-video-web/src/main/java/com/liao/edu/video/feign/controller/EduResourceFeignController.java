package com.liao.edu.video.feign.controller;

import com.liao.edu.common.vo.R;
import com.liao.edu.video.feign.service.EduResourceFeignService;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author liao
 * @since 2019/12/23 18:38
 */
@Api(description = "eduResource Feign客户调用端")
@RestController
@RequestMapping("/v1/edu/edu-resource")
public class EduResourceFeignController {
    @Resource
    private EduResourceFeignService service;

    @ApiOperation(value = "视频文件上传")
    @PostMapping(value = "/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R uploadFile(@RequestParam String courseId, @RequestPart MultipartFile file) {

        return service.uploadFile(courseId, file);
    }

    @ApiOperation(value = "删除视频")
    @DeleteMapping("/video/{courseId}")
    public R deleteFile(@PathVariable String courseId) {
        return service.deleteFile(courseId);
    }

    @ApiOperation(value = "获取上传文件的原文名")
    @RequestMapping(value = "/video/{id}", method = RequestMethod.GET)
    public R getVideoNameById(@PathVariable String id) {
        return service.getVideoNameById(id);
    }

    @ApiOperation(value = "通过文件id获取视频的文件")
    @GetMapping("/video/file/{id}")
    public void getVideoFileByName(@PathVariable String id, HttpServletResponse servletResponse) {
        R r = service.getVideoNameById(id);
        String name = (String) r.getData().get("name");
        InputStream inputStream;
        byte[] bytes = new byte[1024 * 8];
        Response response = service.getVideoFileByName(id);
        try {
            inputStream = response.body().asInputStream();
            int i = inputStream.read(bytes);
            // 设置下载文件的信息
            servletResponse.setHeader("Content-Disposition", "attachment;filename=" + name);
            ServletOutputStream outputStream = servletResponse.getOutputStream();
            while (i > 0) {
                outputStream.write(bytes, 0, i);
                i = inputStream.read(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/video/tencent/signature")
    public R getSignature() {
        return service.getSignature();
    }
}
