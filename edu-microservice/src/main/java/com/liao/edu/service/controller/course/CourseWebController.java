package com.liao.edu.service.controller.course;

import com.liao.edu.common.entity.Course;
import com.liao.edu.common.entity.Subject;
import com.liao.edu.common.entity.query.CoursePage;
import com.liao.edu.common.entity.query.CourseWebQuery;
import com.liao.edu.common.entity.vo.CourseWebVo;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liao
 * @since 2020/2/14 10:28
 */
@Api(description = "课程对外相关的操作")
@RestController
@Slf4j
@RequestMapping("/web/edu/course")
public class CourseWebController {
    @Resource
    private CourseService courseService;

    @GetMapping("/sub/{parentId}")
    public R getSubjectList(@PathVariable String parentId) {
        List<Subject> subjectList = courseService.selectSubject(parentId);
        return R.ok().data("subjectList", subjectList);
    }

    @ApiOperation(value = "通过不等的等价查询对应的课程")
    @GetMapping("/list")
    public R getCourseList(
            @ApiParam(value = "等级 分为：1 全部课程 2一级subject下所有课程 3 二级subject课程",
                    required = true)
            @RequestParam(value = "level", required = true) Integer level,
            @ApiParam(value = "subjectId")
            @RequestParam(value = "id", required = false) String id) {
        log.info("获取的数据为{}", level);
        List<Course> courseList = courseService.getCourseList(level, id);
        return R.ok().data("courseList", courseList);
    }

    @ApiOperation(value = "获取分页课程数据")
    @PostMapping("/list/page")
    public R getCourseList(@RequestBody CourseWebQuery courseWebQuery) {
        CoursePage coursePage = courseService.getCourseByCourseWebQuery(courseWebQuery);
        return R.ok().data("coursePage", coursePage);
    }

    @ApiOperation(value = "获取课程页面详情数据")
    @GetMapping("/list/{courseId}")
    public R getCourseDetailInfo(@PathVariable String courseId) {
        CourseWebVo webVo = courseService.getCourseWebVoInfoByCourseId(courseId);
        return R.ok().data("courseInfo", webVo);
    }


}
