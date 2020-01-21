package com.liao.edu.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liao.edu.common.entity.Course;
import com.liao.edu.common.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
public interface CourseMapper extends BaseMapper<Course> {
    Course selectCourseById(String id);
    CoursePublishVo selectCoursePublishVoByCourseId(String courseId);
}
