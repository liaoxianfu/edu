package com.liao.edu.common.constants;

import lombok.Getter;

/**
 * @author liao
 * create at 2019/11/29 16:31
 */
@Getter
public enum ResultCodeEnum {
    // 成功
    SUCCESS(true, 20000, "成功"),
    BAD_SQL_GRAMMAR(false, 21001, "sql语法错误"),
    JSON_PARSE_ERROR(false, 21002, "json解析异常"),
    PARAMS_PARSE_ERROR(false, 21003, "参数解析错误"),
    SAVE_ERROR(false, 21004, "保存到数据库失败"),
    UPLOAD_ERROR(false, 21005, "上传文件出错,请重新上传"),
    DELETE_FILE_ERROR(false, 21006, "删除文件出错"),
    DOWNLOAD_FILE_ERROR(false, 21007, "下载文件出错"),
    SIGNATURE_ERROR(false, 21008, "获取签名失败"),
    UNKNOWN_REASON(false, 20001, "未知错误");

    private Boolean success;

    private Integer code;

    private String message;

    private ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

}
