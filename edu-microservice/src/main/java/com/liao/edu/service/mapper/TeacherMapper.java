package com.liao.edu.service.mapper;

import com.liao.edu.service.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
    List<String> findTeacherIdLikeName(String name);
}
