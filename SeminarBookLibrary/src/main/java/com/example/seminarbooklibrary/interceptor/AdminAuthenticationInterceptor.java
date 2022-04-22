package com.example.seminarbooklibrary.interceptor;

import com.example.seminarbooklibrary.Domain.UserDomain;
import com.example.seminarbooklibrary.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AdminAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private HttpSession httpSession;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserDomain user= (UserDomain) httpSession.getAttribute("user");
        if (user!=null){
            if (user.getLoginNameUser().equals("admin")){
                return true;
            }
            else
            {
                httpSession.setAttribute("redirect-uri",request.getRequestURI());
                response.sendRedirect("/login");
                return false;
            }
        }
        httpSession.setAttribute("redirect-uri",request.getRequestURI());
        response.sendRedirect("/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
