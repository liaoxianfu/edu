package com.liao.edu.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

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
    @TableField(value = "is_delete")
    private Integer delete;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_update")
    private Date gmtUpdate;

    public static final String COL_ID = "id";

    public static final String COL_STUDENT_ID = "student_id";

    public static final String COL_STUDENT_NAME = "student_name";

    public static final String COL_STUDENT_AVATAR = "student_avatar";

    public static final String COL_STUDENT_GENDER = "student_gender";

    public static final String COL_IS_DELETE = "is_delete";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_UPDATE = "gmt_update";
}