package com.liao.edu.service.feign.controller;

import com.liao.edu.common.entity.form.CourseInfoForm;
import com.liao.edu.common.entity.query.CourseQuery;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.feign.service.CourseFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liao
 * @since 2019/12/24 16:08
 */
@Api(description = "课程相关的操作")
@RestController
@RequestMapping("/admin/edu/course")
public class CourseFeignController {
    @Resource
    private CourseFeignService service;

    @ApiOperation(value = "通过条件查询获取分页数据")
    @PostMapping("/{current}/{size}")
    public R getAllCourseInfo(@ApiParam(value = "当前页码", required = true) @PathVariable long current,
                              @ApiParam(value = "页面展示数量", required = true) @PathVariable long size,
                              @ApiParam(value = "查询条件", required = false) @RequestBody CourseQuery courseQuery
    ) {
        return service.getAllCourseInfo(current, size, courseQuery);
    }

    @ApiOperation(value = "保存课程的基本信息")
    @PostMapping
    public R saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        return service.saveCourseInfo(courseInfoForm);
    }

    @ApiOperation(value = "通过id获取课程的基本信息")
    @GetMapping("/{id}")
    public R getCourseInfoById(@PathVariable String id) {
        return service.getCourseInfoById(id);
    }

    @ApiOperation(value = "通过id更新数据")
    @PutMapping
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        return service.updateCourseInfo(courseInfoForm);
    }

    @ApiOperation(value = "通过id删除课程")
    @DeleteMapping("/{id}")
    public R deleteCourseById(@PathVariable String id) {
        return service.deleteCourseById(id);
    }


    @ApiOperation(value = "通过课程id获取发布的概览信息")
    @GetMapping("/publish/{id}")
    public R getPublishInfo(@PathVariable String id) {
        return service.getPublishInfo(id);
    }

    @ApiOperation(value = "发布课程")
    @PostMapping("/publish")
    public R publishCourse(String courseId) {
        return service.publishCourse(courseId);
    }
}
