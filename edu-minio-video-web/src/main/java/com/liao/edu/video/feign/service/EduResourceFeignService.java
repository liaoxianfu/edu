package com.liao.edu.video.feign.service;

import com.liao.edu.common.vo.R;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liao
 * @since 2019/12/23 18:27
 */

@FeignClient(value = "edu-minio-video")
@RequestMapping("/edu/edu-resource")
public interface EduResourceFeignService {

    class MultipartSupportConfig {
        @Bean
        public SpringFormEncoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }


    @PostMapping(value = "/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R uploadFile(@RequestParam String courseId, @RequestPart MultipartFile file);

    @DeleteMapping("/video/{courseId}")
    public R deleteFile(@PathVariable String courseId);
}
