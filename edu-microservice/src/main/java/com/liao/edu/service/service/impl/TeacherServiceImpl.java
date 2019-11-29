package com.liao.edu.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.entity.Teacher;
import com.liao.edu.service.entity.query.TeacherQuery;
import com.liao.edu.service.mapper.TeacherMapper;
import com.liao.edu.service.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    private static Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public R teacherQuery(Page<Teacher> page, TeacherQuery teacherQuery) {
        // 打印日志

        logger.debug("获取到的teacherQuery为{}", teacherQuery);
        QueryWrapper<Teacher> queryWrapper = null;
        if (teacherQuery != null) {
            queryWrapper = new QueryWrapper<>();
            String teacherName = teacherQuery.getTeacherName();
            Integer level = teacherQuery.getLevel();
            String startTime = teacherQuery.getStartTime();
            String endTime = teacherQuery.getEndTime();
            if (!StringUtils.isEmpty(teacherName)) {
                queryWrapper.like("name", teacherName);
            }


            if (level != null) {
                queryWrapper.eq("level", level);
            }

            if (startTime != null) {
                queryWrapper.ge("gmt_create", startTime);
            }

            if (endTime != null) {
                queryWrapper.le("gmt_create", endTime);
            }
        }

        IPage<Map<String, Object>> mapsPage = teacherMapper.selectMapsPage(page, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", mapsPage.getRecords());
        map.put("total", mapsPage.getTotal());
        return R.ok().data(map);
    }
}