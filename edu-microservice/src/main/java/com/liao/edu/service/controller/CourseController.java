package com.liao.edu.service.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.entity.Course;
import com.liao.edu.service.entity.form.CourseInfoForm;
import com.liao.edu.service.entity.query.CourseQuery;
import com.liao.edu.service.entity.vo.CoursePublishVo;
import com.liao.edu.service.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
@Slf4j
@RequestMapping("/admin/edu/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @ApiOperation(value = "通过条件查询获取分页数据")
    @PostMapping("/{current}/{size}")
    public R getAllCourseInfo(@ApiParam(value = "当前页码", required = true) @PathVariable long current,
                              @ApiParam(value = "页面展示数量", required = true) @PathVariable long size,
                              @ApiParam(value = "查询条件", required = false) @RequestBody CourseQuery courseQuery
    ) {
        IPage<Course> coursePage = new Page<Course>(current, size);
        Map<String, Object> map = courseService.courseQuery(coursePage, courseQuery);
        return R.ok().data("data", map);
    }


    @ApiOperation(value = "保存课程的基本信息")
    @PostMapping
    public R saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        log.debug("获取到的description==={}", courseInfoForm.getDescription());
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
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String id = courseService.updateCourseInfo(courseInfoForm);
        if (StringUtils.isEmpty(id)) {
            return R.error();
        }
        return R.ok().data("courseId", id);
    }

    @ApiOperation(value = "通过id删除课程")
    @DeleteMapping("/{id}")
    public R deleteCourseById(@PathVariable String id) {
        boolean b = courseService.removeById(id);
        if (b) {
            return R.ok();
        }
        return R.error();
    }


    @ApiOperation(value = "通过课程id获取发布的概览信息")
    @GetMapping("/publish/{id}")
    public R getPublishInfo(@PathVariable String id) {
        CoursePublishVo info = courseService.getCoursePublishInfoByCourseId(id);
        return R.ok().data("data", info);
    }

    @ApiOperation(value = "发布课程")
    @PostMapping("/publish")
    public R publishCourse(String courseId) {
        Course course = new Course();
        course.setId(courseId);
        course.setStatus(Course.COURSE_NORMAL);
        boolean b = courseService.updateById(course);
        if (b) {
            return R.ok();
        }
        return R.error();
    }

}

