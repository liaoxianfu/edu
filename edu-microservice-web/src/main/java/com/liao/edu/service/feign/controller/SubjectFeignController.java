package com.liao.edu.service.feign.controller;

import com.liao.edu.common.entity.Subject;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.feign.service.SubjectFeignService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author liao
 * @since 2019/12/25 9:43
 */
@RestController
@RequestMapping("/v1/admin/edu/subject")
public class SubjectFeignController {

    @Resource
    private SubjectFeignService service;

    @ApiOperation(value = "Excel批量导入")
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R batchAddSub(
            @ApiParam(name = "file", value = "Excel文件", required = true)
            @RequestPart("file") MultipartFile file) {
        return service.batchAddSub(file);
    }

    @ApiOperation(value = "获取数据内容")
    @GetMapping
    public R getAllSub() {
        return service.getAllSub();
    }

    @ApiOperation(value = "通过id删除数据")
    @DeleteMapping("/{id}")
    public R deleteSub(@PathVariable String id) {
        return service.deleteSub(id);
    }

    @ApiOperation(value = "添加标题一或者标题二的数据")
    @PostMapping
    public R addSub(@RequestBody Subject subject) {
        return service.addSub(subject);
    }

    @ApiOperation(value = "获取所有的一级标题")
    @GetMapping("/firstLevel")
    public R getAllFirstSub() {
        return service.getAllFirstSub();
    }

    @ApiOperation(value = "通过一级标题id获取二级标题")
    @GetMapping("/secondLevel/{id}")
    public R getSecondLevelSubject(@PathVariable String id) {
        return service.getSecondLevelSubject(id);
    }

    @ApiOperation(value = "通过二级标题的id获取一级标题的信息")
    @GetMapping("/firstLevel/{id}")
    public R getFirstLevelSubject(@PathVariable String id) {
        return service.getFirstLevelSubject(id);
    }

}
