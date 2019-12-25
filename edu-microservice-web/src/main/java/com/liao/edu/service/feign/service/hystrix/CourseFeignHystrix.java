//package com.liao.edu.service.feign.service.hystrix;
//
//import com.liao.edu.common.entity.form.CourseInfoForm;
//import com.liao.edu.common.entity.query.CourseQuery;
//import com.liao.edu.common.vo.R;
//import com.liao.edu.service.feign.service.CourseFeignService;
//
///**
// * @author liao
// * @since 2019/12/25 9:24
// */
//public class CourseFeignHystrix implements CourseFeignService {
//    R errInfo = R.error().data("info","资源不可访问");
//    @Override
//    public R getAllCourseInfo(long current, long size, CourseQuery courseQuery) {
//        return errInfo;
//    }
//
//    @Override
//    public R saveCourseInfo(CourseInfoForm courseInfoForm) {
//        return errInfo;
//    }
//
//    @Override
//    public R getCourseInfoById(String id) {
//        return errInfo;
//    }
//
//    @Override
//    public R updateCourseInfo(CourseInfoForm courseInfoForm) {
//        return errInfo;
//    }
//
//    @Override
//    public R deleteCourseById(String id) {
//        return errInfo;
//    }
//
//    @Override
//    public R getPublishInfo(String id) {
//        return errInfo;
//    }
//
//    @Override
//    public R publishCourse(String courseId) {
//        return errInfo;
//    }
//}
