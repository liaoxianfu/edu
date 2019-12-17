package com.liao.edu.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liao.edu.common.constants.ResultCodeEnum;
import com.liao.edu.common.exception.EduException;
import com.liao.edu.service.entity.Course;
import com.liao.edu.service.entity.CourseDescription;
import com.liao.edu.service.entity.Teacher;
import com.liao.edu.service.entity.form.CourseInfoForm;
import com.liao.edu.service.entity.query.CourseQuery;
import com.liao.edu.service.mapper.CourseDescriptionMapper;
import com.liao.edu.service.mapper.CourseMapper;
import com.liao.edu.service.mapper.TeacherMapper;
import com.liao.edu.service.service.CourseDescriptionService;
import com.liao.edu.service.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.edu.service.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {


    @Resource
    private CourseDescriptionService courseDescriptionService;


    /**
     * 保存课程基本信息
     *
     * @param courseInfoForm 课程基本信息表单
     * @return 课程的id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        Course course = new Course();
        // 默认为未发布的课程
        course.setStatus(Course.COURSE_DRAFT);
        // 属性拷贝
        BeanUtils.copyProperties(courseInfoForm, course);
        // 保存数据到数据库
        boolean save = this.save(course);
        // 数据保存失败
        if (!save) {
            throw new EduException(ResultCodeEnum.SAVE_ERROR);
        }
        // 获取保存后的数据id
        String id = course.getId();
        // edu_course_description表中的id与course中的id保持一致
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(id);
        courseDescription.setDescription(courseInfoForm.getDescription());

        boolean b = courseDescriptionService.save(courseDescription);
        // 保存失败
        if (!b) {
            throw new EduException(ResultCodeEnum.SAVE_ERROR);
        } else {
            return id;
        }
    }

    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        // 通过id获取课程的信息
        Course course = this.getById(id);
        // 通过id获取课程描述的信息
        CourseDescription description = courseDescriptionService.getById(id);
        // 属性拷贝
        BeanUtils.copyProperties(course, courseInfoForm);
        // 设置描述
        courseInfoForm.setDescription(description.getDescription());
        return courseInfoForm;
    }

    @Transactional
    @Override
    public String updateCourseInfo(CourseInfoForm courseInfoForm) {
        // 更新数据
        // 更新course表数据
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        // 更新描述信息
        CourseDescription description = new CourseDescription();
        BeanUtils.copyProperties(courseInfoForm, description);
        boolean b = this.updateById(course);
        if (!b) {
            throw new EduException(ResultCodeEnum.PARAMS_PARSE_ERROR);
        }
        boolean updateById = courseDescriptionService.updateById(description);
        if (!updateById) {
            throw new EduException(ResultCodeEnum.PARAMS_PARSE_ERROR);
        }
        return courseInfoForm.getId();
    }

    @Override
    public Map<String, Object> courseQuery(IPage<Course> page, CourseQuery courseQuery) {
        QueryWrapper<Course> queryWrapper = null;
        if (courseQuery != null) {
            queryWrapper = new QueryWrapper<>();
            String courseName = courseQuery.getCourseName();
            String startTime = courseQuery.getStartTime();
            String endTime = courseQuery.getEndTime();

            if (!StringUtils.isEmpty(courseName)) {
                queryWrapper.like("title", courseName);
            }

            if (!StringUtils.isEmpty(startTime)) {
                queryWrapper.ge("gmt_create", startTime);
            }

            if (!StringUtils.isEmpty(endTime)) {
                queryWrapper.le("gmt_create", endTime);
            }

        }
        IPage<Map<String, Object>> pageMaps = this.pageMaps(page, queryWrapper);
        // 获取数据总数
        long total = pageMaps.getTotal();
        // 获取页面数量
        long pages = pageMaps.getPages();
        // 获取数据
        List<Map<String, Object>> records = pageMaps.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("total", total);
        return map;
    }
}
