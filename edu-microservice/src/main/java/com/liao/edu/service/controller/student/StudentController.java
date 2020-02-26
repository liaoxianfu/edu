package com.liao.edu.service.controller.student;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.edu.common.entity.Student;
import com.liao.edu.common.entity.query.StudentQuery;
import com.liao.edu.common.entity.vo.StudentVo;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.service.StudentService;
import com.liao.edu.service.utils.encryption.MD5Encry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author liao
 * @since 2020/2/25 12:30
 */

@Api(description = "学生相关的操作")
@RestController
@Slf4j
@RequestMapping("/admin/edu/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @ApiOperation(value = "获取分页的数据")
    @PostMapping("{current}/{size}")
    public R getStudentListByPage(
            @ApiParam(name = "current", value = "当前的页数", required = true) @PathVariable Integer current,
            @ApiParam(name = "size", value = "当前的页面条数", required = true) @PathVariable Integer size,
            @ApiParam(name = "studentQuery", value = "页面查询的条件") @RequestBody StudentQuery studentQuery
    ) {
        log.info("查询信息为{}", studentQuery);
        Page<Student> page = new Page<>(current, size);
        StudentVo studentVo = studentService.getStudentListByPageQuery(page, current, size, studentQuery);
        return R.ok().data("studentList", studentVo.getStudentList()).data("total", studentVo.getTotal());
    }

    @ApiOperation(value = "添加学生")
    @PostMapping
    public R addStudent(@RequestBody Student student) {
        String password = student.getPassword();
        if (password != null && password.length() > 0) {
            student.setPassword(MD5Encry.encodeMd5(password));
        }
        boolean save = studentService.save(student);
        if (save) {
            return R.ok().data("status", "success");
        } else {
            return R.error().data("status", "error");
        }
    }


    @ApiOperation(value = "通过id删除学生")
    @DeleteMapping("{id}")
    public R deleteStudentById(@PathVariable String id) {
        boolean b = studentService.removeById(id);

        if (b) {
            return R.ok().data("status", "success");

        } else {
            return R.error().data("status", "error");
        }
    }

    @ApiOperation(value = "通过id更新学生信息")
    @PutMapping
    public R updateStudentById(@RequestBody Student student) {
        // 加密
        String password = student.getPassword();
        if (password != null && password.length() > 0) {
            student.setPassword(MD5Encry.encodeMd5(password));
        }
        boolean b = studentService.updateById(student);
        if (b) {
            return R.ok().data("status", "success");

        } else {
            return R.error().data("status", "error");
        }
    }

    @ApiOperation(value = "通过id获取学生的信息信息")
    @GetMapping("/{id}")
    public R getStudentInfoById(@PathVariable String id) {
        Student student = studentService.getById(id);
        student.setPassword(null);
        return R.ok().data("student", student);
    }

    @ApiOperation(value = "通过Excel文件批量添加")
    @PostMapping("file")
    public R batchAddStudent(
            @ApiParam(name = "file", value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file) {
        log.info("文件{}", file.getOriginalFilename());
        InputStream inputStream = null;
        List<String> list = null;
        try {
            inputStream = file.getInputStream();
            list = studentService.batchAddStudent(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list.size() == 0) {
            return R.ok().message("批量导入成功");
        } else {
            return R.error().message("部分数据导入失败").data("messageList", list);
        }
    }
}
