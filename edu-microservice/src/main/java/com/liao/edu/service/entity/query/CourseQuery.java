package com.liao.edu.service.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author liao
 * @since 2019/12/16 18:16
 */
@Data
@ToString
public class CourseQuery {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("根据课程名称模糊查询")
    private String courseName;


    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;
}
