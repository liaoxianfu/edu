package com.liao.edu.common.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author liao
 * @since 2020/2/19 15:55
 */
@ApiModel(value = "课程信息", description = "网站课程详情页需要的相关字段")
@Data
public class CourseWebVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String courseTitle;
    private double coursePrice;
    private String courseCover;
    private int lessonNum;
    private String buyCount;
    private String viewCount;
    private String description;
    private String firstSubjectTitle;
    private String secondSubjectTitle;
    private String teacherName;
    private String teacherAvatar;
    private String teacherIntroduce;
    private String teacherCareer;
    private String firstSubjectId;
    private String secondSubjectId;
    private String teacherId;

}
