package com.liao.edu.service.feign.controller;

import com.liao.edu.common.entity.Chapter;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.feign.service.ChapterFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liao
 * @since 2019/12/24 15:58
 */
@Api(description = "章节信息添加")
@RestController
@RequestMapping("/v1/admin/edu/chapter")
public class ChapterFeignController {
    @Resource
    private ChapterFeignService service;

    @ApiOperation(value = "通过课程的ID获取章节信息")
    @GetMapping("/chapterVoList/{courseId}")
    public R getChapterVo(@ApiParam(value = "课程id", required = true) @PathVariable String courseId) {
        return service.getChapterVo(courseId);
    }

    @ApiOperation(value = "添加章节信息")
    @PostMapping
    public R addChapter(@ApiParam(value = "章节信息", required = true) @RequestBody Chapter chapter) {
        return service.addChapter(chapter);
    }

    @ApiOperation(value = "根据章节的id更新章节的信息")
    @PutMapping
    public R updateChapterById(@ApiParam(value = "章节信息", required = true) @RequestBody Chapter chapter) {
        return service.updateChapterById(chapter);
    }

    @ApiOperation(value = "根据章节的id删除章节的信息")
    @DeleteMapping("/{id}")
    public R deleteChapterById(@PathVariable String id) {
        return service.deleteChapterById(id);
    }
}
