package com.liao.edu.service.feign.controller.user;

import com.liao.edu.common.entity.User;
import com.liao.edu.common.vo.R;
import com.liao.edu.service.feign.service.user.UserFeignService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liao
 * @since 2019/12/25 10:04
 */
@RestController
@RequestMapping("/v1/user")
public class UserFeignController {
    @Resource
    private UserFeignService service;

    @PostMapping("/login")
    public R login(@RequestBody User user) {
        return service.login(user);
    }

    @GetMapping("/info")
    public R info(@RequestParam String token) {
        return service.info(token);
    }

    @PostMapping("/logout")
    public R logout() {
        return service.logout();
    }

}
