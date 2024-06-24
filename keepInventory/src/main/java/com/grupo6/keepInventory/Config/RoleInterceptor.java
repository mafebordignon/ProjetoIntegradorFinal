package com.grupo6.keepInventory.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class RoleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = (String) request.getSession().getAttribute("username");
        String userRole = (String) request.getSession().getAttribute("role");

        if (username == null || userRole == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/admin") && !(userRole.equals("admin") || userRole.equals("ADMIN") )) {
            response.sendRedirect(request.getContextPath() + "/access-denied");
            return false;
        }

    return true;
}

}
