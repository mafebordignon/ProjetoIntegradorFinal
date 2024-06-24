package com.grupo6.keepInventory.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = (String) request.getSession().getAttribute("username");
        Boolean isDefaultPassword = (Boolean) request.getSession().getAttribute("isDefaultPassword");
        String requestURI = request.getRequestURI();

        if (username == null || isDefaultPassword == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        if (requestURI.equals("/atualizar-senha")) {
            if (!isDefaultPassword) {
                response.sendRedirect(request.getContextPath() + "/access-denied");
                return false;
            }
        } else {
            if (isDefaultPassword && !requestURI.equals("/api/senha")){
                response.sendRedirect(request.getContextPath() + "/atualizar-senha");
                return false;
            }
        }

        return true;
    }
}
