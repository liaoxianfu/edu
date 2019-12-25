package com.liao.edu.service.feign.service.user;

import com.liao.edu.common.entity.User;
import com.liao.edu.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author liao
 * @since 2019/12/25 9:27
 */

@FeignClient(value = "edu-microservice")
@RequestMapping("/user")
public interface UserFeignService {
    @PostMapping("/login")
    public R login(@RequestBody User user);

    @GetMapping("/info")
    public R info(@RequestParam String token);

    @PostMapping("/logout")
    public R logout();


}
