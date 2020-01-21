package com.liao.edu.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.edu.common.entity.Video;
import com.liao.edu.service.mapper.VideoMapper;
import com.liao.edu.service.service.VideoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

}
