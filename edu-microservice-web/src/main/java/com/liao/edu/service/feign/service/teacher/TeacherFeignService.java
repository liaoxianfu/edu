package com.liao.edu.service.feign.service.teacher;

import com.liao.edu.common.entity.Teacher;
import com.liao.edu.common.entity.query.TeacherQuery;
import com.liao.edu.common.vo.R;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author liao
 * @since 2019/12/25 9:26
 */

@FeignClient(value = "edu-microservice")
@RequestMapping("/admin/edu/teacher")
public interface TeacherFeignService {
    @PostMapping("/{current}/{size}")
    public R findUser(@PathVariable int current, @PathVariable int size,@RequestBody TeacherQuery teacherQuery);

    @DeleteMapping("/{id}")
    public R deleteUserById(@PathVariable String id);

    @PostMapping
    public R addTeacher(@RequestBody Teacher teacher);

    @PutMapping("/{id}")
    public R updateTeacher(@RequestBody Teacher teacher, @PathVariable String id);

    @GetMapping("/{id}")
    public R getTeacherById(@PathVariable String id);

    @GetMapping
    public R getAllTeacher();

    @GetMapping("/map")
    public R getTeacherMap();


}
