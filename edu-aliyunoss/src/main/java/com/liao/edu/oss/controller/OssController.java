package com.liao.edu.oss.controller;

import com.liao.edu.common.vo.R;
import com.liao.edu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liao
 * create at 2019/12/4 20:15
 */
@Api("阿里云oss文件上传")
@RestController
@RequestMapping("/admin/oss/file")
public class OssController {

    private final OssService ossService;


    public OssController(OssService ossService) {
        this.ossService = ossService;
    }

    @PostMapping("/upload")
    public R upload(@ApiParam(name = "file", value = "文件", required = true)
                    @RequestParam MultipartFile file) {
        String upload = ossService.upload(file);
        if (!StringUtils.isEmpty(upload)) {
            return R.ok().message("文件上传成功").data("url", upload);
        } else {
            return R.error().message("文件上传失败");
        }
    }

}
