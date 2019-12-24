package com.liao.edu.oss.feign.service;

import com.liao.edu.common.vo.R;
import feign.form.spring.SpringFormEncoder;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liao
 * @since 2019/12/24 14:59
 */

@FeignClient(value = "edu-aliyun-oss")
@RequestMapping("/admin/oss/file")
public interface OssFeignService {
    class MultipartSupportConfig {
        @Bean
        public SpringFormEncoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }

    @PostMapping(value = "/upload/{fileHost}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R upload(@ApiParam(name = "file", value = "文件", required = true)
             @RequestPart MultipartFile file, @PathVariable String fileHost);
}
