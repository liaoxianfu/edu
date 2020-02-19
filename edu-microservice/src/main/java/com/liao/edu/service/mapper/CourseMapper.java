package com.liao.edu.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liao.edu.common.entity.Course;
import com.liao.edu.common.entity.Subject;
import com.liao.edu.common.entity.vo.CoursePublishVo;
import com.liao.edu.common.entity.vo.CourseWebVo;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 通过课程的id查询课程
     *
     * @param id id
     * @return course
     */
    Course selectCourseById(String id);

    /**
     * 通过课程id 查询课程的信息
     *
     * @param courseId 课程id
     * @return CoursePublishVo
     */
    CoursePublishVo selectCoursePublishVoByCourseId(String courseId);

    /**
     * 根据父id查询所属的子id列表 一级学科列表parentId = 0
     *
     * @param parentId 父id
     * @return list
     */
    List<Subject> selectSubject(String parentId);

    /**
     * 查询以及学科下的所有的课程
     */
    List<Course> selectCourseListByFirstLevelSubject(String subjectId);

    CourseWebVo selectWebVoByCourseId(String id);
}
