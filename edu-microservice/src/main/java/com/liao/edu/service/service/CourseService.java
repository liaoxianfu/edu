package com.liao.edu.service.service;

import com.liao.edu.service.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liao.edu.service.entity.form.CourseInfoForm;

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
}
