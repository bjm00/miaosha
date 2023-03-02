package com.miaosha.controller;

import com.miaosha.domain.MiaoshaOrder;
import com.miaosha.domain.MiaoshaUser;
import com.miaosha.redis.RedisService;
import com.miaosha.resultcode.CodeMsg;
import com.miaosha.service.*;
import com.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    private static Logger log = LoggerFactory.getLogger(MiaoshaController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    MiaoshaGoodsService miaoshaGoodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/do_miaosha")
    public String doMiaosha(Model model, MiaoshaUser miaoshaUser
            , @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", miaoshaUser);

        if (miaoshaUser == null) {
            return "login";
        }
        //判断库存
        GoodsVo goodsVo = goodsService.getGoodsById(goodsId);
        int stockCount = goodsVo.getStockCount();

        if (stockCount <= 0) {
            model.addAttribute("errorMsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }

        //判断是否已经秒杀到了
        MiaoshaOrder goodsIdUserId = orderService.getMiaoshaOrderByGoodsIdUserId(miaoshaUser.getId(), goodsId);
        //减库存，造订单，写入订单库

        model.addAttribute("goodsList", goodsVos);
        return "goods_list";
    }

}
