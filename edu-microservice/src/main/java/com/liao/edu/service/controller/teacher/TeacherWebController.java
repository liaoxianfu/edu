package com.liao.edu.service.controller.teacher;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.edu.common.entity.Course;
import com.liao.edu.common.entity.Teacher;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liao
 * @since 2019/12/30 15:09
 */
@Slf4j
@Api(value = "teacher web controller")
@RestController
@RequestMapping("/web/edu/teacher")
public class TeacherWebController {

    @Resource
    private TeacherService teacherService;

    @ApiOperation(value = "获取所有的教师信息")
    @GetMapping
    public R getAllTeacher() {
        List<Teacher> list = teacherService.list();
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据教师的id获取教师的信息")
    @GetMapping("/{id}")
    public R getTeacherById(@PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @ApiOperation(value = "通过分页获取教师数据")
    @GetMapping("/{current}/{size}}")
    public R getTeacherByPage(@PathVariable int current, @PathVariable int size) {
        Page<Teacher> teacherPage = new Page<>(current, size);
        return teacherService.teacherQuery(teacherPage, null);
    }

    @ApiOperation(value = "通过教师的id查询教授的课程")
    @GetMapping("course/{id}")
    public R getCourseListByTeacherId(@PathVariable String id) {
        List<Course> courseList = teacherService.findCourseListByTeacherList(id);
        return R.ok().data("courseList", courseList);
    }


}
