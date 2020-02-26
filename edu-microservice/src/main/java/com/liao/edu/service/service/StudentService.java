package com.liao.edu.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liao.edu.common.entity.Student;
import com.liao.edu.common.entity.query.StudentQuery;
import com.liao.edu.common.entity.vo.StudentVo;

import java.io.InputStream;
import java.util.List;

/**
 * @author liao
 * @since 2020/2/25 12:26
 */
public interface StudentService extends IService<Student> {
    public StudentVo getStudentListByPageQuery(Page<Student> page, Integer current, Integer size, StudentQuery query);

    List<String> batchAddStudent(InputStream inputStream);

}
