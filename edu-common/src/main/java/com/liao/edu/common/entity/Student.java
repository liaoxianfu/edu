package com.liao.edu.common.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author liao
 * @since 2020/2/25 12:18
 */
@Data
@TableName(value = "edu_student")
public class Student {
    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 学号
     */
    @TableField(value = "student_id")
    private String studentId;

    /**
     * 姓名
     */
    @TableField(value = "student_name")
    private String studentName;
    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 头像
     */
    @TableField(value = "student_avatar")
    private String studentAvatar;

    /**
     * 性别
     * 默认为2 也就是不确定 0 男 1 女
     */
    @TableField(value = "student_gender")
    private Integer studentGender;

    /**
     * 逻辑删除
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer logicDelete;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


    public static final String COL_ID = "id";

    public static final String COL_STUDENT_ID = "student_id";

    public static final String COL_STUDENT_NAME = "student_name";

    public static final String COL_STUDENT_AVATAR = "student_avatar";

    public static final String COL_STUDENT_GENDER = "student_gender";

    public static final String COL_IS_DELETED = "is_deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";

    public static final String COL_PASSWORD = "password";
}