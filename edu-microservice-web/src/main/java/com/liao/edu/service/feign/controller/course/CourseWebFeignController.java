package com.liao.edu.service.feign.controller.course;

import com.liao.edu.common.entity.query.CourseWebQuery;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.feign.service.course.CourseWebFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liao
 * @since 2020/2/14 10:56
 */
@Api(description = "web端课程相关的操作")
@RestController
@RequestMapping("/v1/web/edu/course")
public class CourseWebFeignController {
    @Resource
    private CourseWebFeignService service;

    @GetMapping("/sub/{parentId}")
    public R getSubjectList(@PathVariable String parentId) {
        return service.getSubjectList(parentId);
    }


    @ApiOperation(value = "通过不等的等价查询对应的课程")
    @GetMapping("/list")
    public R getCourseList(
            @ApiParam(value = "等级 分为：1 全部课程 2一级subject下所有课程 3 二级subject课程",
                    required = true)
            @RequestParam(value = "level", required = true) Integer level,
            @ApiParam(value = "subjectId")
            @RequestParam(value = "id", required = false) String id) {
        return service.getCourseList(level, id);
    }

    @ApiOperation(value = "获取分页课程数据")
    @PostMapping("/list/page")
    public R getCourseList(@RequestBody CourseWebQuery courseWebQuery) {
        return service.getCourseList(courseWebQuery);
    }

    @ApiOperation(value = "获取课程页面详情数据")
    @GetMapping("/list/{courseId}")
    public R getCourseDetailInfo(@PathVariable String courseId) {
        return service.getCourseDetailInfo(courseId);
    }
}
