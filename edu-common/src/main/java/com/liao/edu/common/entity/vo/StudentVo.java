package com.liao.edu.common.entity.vo;

import com.liao.edu.common.entity.Student;
import lombok.Data;

import java.util.List;

/**
 * @author liao
 * @since 2020/2/25 16:20
 */
@Data
public class StudentVo {
    List<Student> studentList;
    Long total;
}
