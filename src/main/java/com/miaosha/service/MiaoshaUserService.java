package com.miaosha.service;

import com.miaosha.dao.MiaoshaUserDao;
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
public class MiaoshaUserService {
    public static final String COOKIE_TOKEN = "token";
    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(long id) {
        return miaoshaUserDao.getById(id);
    }

    public boolean login(LoginVo loginVo, HttpServletResponse response) {
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
        //生成sessionId
        String token = UUIDUtil.genRandomString();
        redisService.set(MiaoshaUserKey.token, token, miaoshaUser);
        Cookie cookie = new Cookie(COOKIE_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expirePrefix());
        cookie.setPath("/");
        response.addCookie(cookie);
        return true;
    }


    public MiaoshaUser getByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
    }

}
