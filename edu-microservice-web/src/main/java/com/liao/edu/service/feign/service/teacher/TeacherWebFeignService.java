package com.liao.edu.service.feign.service.teacher;

import com.liao.edu.common.vo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author liao
 * @since 2020/1/21 12:25
 */

@FeignClient(value = "edu-microservice", path = "/web/edu/teacher")
public interface TeacherWebFeignService {
    /**
     * 获取所有的教师列表
     *
     * @return R
     */
    @GetMapping
    R getAllTeacher();

    /**
     * 通过教师id获取教师信息
     *
     * @param id teacher id
     * @return R
     */
    @GetMapping("/{id}")
    R getTeacherById(@PathVariable String id);
    @GetMapping("course/{id}")
    public R getCourseListByTeacherId(@PathVariable String id);

}
