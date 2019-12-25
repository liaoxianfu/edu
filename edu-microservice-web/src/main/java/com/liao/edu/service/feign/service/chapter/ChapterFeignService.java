package com.liao.edu.service.feign.service.chapter;

import com.liao.edu.common.entity.Chapter;
import com.liao.edu.common.vo.R;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author liao
 * @since 2019/12/24 15:52
 */
@FeignClient(value = "edu-microservice")
@RequestMapping("/admin/edu/chapter")
public interface ChapterFeignService {
    @GetMapping("/chapterVoList/{courseId}")
    R getChapterVo(@ApiParam(value = "课程id", required = true) @PathVariable String courseId);

    @PostMapping
    R addChapter(@ApiParam(value = "章节信息", required = true) @RequestBody Chapter chapter);

    @PutMapping
    R updateChapterById(@ApiParam(value = "章节信息", required = true) @RequestBody Chapter chapter);

    @GetMapping("/{id}")
    R findChapterById(@PathVariable String id);

    @DeleteMapping("/{id}")
    R deleteChapterById(@PathVariable String id);

}
