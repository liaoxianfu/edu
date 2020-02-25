package com.liao.edu.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.edu.common.entity.Chapter;
import com.liao.edu.common.entity.Video;
import com.liao.edu.common.entity.vo.ChapterVo;
import com.liao.edu.common.entity.vo.VideoVo;
import com.liao.edu.service.mapper.ChapterMapper;
import com.liao.edu.service.mapper.VideoMapper;
import com.liao.edu.service.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
@Service
@Slf4j
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Resource
    private VideoMapper videoMapper;


    @Override
    public List<ChapterVo> findChapterVoByCourseId(String courseId) {
        // 获取符合条件的chapter列表
        List<Chapter> chapterList = this.list(new QueryWrapper<Chapter>().eq("course_id", courseId).orderByAsc("sort"));
        // 创建chapterVo列表
        List<ChapterVo> chapterVos = new ArrayList<>();
        chapterList.forEach(chapter -> {
            ChapterVo chapterVo = new ChapterVo();
            // 设置属性
            String chapterId = chapter.getId();
            chapterVo.setId(chapterId);
            chapterVo.setTitle(chapter.getTitle());
            List<Video> videos = videoMapper.selectList(new QueryWrapper<Video>().eq("chapter_id",chapterId).orderByAsc("sort"));
            // 创建VideoVoList
            List<VideoVo> videoVoList = new ArrayList<>();
            // 属性拷贝到VideoVo中 并交VideoVo添加到列表
            videos.forEach(video -> {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(video, videoVo);
                videoVoList.add(videoVo);
            });
            // 将videoVoList添加到ChapterVod中
            chapterVo.setChildren(videoVoList);
            // 将chapterVo添加到ChapterVos中
            chapterVos.add(chapterVo);
        });
        // 返回数据
        return chapterVos;
    }
}
