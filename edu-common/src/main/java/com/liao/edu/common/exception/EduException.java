package com.liao.edu.common.exception;

import com.liao.edu.common.constants.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liao
 * @since 2019/12/13 13:53
 */
@Data
@ApiModel(value = "全局异常")
public class EduException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 接受状态码和消息
     *
     * @param code 状态码
     * @param message 信息
     */
    public EduException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型
     *
     * @param resultCodeEnum 枚举类型
     */
    public EduException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}
