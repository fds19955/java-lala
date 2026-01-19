//package org.example.articlesystem.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true) // 替换 @EnableGlobalMethodSecurity
//public class SecurityConfig备份 { // 移除了 extends WebSecurityConfigurerAdapter
//
//    @Autowired  // 拦截器设置
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    /**
//     * 配置安全过滤链
//     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authz -> authz // 使用 authorizeHttpRequests 替代 authorizeRequests
//                        .requestMatchers("/api/login").permitAll() // 使用 requestMatchers 替代 antMatchers
//                        .requestMatchers("/api/articles/public/**").permitAll()
////                        .requestMatchers("/api/users","/api/articles").permitAll() // 新增：放行 /api/users  /api/articles 接口
//                        .anyRequest().authenticated()
////                        .anyRequest().permitAll()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        // 添加 JWT 过滤器
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        // 核心配置：放行所有请求
////        http.authorizeHttpRequests(authz -> authz
////                        .anyRequest().permitAll() // 将之前的 .authenticated() 改为 .permitAll()
////                )
////                .csrf(csrf -> csrf.disable()) // 通常需要禁用CSRF
////                .sessionManagement(session -> session
////                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                );
////
////        // 如果您确定所有接口都不需要JWT验证，可以考虑移除这个过滤器
////        // http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
////
////        return http.build();
////    }
//
//    /**
//     * 密码编码器
//     */
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
//    /**
//     * 认证管理器
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}