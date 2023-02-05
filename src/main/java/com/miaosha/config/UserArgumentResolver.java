package com.miaosha.config;

import com.miaosha.domain.MiaoshaUser;
import com.miaosha.service.MiaoshaUserService;
import com.miaosha.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    UserService userService;

    /**
     * 该方法用于判断Controller中方法参数中是否有符合条件的参数：
     * 有则进入下一个方法resolveArgument；
     * 没有则跳过不做处理
     *
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        return parameterType == MiaoshaUser.class;
    }

    /**
     * 该方法在上一个方法同通过之后调用：
     *      在这里可以进行处理，根据情况返回对象——返回的对象将被赋值到Controller的方法的参数中
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest nativeRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse nativeResponse = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = nativeRequest.getParameter(MiaoshaUserService.COOKIE_TOKEN);
        String cookieToken = getCookieToken(MiaoshaUserService.COOKIE_TOKEN, nativeRequest);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(cookieToken) ? paramToken : cookieToken;
        return miaoshaUserService.getByToken(nativeResponse, token);
    }

    private String getCookieToken(String cookieKey, HttpServletRequest nativeRequest) {

        Cookie[] cookies = nativeRequest.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (StringUtils.equals(cookies[i].getName(), cookieKey)) {
                return cookies[i].getValue();
            }
        }

        return null;
    }
}
