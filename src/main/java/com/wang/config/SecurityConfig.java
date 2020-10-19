package com.wang.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.wang.handler.AjaxAccessDeniedHandler;
import com.wang.handler.AjaxAuthenticationEntryPoint;
import com.wang.handler.AjaxAuthenticationFailureHandler;
import com.wang.handler.AjaxAuthenticationSuccessHandler;
import com.wang.handler.AjaxLogoutSuccessHandler;
import com.wang.handler.SelfAuthenticationProvider;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    AjaxAuthenticationEntryPoint authenticationEntryPoint;  //  未登陆时返回 JSON 格式的数据给前端（否则为 html）

    @Autowired
    AjaxAuthenticationSuccessHandler authenticationSuccessHandler;  // 登录成功返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    AjaxAuthenticationFailureHandler authenticationFailureHandler;  //  登录失败返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    AjaxLogoutSuccessHandler  logoutSuccessHandler;  // 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）

    @Autowired
    AjaxAccessDeniedHandler accessDeniedHandler;    // 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）

    @Autowired
    SelfAuthenticationProvider provider; // 自定义安全认证
	
	@Bean(name="testbean")
	public Map<String,Object> testbean(){
		Map<String, Object>testbean=new HashMap<String, Object>();
		testbean.put("name", "wanghe");
		testbean.put("age", "25岁");
		return testbean;
	}
	
	 @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/**
		 * 
		 *    使用基于内存的用户存储		 
		  auth.inMemoryAuthentication().withUser("lxw").password("lxw").roles("USER").and()
		         .withUser("admin").password("admin").roles("USER","ADMIN");
		 *
		 */
		 
		/***
		 * 基于JDBC的用户权限配置

	        auth.jdbcAuthentication().dataSource(dataSource).
	                usersByUsernameQuery("select username,password,true from tb_user where username = ?").
	                authoritiesByUsernameQuery("select username,'ROLE_USER' from tb_user where username = ?");
	
		 
		 */		 
		 
		    // 加入自定义的安全认证
	        auth.authenticationProvider(provider);
	    }

	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {

	        http.csrf().disable()

	                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)

	                .and()
	                .authorizeRequests()// 对请求授权
	                
	                .antMatchers("/wang/getTestbean").permitAll() //对于访问 /wang/getTestbean 链接，不需要登录也可以访问
	                .antMatchers( "/static/**").hasRole("ADMIN" ) //需要角色配合 （也需要用户认证） （token的权限会自动加上 ROLE_前缀）
	                .antMatchers( "/db/**").access("hasRole('ADMIN') and hasRole('DBA')") //需要多个角色认证
	                .anyRequest().authenticated()// 其他 url 需要身份认证，这些没有匹配上的其他的url请求，只需要用户被验证。

	                .and()
	                .formLogin()  //开启登录
	                
	                .loginPage("/login") //自定义登录界面
	                .successHandler(authenticationSuccessHandler) //登录成功   
	                .failureHandler(authenticationFailureHandler) // 登录失败
	                .permitAll()
	                
	                .and()
	                .logout().addLogoutHandler(new LogoutHandler(){
	                	public void logout(HttpServletRequest request, HttpServletResponse response,
	                			Authentication authentication) {
	                		System.err.println("********");
	                		request.getSession().getAttribute("role");
	                		request.getSession().invalidate();
	                		System.err.println("****退出成功****");
	                	}
	                })
	              
	                .logoutSuccessHandler(logoutSuccessHandler)
	                .permitAll();

	        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler); // 无权访问 JSON 格式的数据
	    }
	
}
