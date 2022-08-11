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
 * 多个拦截器的执行顺序和Springmvc的配置文件中配置的顺序有关
 * preHandle()按照配置的属性执行，afterCompletion()和postHandle()按照配置的反序执行
 *
 * 若拦截器中有某个拦截器的preHandle()返回了false
 * 拦截器的preHandle()返回了false和它之前的拦截器的preHandle()都会执行
 * 所有的拦截器的postHandle()都不执行
 * 拦截器的preHandle()返回false之前的afterCompletion()会执行
 *
 */
@Component
public class FirstInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("FirstInterceptor-->preHandle");
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("FirstInterceptor-->postHandle");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("FirstInterceptor-->afterCompletion");
    }
}
