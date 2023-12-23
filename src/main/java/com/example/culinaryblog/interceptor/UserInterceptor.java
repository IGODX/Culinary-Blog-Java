package com.example.culinaryblog.interceptor;

import com.example.culinaryblog.config.SecurityUtil;
import com.example.culinaryblog.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = SecurityUtil.getUserFromSession();
        if (user != null) {
            request.setAttribute("username", user.getUsername());
            request.setAttribute("userId", user.getId());
            request.setAttribute("role", user.getRole().name());
        }
        return true;
    }
}
