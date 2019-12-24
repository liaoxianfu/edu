package com.liao.edu.service.feign.service;

import com.liao.edu.common.entity.form.CourseInfoForm;
import com.liao.edu.common.entity.query.CourseQuery;
import com.liao.edu.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author liao
 * @since 2019/12/24 16:04
 */
@FeignClient(value = "edu-microservice")
@RequestMapping("/admin/edu/course")
public interface CourseFeignService {

    @PostMapping("/{current}/{size}")
    R getAllCourseInfo(@PathVariable long current, @PathVariable long size, @RequestBody CourseQuery courseQuery);

    @PostMapping
    R saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm);

    @GetMapping("/{id}")
    R getCourseInfoById(@PathVariable String id);

    @PutMapping
    R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm);

    @DeleteMapping("/{id}")
    R deleteCourseById(@PathVariable String id);

    @GetMapping("/publish/{id}")
    R getPublishInfo(@PathVariable String id);

    @PostMapping("/publish")
    R publishCourse(String courseId);
}
