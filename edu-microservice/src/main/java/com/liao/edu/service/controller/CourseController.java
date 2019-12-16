package com.liao.edu.service.controller;


import com.liao.edu.common.vo.R;
import com.liao.edu.service.entity.form.CourseInfoForm;
import com.liao.edu.service.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
@Api(description = "课程相关的操作")
@RestController
@RequestMapping("/admin/edu/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @ApiOperation(value = "保存课程的基本信息")
    @PostMapping
    public R saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String s = courseService.saveCourseInfo(courseInfoForm);
        if (s != null) {
            return R.ok().data("courseId", s);
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "通过id获取课程的基本信息")
    @GetMapping("/{id}")
    public R getCourseInfoById(@PathVariable String id) {
        CourseInfoForm courseInfoForm = courseService.getCourseInfoById(id);
        return R.ok().data("data", courseInfoForm);
    }

    @ApiOperation(value = "通过id更新数据")
    @PutMapping
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        String id = courseService.updateCourseInfo(courseInfoForm);
        if (StringUtils.isEmpty(id)){
            return R.error();
        }
        return R.ok().data("courseId",id);
    }

}

