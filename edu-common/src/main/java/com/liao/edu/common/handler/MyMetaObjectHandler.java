package com.liao.edu.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author liao
 * create at 2019/11/28 21:42
 */

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.setInsertFieldValByName("is_deleted", 0, metaObject);
        this.setInsertFieldValByName("gmtCreate", new Date(), metaObject);
        this.setInsertFieldValByName("gmtModified", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setUpdateFieldValByName("gmtModified", new Date(), metaObject);
    }
}
