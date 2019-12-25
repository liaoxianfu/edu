//package com.liao.edu.service.feign.service.hystrix;
//
//import com.liao.edu.common.entity.Chapter;
//import com.liao.edu.common.vo.R;
//import com.liao.edu.service.feign.service.ChapterFeignService;
//
///**
// * @author liao
// * @since 2019/12/25 9:21
// */
//public class ChapterFeignHystrix implements ChapterFeignService {
//    R errInfo = R.error().data("info","资源不可访问");
//    @Override
//    public R getChapterVo(String courseId) {
//        return errInfo;
//    }
//
//    @Override
//    public R addChapter(Chapter chapter) {
//        return errInfo;
//    }
//
//    @Override
//    public R updateChapterById(Chapter chapter) {
//        return errInfo;
//    }
//
//    @Override
//    public R findChapterById(String id) {
//        return errInfo;
//    }
//
//    @Override
//    public R deleteChapterById(String id) {
//        return errInfo;
//    }
//}
