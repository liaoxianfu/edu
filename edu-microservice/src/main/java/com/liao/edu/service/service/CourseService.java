package com.liao.edu.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liao.edu.service.entity.Course;
import com.liao.edu.service.entity.form.CourseInfoForm;
import com.liao.edu.service.entity.query.CourseQuery;
import com.liao.edu.service.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
public interface CourseService extends IService<Course> {

    /**
     * 保存课程的基本信息
     *
     * @param courseInfoForm 课程基本信息对象
     * @return 保存后数据库返回的id
     */
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    /**
     * 通过课程基本信息的id返回课程的信息
     *
     * @param id 课程id
     * @return 课程基本信息对象
     */
    CourseInfoForm getCourseInfoById(String id);

    /**
     * 更新课程基本信息
     *
     * @param courseInfoForm 课程基本信息
     * @return 课程id
     */
    String updateCourseInfo(CourseInfoForm courseInfoForm);

    /**
     * 根据条件查询课程列表信息
     * @param page 分页信息
     * @param courseQuery 查询条件
     * @return map
     */
    Map<String,Object> courseQuery(IPage<Course> page, CourseQuery courseQuery);

    /**
     * 根据课程id查询发布的课程信息
     * @param courseId 课程id
     * @return CoursePublishVo
     */
    CoursePublishVo getCoursePublishInfoByCourseId(String courseId);
}
