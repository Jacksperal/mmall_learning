package com.mmall.util;

import com.mmall.common.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {
    private final static String COOKIE_DOMAIN = ".lizhemmall.com";
    private final static String COOKIE_NAME = "mmall_login_token";

    //X:domain=".lizhemall.com"
    //a:A.lizhemall.com             cookie:domain=A.happymall.com;path="/"
    //b:B.lizhemall.com             cookie:domain=B.happymall.com;path="/"
    //c:A.lizhemall.com/test/cc     cookie:domain=A.happymall.com;path="/test/cc"
    //d:A.lizhemall.com/test/dd     cookie:domain=A.happymall.com;path="/test/dd"
    //e:A.lizhemall.com/test        cookie:domain=A.happymall.com;path="/test"



    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(CookieUtil.COOKIE_NAME, token);
        cookie.setDomain(CookieUtil.COOKIE_DOMAIN);
        //代表设置在根目录下
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        //设置cookie的有效期,如果是-1代表永久,单位秒
        //如果不设置,则cookie信息只写在内存中,不会写入硬盘
        cookie.setMaxAge(Const.RedisCacheExtime.REDIS_SESSSION_ETIME);

        log.info("write cookieName:{},cookieValue:{}", cookie.getName(), cookie.getValue());

        response.addCookie(cookie);

    }

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("read cookieName:{},cookieValue:{}", cookie.getName(), cookie.getValue());
                if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
                    log.info("return cookieName:{},cookieValue:{}", cookie.getName(), cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    //把MaxAge设置成0,代表删除此cookie
                    cookie.setMaxAge(0);

                    log.info("del cookieName:{},cookieValue:{}", cookie.getName(), cookie.getValue());
                    response.addCookie(cookie);
                }
            }
        }
    }
}
