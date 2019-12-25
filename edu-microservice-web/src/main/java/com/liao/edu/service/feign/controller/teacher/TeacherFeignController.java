package com.liao.edu.service.feign.controller.teacher;

import com.liao.edu.common.entity.Teacher;
import com.liao.edu.common.entity.query.TeacherQuery;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.feign.service.teacher.TeacherFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liao
 * @since 2019/12/25 9:50
 */
@Api(value = "teacher controller")
@RestController
@RequestMapping("/v1/admin/edu/teacher")
public class TeacherFeignController {
    @Resource
    private TeacherFeignService service;

    @ApiOperation("根据分页与特定条件查询教师列表")
    @PostMapping(value = "/{current}/{size}")
    public R findUser(@ApiParam(name = "current", value = "第几页", required = true) @PathVariable int current,
                      @ApiParam(name = "size", value = "页面展示数量", required = true) @PathVariable int size,
                      @ApiParam(name = "teacher", value = "查询条件") @RequestBody TeacherQuery teacherQuery) {
        return service.findUser(current, size, teacherQuery);
    }

    @ApiOperation(value = "通过id删除教师")
    @DeleteMapping("/{id}")
    public R deleteUserById(
            @ApiParam(name = "id", value = "教师的id", required = true) @PathVariable String id
    ) {
        return service.deleteUserById(id);
    }

    @ApiOperation(value = "添加教师")
    @PostMapping
    public R addTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher) {
        return service.addTeacher(teacher);
    }


    @ApiOperation("通过教师id更新教师的信息")
    @PutMapping("/{id}")
    public R updateTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher,
            @ApiParam(name = "id", value = "讲师对象id", required = true)
            @PathVariable String id) {
        return service.updateTeacher(teacher, id);
    }

    @ApiOperation("通过教师的id获取教师详细信息")
    @GetMapping("/{id}")
    public R getTeacherById(
            @ApiParam(name = "id", value = "教师id")
            @PathVariable String id) {
        return service.getTeacherById(id);
    }

    @ApiOperation(value = "获取所有的教师信息")
    @GetMapping
    public R getAllTeacher() {
        return service.getAllTeacher();
    }

    @ApiOperation(value = "将教师的id与name封装成map返回给前端")
    @GetMapping("/map")
    public R getTeacherMap() {
        return service.getTeacherMap();
    }

}


