package com.miaosha.service;

import com.miaosha.dao.GoodsDao;
import com.miaosha.dao.MiaoshaUserDao;
import com.miaosha.domain.Goods;
import com.miaosha.domain.MiaoshaUser;
import com.miaosha.domain.OrderInfo;
import com.miaosha.exception.GlobalException;
import com.miaosha.redis.MiaoshaUserKey;
import com.miaosha.redis.RedisService;
import com.miaosha.resultcode.CodeMsg;
import com.miaosha.utils.Md5Util;
import com.miaosha.utils.UUIDUtil;
import com.miaosha.vo.GoodsVo;
import com.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减少库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);

        OrderInfo orderInfo = orderService.createOrder(user,goods);

        Goods g = new Goods();
        g.setId(goods.getId());
        g.setGoodsStock(goods.getGoodsStock());


        return null;
    }
}
