package com.miaosha.service;

import com.miaosha.dao.MiaoshaUserDao;
import com.miaosha.dao.OrderDao;
import com.miaosha.domain.MiaoshaGoods;
import com.miaosha.domain.MiaoshaOrder;
import com.miaosha.domain.MiaoshaUser;
import com.miaosha.exception.GlobalException;
import com.miaosha.redis.MiaoshaUserKey;
import com.miaosha.redis.RedisService;
import com.miaosha.resultcode.CodeMsg;
import com.miaosha.utils.Md5Util;
import com.miaosha.utils.UUIDUtil;
import com.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class OrderService {
    public static final String COOKIE_TOKEN = "token";
    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    OrderDao orderDao;

    public MiaoshaOrder getMiaoshaOrderByGoodsIdUserId(long userId, long goodsId) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        String password = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        MiaoshaUser miaoshaUser = getById(Long.parseLong(mobile));
        if (miaoshaUser == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //验证密码
        String dbPassword = miaoshaUser.getPassword();
        String dbSalt = miaoshaUser.getSalt();
        String calcPass = Md5Util.FormPassMd5DbPass(password, dbSalt);
        if (!StringUtils.equals(calcPass, dbPassword)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成token
        String token = UUIDUtil.genRandomString();
        addCookie(response, token, miaoshaUser);
        return true;
    }

}
