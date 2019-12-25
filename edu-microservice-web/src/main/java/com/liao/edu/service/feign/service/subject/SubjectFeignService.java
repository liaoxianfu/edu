package com.liao.edu.service.feign.service.subject;

import com.liao.edu.common.entity.Subject;
import com.liao.edu.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liao
 * @since 2019/12/25 9:26
 */
@FeignClient(value = "edu-microservice")
@RequestMapping("/admin/edu/subject")
public interface SubjectFeignService {
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R batchAddSub(@RequestPart("file") MultipartFile file);

    @GetMapping
    public R getAllSub();

    @DeleteMapping("/{id}")
    public R deleteSub(@PathVariable String id);

    @PostMapping
    public R addSub(@RequestBody Subject subject);

    @GetMapping("/firstLevel")
    public R getAllFirstSub();

    @GetMapping("/secondLevel/{id}")
    public R getSecondLevelSubject(@PathVariable String id);

    @GetMapping("/firstLevel/{id}")
    public R getFirstLevelSubject(@PathVariable String id);
}
