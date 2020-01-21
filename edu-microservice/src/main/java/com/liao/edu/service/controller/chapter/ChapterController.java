package com.liao.edu.service.controller.chapter;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liao.edu.common.entity.Chapter;
import com.liao.edu.common.entity.Video;
import com.liao.edu.common.entity.vo.ChapterVo;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.service.ChapterService;
import com.liao.edu.service.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author liao
 * @since 2019-11-28
 */
@Api(description = "章节信息添加")
@RestController
@RequestMapping("/admin/edu/chapter")
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    @Resource
    private VideoService videoService;

    @ApiOperation(value = "通过课程的ID获取章节信息")
    @GetMapping("/chapterVoList/{courseId}")
    public R getChapterVo(@ApiParam(value = "课程id", required = true) @PathVariable String courseId) {
        List<ChapterVo> chapterVoList = chapterService.findChapterVoByCourseId(courseId);
        return R.ok().data("items", chapterVoList);
    }

    @ApiOperation(value = "添加章节信息")
    @PostMapping
    public R addChapter(@ApiParam(value = "章节信息", required = true) @RequestBody Chapter chapter) {
        // 首先判断是否存在课程的id 如果不存在就不能进行保存
        if (!StringUtils.isEmpty(chapter.getCourseId())) {
            boolean save = chapterService.save(chapter);
            if (save) {
                return R.ok();
            }
        }
        return R.error();
    }

    @ApiOperation(value = "根据章节的id更新章节的信息")
    @PutMapping
    public R updateChapterById(@ApiParam(value = "章节信息", required = true) @RequestBody Chapter chapter) {
        // 如果不存在章节的id就返回失败信息
        String id = chapter.getId();
        if (StringUtils.isEmpty(id)) {
            return R.error();
        }
        // 更新信息
        boolean b = chapterService.updateById(chapter);
        if (b) {
            return R.ok();
        }
        return R.error();
    }

    // 查询章节信息

    @ApiOperation(value = "根据章节的id查询章节的信息")
    @GetMapping("/{id}")
    public R findChapterById(@PathVariable String id) {
        Chapter chapter = chapterService.getById(id);
        return R.ok().data("data", chapter);
    }


    // 删除章节信息

    @ApiOperation(value = "根据章节的id删除章节的信息")
    @DeleteMapping("/{id}")
    public R deleteChapterById(@PathVariable String id) {
        // 删除章节就需要先删除章节下的小结数据信息
        boolean flag = true;
        // 先判断是否有数据
        List<Video> videoList = videoService.list(new QueryWrapper<Video>().eq("chapter_id", id));
        // 如果存在就删除小结信息
        if (videoList.size() > 0) {
            boolean b = videoService.remove(new QueryWrapper<Video>().eq("chapter_id", id));
            // 如果删除失败，就不能继续删除数据
            if (!b) {
                flag = false;
            }
        }
        // 删除成功之后再删除章节的信息
        if (flag) {
            boolean remove = chapterService.removeById(id);
            if (remove) {
                return R.ok();
            }
        }
        return R.error();
    }
}

