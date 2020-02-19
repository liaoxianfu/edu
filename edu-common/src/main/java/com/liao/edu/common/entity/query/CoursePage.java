package com.liao.edu.common.entity.query;

import com.liao.edu.common.entity.Course;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author liao
 * @since 2020/2/14 17:08
 */
@Data
public class CoursePage {
    List<Course> courseList;
    private Integer total;
    private Integer page;

}
