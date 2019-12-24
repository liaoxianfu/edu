package com.liao.edu.video.feign.service.hystrix;

import com.liao.edu.common.vo.R;
import com.liao.edu.video.feign.service.EduResourceFeignService;
import feign.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liao
 * @since 2019/12/24 14:33
 */
@Component
public class EduResourceHystrixImpl implements EduResourceFeignService {
    public R errorInfo() {
        return R.error().data("info", "数据不可用");
    }


    @Override
    public R uploadFile(String courseId, MultipartFile file) {
        return errorInfo();
    }

    @Override
    public R deleteFile(String courseId) {
        return errorInfo();
    }

    @Override
    public R getVideoNameById(String id) {
        return errorInfo();
    }

    @Override
    public Response getVideoFileByName(String id) {
        return null;
    }
}
