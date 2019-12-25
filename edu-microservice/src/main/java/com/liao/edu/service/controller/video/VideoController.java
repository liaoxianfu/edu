package com.liao.edu.service.controller.video;


import com.liao.edu.common.vo.R;
import com.liao.edu.service.entity.Video;
import com.liao.edu.service.service.VideoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
@RestController
@RequestMapping("/admin/edu/video")
public class VideoController {

    @Resource
    private VideoService videoService;

    @ApiOperation(value = "添加小结")
    @PostMapping
    public R addOrUpdateVideo(@ApiParam(value = "小结信息", required = true) @RequestBody Video video) {
        // 先判断小结中的章节id是否存在，如果不存在就返回失败
        if (StringUtils.isEmpty(video.getChapterId())) {
            return R.error();
        }
        // 判断是否小结的id 如果存在证明是更新 否者是添加
        if (StringUtils.isEmpty(video.getId())) {
            boolean save = videoService.save(video);
            if (save) {
                return R.ok();
            }
        } else {
            return updateVideo(video);
        }
        return R.error();
    }

    @ApiOperation(value = "通过id获取小结的信息")
    @GetMapping("/{id}")
    public R getVideoById(@ApiParam(value = "小结id") @PathVariable String id) {
        Video video = videoService.getById(id);
        return R.ok().data("data", video);
    }


    /**
     * 修改章节信息
     *
     * @param video video对象
     * @return R
     */
    public R updateVideo(Video video) {
        // 判断章节的id是否存在，不存在返回失败信息
        if (StringUtils.isEmpty(video.getChapterId())) {
            return R.error();
        }

        boolean b = videoService.updateById(video);
        if (b) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation(value = "删除小结")
    @DeleteMapping("/{id}")
    public R deleteVideoById(@ApiParam(value = "小结id") @PathVariable String id) {
        boolean b = videoService.removeById(id);
        if (b) {
            return R.ok();
        }
        return R.error();
    }


}

