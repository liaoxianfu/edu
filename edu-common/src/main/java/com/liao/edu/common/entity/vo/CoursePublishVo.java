package com.liao.edu.common.entity.vo;

import lombok.Data;

/**
 * @author liao
 * @since 2019/12/19 16:31
 */

@Data
public class CoursePublishVo {
    private static final long serialVersionUID=1L;
    private String cover;
    private String title;
    private String lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;
    private int lessonPublishCount;
}
