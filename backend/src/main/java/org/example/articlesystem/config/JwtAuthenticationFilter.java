package org.example.articlesystem.config;

import org.example.articlesystem.service.impl.JwtService;
import org.example.articlesystem.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

//处理请求token的，bearer的请求头，有没有都能校验通过

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 使用改进的Token提取方法
            String jwt = extractToken(request);

            if (StringUtils.hasText(jwt)) {
                // 从Token中解析出用户名
                String username = jwtService.extractUsername(jwt);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 加载用户信息
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    // 校验令牌
                    if (jwtService.validateToken(jwt)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        } catch (Exception e) {
            // 记录日志，但不要阻断请求，可能是不需要认证的接口
            logger.warn("JWT令牌处理失败: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 增强的Token提取方法，支持多种格式
     */
    private String extractToken(HttpServletRequest request) {
        // 1. 优先从Authorization头获取（标准格式：Bearer <token>）
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken)) {
            if (bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7); // 去除"Bearer "前缀
            } else {
                // 兼容：直接传递Token的情况（例如Swagger或某些客户端）
                return bearerToken;
            }
        }

        // 2. 兼容其他可能的Header（例如某些场景下使用"JWT"头）
        String jwtToken = request.getHeader("JWT");
        if (StringUtils.hasText(jwtToken)) {
            return jwtToken;
        }

        // 3. 还可以根据需要从Cookie或URL参数获取（一般不推荐用于生产环境）
        // String tokenFromParam = request.getParameter("access_token");
        // if (StringUtils.hasText(tokenFromParam)) {
        //     return tokenFromParam;
        // }

        return null;
    }
}