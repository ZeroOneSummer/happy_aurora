package com.aurora.happy.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true) //启动鉴权注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
    //配置URL
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            //自定义配置请求地址权限
            .antMatchers("/swagger-ui.html", "/v2/api-doc").hasAnyRole("admin")
            .anyRequest().permitAll()           //.anyRequest().authenticated() 所有请求都需要进行认证
            .and().formLogin()                  //默认登陆界面
            .and().httpBasic()
            .and().cors()                       // 允许跨域
            .and().csrf().disable();            //禁用csrf验证，防止请求403
    }
 
 
    //配置用户
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //spring内置了两种UserDetailManager实现，一种基于内存的InMemoryUserDetailsManager，另一种是基于数据库的JdbcUserDetailsManager
        auth.
            //使用内存中的InMemoryUserDetailsManager(内存用户管理器)
            inMemoryAuthentication()
            //不使用passwordEncoder密码加密
            .passwordEncoder(NoOpPasswordEncoder.getInstance())
            //在内存中给配置user用户
            .withUser("admin").password("admin").roles("admin")
            .and()
            //在内存中配置admin用户
            .withUser("user").password("user").roles("user");
 
    }
}