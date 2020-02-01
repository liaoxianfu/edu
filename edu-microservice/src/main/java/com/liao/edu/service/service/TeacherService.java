package com.liao.edu.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liao.edu.common.entity.Teacher;
import com.liao.edu.common.entity.query.TeacherQuery;
import com.liao.edu.common.vo.R;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
public interface TeacherService extends IService<Teacher> {
    /**
     * 根据条件查询教师列表
     *
     * @param page         自定义分页查询对象
     * @param teacherQuery 查询条件封装
     * @return 同意状态码返回
     */
    R teacherQuery(Page<Teacher> page, TeacherQuery teacherQuery);

    /**
     * 获取所有的教师信息 通过马屁、返回给前端
     * @return map
     */
    Map<String,String> getAllTeacherMap();

    List<Teacher> findAllTeacherOrderByGmtModified();
}
