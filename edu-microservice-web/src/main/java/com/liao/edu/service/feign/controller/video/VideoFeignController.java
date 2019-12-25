package com.liao.edu.service.feign.controller.video;

import com.liao.edu.common.entity.Video;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.feign.service.video.VideoFeignService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liao
 * @since 2019/12/25 10:09
 */

@RestController
@RequestMapping("/v1/admin/edu/video")
public class VideoFeignController {
    @Resource
    private VideoFeignService service;

    @ApiOperation(value = "添加小结")
    @PostMapping
    public R addOrUpdateVideo(@ApiParam(value = "小结信息", required = true) @RequestBody Video video) {
        return service.addOrUpdateVideo(video);
    }

    @ApiOperation(value = "通过id获取小结的信息")
    @GetMapping("/{id}")
    public R getVideoById(@ApiParam(value = "小结id") @PathVariable String id) {
        return service.getVideoById(id);
    }

    @ApiOperation(value = "删除小结")
    @DeleteMapping("/{id}")
    public R deleteVideoById(@ApiParam(value = "小结id") @PathVariable String id) {
        return service.deleteVideoById(id);
    }


}
