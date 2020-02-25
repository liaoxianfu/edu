package com.liao.edu.service.feign.service.course;

import com.liao.edu.common.entity.query.CourseWebQuery;
import com.liao.edu.common.vo.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author liao
 * @since 2020/2/14 10:57
 */
@FeignClient(value = "edu-microservice")
@RequestMapping("/web/edu/course")
public interface CourseWebFeignService {
    @GetMapping("/sub/{parentId}")
    R getSubjectList(@PathVariable String parentId);

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    R getCourseList(
            @RequestParam(value = "level", required = true) Integer level, @RequestParam(value = "id", required = false) String id);

    @PostMapping("/list/page")
    R getCourseList(@RequestBody CourseWebQuery courseWebQuery);


    @GetMapping("/list/{courseId}")
    R getCourseDetailInfo(@PathVariable String courseId);

    @GetMapping("/chapterVoList/{courseId}")
    public R getChapterVoList(@PathVariable String courseId);

    @GetMapping("/video/list/{videoId}")
    public R getChapterVoListByVideoId(@PathVariable String videoId);

    @GetMapping("/video/resource/{videoId}")
    public R getVideoResource(@PathVariable String videoId);
}
