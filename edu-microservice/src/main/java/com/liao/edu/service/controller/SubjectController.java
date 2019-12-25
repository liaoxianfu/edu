package com.liao.edu.service.controller;


import com.liao.edu.common.vo.R;
import com.liao.edu.common.vo.SubjectVo;
import com.liao.edu.service.entity.Subject;
import com.liao.edu.service.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
@Api("课程分类管理")
@RestController
@RequestMapping("/admin/edu/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @ApiOperation(value = "Excel批量导入")
    @PostMapping("/import")
    public R batchAddSub(
            @ApiParam(name = "file", value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file) {

        List<String> msg = subjectService.batchImportByExcelFile(file);
        if (msg.size() == 0) {
            return R.ok().message("批量导入成功");
        } else {
            return R.error().message("部分数据导入失败").data("messageList", msg);
        }
    }

    @ApiOperation(value = "获取数据内容")
    @GetMapping
    public R getAllSub() {
        List<SubjectVo> map = subjectService.getAllSubject();
        return R.ok().data("items", map);
    }


    @ApiOperation(value = "通过id删除数据")
    @DeleteMapping("/{id}")
    public R deleteSub(@PathVariable String id) {
        boolean b = subjectService.deleteSubjectById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "添加标题一或者标题二的数据")
    @PostMapping
    public R addSub(@RequestBody Subject subject) {
        boolean b = subjectService.addSubject(subject);
        if (b) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation(value = "获取所有的一级标题")
    @GetMapping("/firstLevel")
    public R getAllFirstSub() {
        List<Subject> parentSubject = subjectService.findAllParentSubject();
        return R.ok().data("items", parentSubject);
    }

    @ApiOperation(value = "通过一级标题id获取二级标题")
    @GetMapping("/secondLevel/{id}")
    public R getSecondLevelSubject(@PathVariable String id) {
        List<Subject> subjects = subjectService.findSecondSubjectById(id);
        return R.ok().data("items", subjects);
    }

    @ApiOperation(value = "通过二级标题的id获取一级标题的信息")
    @GetMapping("/firstLevel/{id}")
    public R getFirstLevelSubject(@PathVariable String id) {
        Subject firstLevelSubject = subjectService.findFirstSubjectBySecondSubjectId(id);
        return R.ok().data("data", firstLevelSubject);
    }


}

