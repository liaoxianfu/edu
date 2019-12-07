package com.liao.edu.service.controller;


import com.liao.edu.common.vo.R;
import com.liao.edu.service.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
            @RequestParam("file") MultipartFile file) throws Exception {

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
        Map<String, List<String>> map = subjectService.getAllSubject();

        return R.ok().data("items", map);

    }
}

