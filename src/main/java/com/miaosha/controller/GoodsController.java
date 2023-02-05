package com.miaosha.controller;

import com.miaosha.domain.MiaoshaUser;
import com.miaosha.domain.User;
import com.miaosha.redis.RedisService;
import com.miaosha.resultcode.Result;
import com.miaosha.service.GoodsService;
import com.miaosha.service.MiaoshaUserService;
import com.miaosha.service.UserService;
import com.miaosha.vo.GoodsVo;
import com.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_list")
    public String toList(Model model, MiaoshaUser miaoshaUser) {
        model.addAttribute("user", miaoshaUser);
        //查询商品列表
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsVos);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, MiaoshaUser miaoshaUser,
                           @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", miaoshaUser);
        //查询商品列表
        GoodsVo goodsVo = goodsService.getGoodsById(goodsId);
        //
        long startTime = goodsVo.getStartDate().getTime();
        long endTime = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 1;
        int remainTime = 0;
        //秒杀未开始
        if (now < startTime) {
            miaoshaStatus = 0;
            remainTime = (int) (startTime-now/1000);
            //秒杀已过期
        } else if (now > endTime) {
            miaoshaStatus = 2;
            remainTime=-1;
        } else {
            //秒杀进行中
            miaoshaStatus = 1;
            remainTime=0;
        }

        model.addAttribute("user", miaoshaUser);
        model.addAttribute("goods", goodsVo);
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainTime);
        return "goods_detail";
    }
}
