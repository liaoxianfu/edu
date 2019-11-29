package com.liao.edu.service.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liao
 * create at 2019/11/29 17:15
 */
@Data
@ToString
public class TeacherQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("根据教师姓名，模糊查询使用")
    private String teacherName;

    @ApiModelProperty("根据教师等级查询")
    private Integer level;

    @ApiModelProperty("开始时间")
    private String  startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

}
