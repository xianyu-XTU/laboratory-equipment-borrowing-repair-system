package com.xtu.labequipment.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xtu.labequipment.annotation.RequireRole;
import com.xtu.labequipment.common.AuthContext;
import com.xtu.labequipment.common.LoginUser;
import com.xtu.labequipment.common.Result;
import com.xtu.labequipment.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final TokenUtil tokenUtil;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        LoginUser loginUser = tokenUtil.parseToken(token);
        if (loginUser == null) {
            writeJson(response, 401, Result.fail(401, "未登录或登录已过期"));
            return false;
        }
        AuthContext.set(loginUser);

        if (handler instanceof HandlerMethod handlerMethod) {
            RequireRole requireRole = handlerMethod.getMethodAnnotation(RequireRole.class);
            if (requireRole == null) {
                requireRole = handlerMethod.getBeanType().getAnnotation(RequireRole.class);
            }
            if (requireRole != null) {
                boolean match = Arrays.asList(requireRole.value()).contains(loginUser.getRoleCode());
                if (!match) {
                    writeJson(response, 403, Result.fail(403, "没有权限访问该接口"));
                    return false;
                }
            }
        }
        return true;
    }

    private void writeJson(HttpServletResponse response, int status, Result<?> result) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        AuthContext.clear();
    }
}
