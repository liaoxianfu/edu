package com.liao.edu.service.mapper;

import com.liao.edu.service.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
public interface SubjectMapper extends BaseMapper<Subject> {
    /**
     * 通过二级标题id查询其所属的一级标题内容
     *
     * @param id 二级标题id
     * @return subject对象
     */
    Subject findFirstSubjectBySecondSubjectId(String id);

}
