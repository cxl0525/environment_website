package com.qdu.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //登录成功后，应该有用户的session
        Object loginAdmin = request.getSession().getAttribute("loginAdmin");
        if(loginAdmin==null){
            //没有登录
            request.setAttribute("message","没有权限，请先登录");
            request.getRequestDispatcher("/admin/login").forward(request,response);
            return false;
        }else {
            return  true;
        }
    }

}




























