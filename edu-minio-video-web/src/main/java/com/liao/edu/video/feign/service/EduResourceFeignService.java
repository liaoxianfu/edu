package com.liao.edu.video.feign.service;

import com.liao.edu.common.vo.R;
import com.liao.edu.video.feign.service.hystrix.EduResourceHystrixImpl;
import feign.Response;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author liao
 * @since 2019/12/23 18:27
 */

@FeignClient(value = "edu-minio-video", path = "/edu/edu-resource")
public interface EduResourceFeignService {

    class MultipartSupportConfig {
        @Bean
        public SpringFormEncoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }


    @PostMapping(value = "/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R uploadFile(@RequestParam String courseId, @RequestPart MultipartFile file);

    @DeleteMapping("/video/{courseId}")
    R deleteFile(@PathVariable String courseId);

    @GetMapping("/video/{id}")
    R getVideoNameById(@PathVariable String id);

    @GetMapping("/video/file/{id}")
    Response getVideoFileByName(@PathVariable String id);

    @PostMapping("/video/tencent/signature")
    R getSignature();
}
