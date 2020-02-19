package com.liao.edu.common.entity.query;

import lombok.Data;

/**
 * @author liao
 * @since 2020/2/14 17:01
 */
@Data
public class CourseWebQuery {
    private Integer level;
    private String subjectId;
    private Integer current;
    private Integer pageSize;
}
