package com.tzadok.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tzadok
 * @date 2022/8/10 10:19:09
 * @project SSM-SpringMVC
 * @description:
 * 拦截器的三个方法：
 * preHandle():在控制器方法执行之前，其返回值表示对控制器方法的拦截（false）或放行（true）
 * postHandle():在控制器方法执行之后
 * afterCompletion():在控制器方法执行之后，且渲染视图完毕之后
 *
 * 多个拦截器的执行顺序
 */
@Component
public class SecondInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("SecondInterceptor-->preHandle");
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("SecondInterceptor-->postHandle");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("SecondInterceptor-->afterCompletion");
    }
}
