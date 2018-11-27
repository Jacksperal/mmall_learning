package com.mmall.controller.common.interceptor;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("start");

        //请求中Controller中的方法名
        HandlerMethod handlerMethod = (HandlerMethod) o;

        //解析HandlerMethod
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();

        //解析参数,具体的参数key以及value是什么,我们打印日志
        StringBuffer requestParamBuffer = new StringBuffer();
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> entry = iterator.next();
            String key = entry.getKey();
            String mapValue = StringUtils.EMPTY;

            String[] value = entry.getValue();

            mapValue = Arrays.toString(value);

            requestParamBuffer.append(key).append("=").append(mapValue);
        }

        User user = null;
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isNotEmpty(loginToken)) {
            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
            user = JsonUtil.string2Obj(userJsonStr, User.class);
        }

        if (user == null || (user.getRole().intValue() != Const.Role.ROLE_ADMIN)) {
            //返回false,即不会调用controller里的方法

            //这里需要添加reset,否则报异常 getWriter() has already been called for this response.
            httpServletResponse.reset();

            //这里需要设置编码,否则会乱码
            httpServletResponse.setCharacterEncoding("UTF-8");

            //设置返回值的类型
            httpServletResponse.setContentType("application/json;charset=UTF-8");


            PrintWriter writer = httpServletResponse.getWriter();

            if (user == null) {
                writer.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截,用户未的登陆")));
            }else{
                writer.write(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截,不是管理员")));
            }
            writer.flush();
            writer.close();
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
