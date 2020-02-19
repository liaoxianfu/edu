package com.liao.edu.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liao.edu.common.entity.Course;
import com.liao.edu.common.entity.Subject;
import com.liao.edu.common.entity.form.CourseInfoForm;
import com.liao.edu.common.entity.query.CourseQuery;
import com.liao.edu.common.entity.vo.CoursePublishVo;
import com.liao.edu.common.entity.query.CoursePage;
import com.liao.edu.common.entity.query.CourseWebQuery;
import com.liao.edu.common.entity.vo.CourseWebVo;

import java.util.List;
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

    /**
     * 根据父id查询所属的子id列表 一级学科列表parentId = 0
     *
     * @param parentId 父id
     * @return list
     */
    List<Subject> selectSubject(String parentId);

    /**
     * 通过等级判断选择的是 全部 一级还是二级学科的课程
     * @param level 等级 1，2，3
     * @param id id
     * @return
     */
    List<Course> getCourseList(Integer level,String id);

    CoursePage getCourseByCourseWebQuery(CourseWebQuery query);

    CourseWebVo getCourseWebVoInfoByCourseId(String courseId);
}
