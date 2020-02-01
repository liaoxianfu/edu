package com.liao.edu.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.edu.common.entity.Teacher;
import com.liao.edu.common.entity.query.TeacherQuery;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.mapper.TeacherMapper;
import com.liao.edu.service.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
@Slf4j
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {


    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public R teacherQuery(Page<Teacher> page, TeacherQuery teacherQuery) {
        // 打印日志
        log.debug("获取的查询信息为{}", teacherQuery);
        QueryWrapper<Teacher> queryWrapper = null;
        if (teacherQuery != null) {
            queryWrapper = new QueryWrapper<>();
            String teacherName = teacherQuery.getTeacherName();
            String level = teacherQuery.getLevel();
            String startTime = teacherQuery.getStartTime();
            String endTime = teacherQuery.getEndTime();

            queryWrapper.orderByDesc("gmt_modified");
            if (!StringUtils.isEmpty(teacherName)) {
                queryWrapper.like("name", teacherName);
            }

            if (!StringUtils.isEmpty(level)) {
                queryWrapper.eq("level", level);
            }

            if (!StringUtils.isEmpty(startTime)) {
                queryWrapper.ge("gmt_create", startTime);
            }

            if (!StringUtils.isEmpty(endTime)) {
                queryWrapper.le("gmt_create", endTime);
            }
        }

        IPage<Map<String, Object>> mapsPage = teacherMapper.selectMapsPage(page, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", mapsPage.getRecords());
        map.put("total", mapsPage.getTotal());
        return R.ok().data(map);
    }

    @Override
    public Map<String, String> getAllTeacherMap() {
        List<Teacher> list = this.list();
        Map<String, String> teacherMap = new HashMap<>();
        list.forEach(teacher -> {
            teacherMap.put(teacher.getId(), teacher.getName());
        });
        return teacherMap;
    }

    @Override
    public List<Teacher> findAllTeacherOrderByGmtModified() {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<Teacher>().orderByDesc("gmt_modified");
        return teacherMapper.selectList(queryWrapper);
    }
}
