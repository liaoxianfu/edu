package com.liao.edu.common.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liao
 * @since 2020/2/25 13:45
 */
@Data
public class StudentQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("根据学生姓名，模糊查询使用")
    private String studentName;

    @ApiModelProperty("根据学号进行查询")
    private String studentId;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;
}
