package com.liao.edu.common.constants;

import lombok.Getter;

/**
 * @author liao
 * create at 2019/11/29 16:31
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(true, 20000, "成功"),
    BAD_SQL_GRAMMAR(false, 21001, "sql语法错误"),
    JSON_PARSE_ERROR(false, 21002, "json解析异常"),
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
