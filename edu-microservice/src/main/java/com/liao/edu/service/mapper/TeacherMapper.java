package com.liao.edu.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liao.edu.common.entity.Course;
import com.liao.edu.common.entity.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
public interface TeacherMapper extends BaseMapper<Teacher> {
    List<String> findTeacherIdLikeName(@Param("name") String name);
    List<Course> findCourseByTeacherId(String teacherId);
}
