package com.liao.edu.oss.controller;

import com.liao.edu.common.constants.ResultCodeEnum;
import com.liao.edu.common.vo.R;
import com.liao.edu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liao
 * create at 2019/12/4 20:15
 */
@Api("阿里云oss文件上传")
@RestController
@Slf4j
@RequestMapping("/admin/oss/file")
public class OssController {

    private final OssService ossService;


    public OssController(OssService ossService) {
        this.ossService = ossService;
    }

    @PostMapping("/upload/{fileHost}")
    public R upload(@ApiParam(name = "file", value = "文件", required = true)
                    @RequestParam MultipartFile file, @PathVariable String fileHost) {
        String upload = ossService.upload(file, fileHost);
        if (!StringUtils.isEmpty(upload)) {
            return R.ok().message("文件上传成功").data("url", upload);
        } else {
            return R.error().message("文件上传失败");
        }
    }
}
