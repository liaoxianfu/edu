package com.liao.edu.oss.feign.service.hystrix;

import com.liao.edu.common.vo.R;
import com.liao.edu.oss.feign.service.OssFeignService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liao
 * @since 2019/12/24 15:42
 */

public class OssFeignHystrixImpl implements OssFeignService {
    @Override
    public R upload(MultipartFile file, String fileHost) {
        return R.error();
    }
}
