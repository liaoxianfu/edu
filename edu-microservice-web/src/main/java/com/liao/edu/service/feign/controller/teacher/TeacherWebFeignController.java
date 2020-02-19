package com.liao.edu.service.feign.controller.teacher;

import com.liao.edu.common.vo.R;
import com.liao.edu.service.feign.service.teacher.TeacherWebFeignService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liao
 * @since 2020/1/21 12:24
 */
@Api(value = "teacher web controller")
@RestController
@RequestMapping("/v1/web/edu/teacher")
public class TeacherWebFeignController {

    @Resource
    private TeacherWebFeignService teacherWebFeignService;

    @GetMapping
    public R getAllTeacher() {
        return teacherWebFeignService.getAllTeacher();
    }

    @GetMapping("/{id}")
    public R getTeacherById(@PathVariable String id) {
        return teacherWebFeignService.getTeacherById(id);
    }

    @GetMapping("course/{id}")
    public R getCourseListByTeacherId(@PathVariable String id){
        return teacherWebFeignService.getCourseListByTeacherId(id);
    }
}
