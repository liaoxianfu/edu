package com.liao.edu.service.feign.service.video;

import com.liao.edu.common.entity.Video;
import com.liao.edu.common.vo.R;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author liao
 * @since 2019/12/25 9:27
 */
@FeignClient(value = "edu-microservice")
@RequestMapping("/admin/edu/video")
public interface VideoFeignService {

    @PostMapping
    public R addOrUpdateVideo(@RequestBody Video video);

    @GetMapping("/{id}")
    public R getVideoById(@PathVariable String id);

    @DeleteMapping("/{id}")
    public R deleteVideoById(@PathVariable String id);
}
