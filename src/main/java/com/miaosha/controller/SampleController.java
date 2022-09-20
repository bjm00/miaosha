package com.miaosha.controller;

import com.miaosha.domain.User;
import com.miaosha.redis.KeyPrefix;
import com.miaosha.redis.RedisService;
import com.miaosha.redis.UserKey;
import com.miaosha.resultcode.Result;
import com.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "hello world");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> getById() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGetByKey() {
        User user = redisService.get(UserKey.getById, "1", User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User(1, "晨拥");
        boolean res = redisService.set(UserKey.getById, "1", user);
        return Result.success(res);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> tx() {
        userService.tx();
        return Result.success(true);
    }
}
