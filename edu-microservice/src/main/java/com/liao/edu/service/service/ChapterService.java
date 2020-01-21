package com.liao.edu.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liao.edu.common.entity.Chapter;
import com.liao.edu.common.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
public interface ChapterService extends IService<Chapter> {
    List<ChapterVo> findChapterVoByCourseId(String courseId);

}
