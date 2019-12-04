package com.liao.edu.common.handler;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.liao.edu.common.constants.ResultCodeEnum;
import com.liao.edu.common.vo.R;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * @author liao
 * create at 2019/11/29 19:14
 * 全局统一异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyBatisSystemException.class)
    @ResponseBody
    public R error(MyBatisSystemException e) {
        e.printStackTrace();
        return R.codeEnum(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R error(HttpMessageNotReadableException e){
        e.printStackTrace();
        return R.codeEnum(ResultCodeEnum.JSON_PARSE_ERROR);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error();
    }


}
