package com.liao.edu.service.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.entity.Teacher;
import com.liao.edu.service.entity.query.TeacherQuery;
import com.liao.edu.service.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
@Slf4j
@Api(value = "teacher controller")
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ApiOperation("根据分页与特定条件查询教师列表")
    @GetMapping("/{current}/{size}")
    public R findUser(@ApiParam(name = "current", value = "第几页", required = true) @PathVariable int current,
                      @ApiParam(name = "size", value = "页面展示数量", required = true) @PathVariable int size,
                      @ApiParam(name = "teacher", value = "查询条件") TeacherQuery teacherQuery
    ) {
        log.debug("获取的teacherQuery为{}", teacherQuery);
        Page<Teacher> teacherPage = new Page<>(current, size);
        return teacherService.teacherQuery(teacherPage, teacherQuery);
    }


    @ApiOperation(value = "通过id删除教师")
    @DeleteMapping("/{id}")
    public R deleteUserById(
            @ApiParam(name = "id", value = "教师的id", required = true) @PathVariable String id
    ) {
        boolean b = teacherService.removeById(id);
        if (b) {
            return R.ok();
        }
        return R.error().message("删除失败");
    }

    @ApiOperation(value = "添加教师")
    @PostMapping
    public R addTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        if (save) {
            return R.ok().message("添加教师成功").data("data", teacher);
        } else {
            return R.error().message("添加教师失败");
        }
    }

    @ApiOperation("通过教师id更新教师的信息")
    @PutMapping("/{id}")
    public R updateTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher,
            @ApiParam(name = "id", value = "讲师对象id", required = true)
            @PathVariable String id) {
        teacher.setId(id);
        boolean b = teacherService.updateById(teacher);
        if (b) {
            teacher = teacherService.getById(id);
            return R.ok().data("data", teacher);
        } else {
            return R.error();
        }
    }

    @ApiOperation("通过教师的id获取教师详细信息")
    @GetMapping("/{id}")
    public R getTeacherById(
            @ApiParam(name = "id", value = "教师id")
            @PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("data", teacher);
    }

    @ApiOperation(value = "获取所有的教师信息")
    @GetMapping
    public R getAllTeacher() {
        List<Teacher> list = teacherService.list();
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "将教师的id与name封装成map返回给前端")
    @GetMapping("/map")
    public R getTeacherMap() {
        Map<String, String> map = teacherService.getAllTeacherMap();
        return R.ok().data("data", map);
    }
}

