package com.miaosha.controller;

import com.miaosha.domain.MiaoshaUser;
import com.miaosha.domain.User;
import com.miaosha.redis.RedisService;
import com.miaosha.resultcode.Result;
import com.miaosha.service.MiaoshaUserService;
import com.miaosha.service.UserService;
import com.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_list")
    public String toList(Model model, @CookieValue(value = MiaoshaUserService.COOKIE_TOKEN, required = false) String cookieToken,
                         @RequestParam(value = MiaoshaUserService.COOKIE_TOKEN, required = false) String paramToken) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return "login";
        }
        String token = StringUtils.isEmpty(cookieToken) ? paramToken : cookieToken;
        MiaoshaUser miaoshaUser = miaoshaUserService.getByToken(token);
        model.addAttribute("user", miaoshaUser);
        return "goods_list";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(@Valid LoginVo loginVo, HttpServletResponse response) {
        log.info(loginVo.toString());
//        //参数校验
//        String password = loginVo.getPassword();
//        String mobile = loginVo.getMobile();
//        if (StringUtils.isEmpty(password)) {
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }
//        if (StringUtils.isEmpty(mobile)) {
//            return Result.error(CodeMsg.MOBILE_EMPTY);
//        } else if (!ValidatorUtil.isMobile(mobile)) {
//            return Result.error(CodeMsg.MOBILE_ERROR);
//        }
        //登录
        miaoshaUserService.login(loginVo, response);

        return Result.success(true);

    }

}
