package com.liao.edu.common.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liao
 * @since 2020/2/22 20:38
 */
@Data
public class VideoWebVo implements Serializable {
    private String videoId;

    private String teacherId;
    private String videoTitle;
    private String videoSourceId;
    private String teacherAvatar;
    private String teacherName;
    private String teacherIntroduce;
    /**

     */
}
