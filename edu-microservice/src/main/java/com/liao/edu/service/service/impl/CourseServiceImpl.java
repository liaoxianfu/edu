package com.liao.edu.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.edu.common.constants.ResultCodeEnum;
import com.liao.edu.common.entity.Course;
import com.liao.edu.common.entity.CourseDescription;
import com.liao.edu.common.entity.Subject;
import com.liao.edu.common.entity.form.CourseInfoForm;
import com.liao.edu.common.entity.query.CourseQuery;
import com.liao.edu.common.entity.vo.ChapterVo;
import com.liao.edu.common.entity.vo.CoursePublishVo;
import com.liao.edu.common.entity.vo.CourseWebVo;
import com.liao.edu.common.entity.vo.VideoWebVo;
import com.liao.edu.common.exception.EduException;
import com.liao.edu.common.entity.query.CoursePage;
import com.liao.edu.common.entity.query.CourseWebQuery;
import com.liao.edu.service.mapper.CourseMapper;
import com.liao.edu.service.mapper.SubjectMapper;
import com.liao.edu.service.mapper.TeacherMapper;
import com.liao.edu.service.service.ChapterService;
import com.liao.edu.service.service.CourseDescriptionService;
import com.liao.edu.service.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private ChapterService chapterService;


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
        // 判断课程描述是否存在，不存在设置为空字符串
        if (description == null) {
            courseInfoForm.setDescription("");
        } else {
            courseInfoForm.setDescription(description.getDescription());
        }
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
            // 尝试新建一条数据
            boolean save = courseDescriptionService.save(description);
            if (!save) {
                throw new EduException(ResultCodeEnum.PARAMS_PARSE_ERROR);
            }
        }
        return courseInfoForm.getId();
    }

    @Override
    public Map<String, Object> courseQuery(IPage<Course> page, CourseQuery courseQuery) {
        QueryWrapper<Course> queryWrapper = null;
        // 存放返回的信息
        Map<String, Object> map = new HashMap<>(2);
        if (courseQuery != null) {
            queryWrapper = new QueryWrapper<>();
            String courseName = courseQuery.getCourseName();
            String teacherName = courseQuery.getTeacherName();
            String startTime = courseQuery.getStartTime();
            String endTime = courseQuery.getEndTime();

            if (!StringUtils.isEmpty(courseName)) {
                queryWrapper.like("title", courseName);
            }

            if (!StringUtils.isEmpty(teacherName)) {
                // 查询出所有符合条件的id
                List<String> teacherIdList = teacherMapper.findTeacherIdLikeName(teacherName);
                // 判断查询数据不存在的情况
                if (teacherIdList.size() <= 0) {
                    map.put("items", null);
                    map.put("total", 0);
                    return map;
                }
                queryWrapper.in("teacher_id", teacherIdList);
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

        map.put("items", records);
        map.put("total", total);
        return map;
    }

    @Override
    public CoursePublishVo getCoursePublishInfoByCourseId(String courseId) {
        return courseMapper.selectCoursePublishVoByCourseId(courseId);
    }

    @Override
    public List<Subject> selectSubject(String parentId) {
        return courseMapper.selectSubject(parentId);
    }

    @Override
    public List<Course> getCourseList(Integer level, String id) {
        // level为3的时候是二级菜单的情况
        if (level == 3) {
            QueryWrapper<Course> wrapper = new QueryWrapper<Course>().eq("subject_id", id);
            return courseMapper.selectList(wrapper);
        } else if (level == 2) {

            // 一级菜单的情况
            return courseMapper.selectCourseListByFirstLevelSubject(id);
        } else {
            // 默认是全部的情况
            return this.list();
        }
    }


    @Override
    public CoursePage getCourseByCourseWebQuery(CourseWebQuery query) {
        Integer level = query.getLevel();
        String subjectId = query.getSubjectId();
        List<Course> courses;
        if (level == 3) {
            QueryWrapper<Course> wrapper = new QueryWrapper<Course>().eq("subject_id", subjectId);
            courses = courseMapper.selectList(wrapper);
        } else if (level == 2) {
            courses = courseMapper.selectCourseListByFirstLevelSubject(subjectId);
        } else {
            courses = this.list();
        }
        return setCoursePage(query, courses);
    }

    @Override
    public CourseWebVo getCourseWebVoInfoByCourseId(String courseId) {
        return courseMapper.selectWebVoByCourseId(courseId);
    }

    @Override
    public List<ChapterVo> getChapterVoListByVideoId(String videoId) {
        // 获取视频id所在的课程id
        String courseId = courseMapper.selectCourseIdByVideoId(videoId);
        return chapterService.findChapterVoByCourseId(courseId);
    }

    @Override
    public VideoWebVo selectVideoResourceByVideoId(String videoId) {
        return courseMapper.selectVideoResourceByVideoId(videoId);
    }

    private CoursePage setCoursePage(CourseWebQuery query, List<Course> courses) {
        CoursePage coursePage = new CoursePage();
        Integer pageSize = query.getPageSize();
        Integer current = query.getCurrent();
        int total = courses.size();
        // 设置查询的总记录数
        coursePage.setTotal(total);
        // 设置查询分页的数量
        coursePage.setPage(getPage(total, pageSize));
        // 判断是否存在数据 如果没有数据就不能进行list剪切
        if (total != 0) {
            int endSize = Math.min(pageSize * current, total);
            courses = courses.subList((current - 1) * pageSize, endSize);
        }
        coursePage.setCourseList(courses);
        return coursePage;
    }

    private int getPage(int total, int pageSize) {
        int page = total / pageSize;
        if (total % pageSize != 0) {
            page += 1;
        }
        return page;
    }
}
