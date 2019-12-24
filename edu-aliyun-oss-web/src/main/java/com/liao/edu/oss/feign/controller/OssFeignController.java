package com.liao.edu.oss.feign.controller;

import com.liao.edu.common.vo.R;
import com.liao.edu.oss.feign.service.OssFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author liao
 * @since 2019/12/24 15:08
 */
@Api("阿里云oss文件上传")
@RestController
@RequestMapping("/v1/admin/oss/file")
public class OssFeignController {
    @Resource
    private OssFeignService service;

    @ApiOperation(value = "文件上传到阿里云")
//    要指定文件的上传类型
    @PostMapping(value = "/upload/{fileHost}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R upload(@ApiParam(name = "file", value = "文件", required = true)
                    @RequestPart MultipartFile file,  @PathVariable String fileHost) {
        return service.upload(file, fileHost);
    }

}
