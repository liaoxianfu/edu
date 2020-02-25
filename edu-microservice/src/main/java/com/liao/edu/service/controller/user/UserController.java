package com.liao.edu.service.controller.user;

import com.liao.edu.common.entity.User;
import com.liao.edu.common.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liao
 * create at 2019/12/4 13:19
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        log.info(user.getUsername());
        log.info(user.getPassword());
        return R.ok().data("token", "admin-token");
    }

    @GetMapping("/info")
    public R info(String token) {
        log.info("token={}", token);

        return R.ok().data("roles", "[\"admin\"]")
                .data("introduction", "I am a super administrator")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .data("name", "Super Admin");
    }

    @PostMapping("/logout")
    public R logout() {
        log.info("logout demo");

        return R.ok().data("data", "success");
    }
}
