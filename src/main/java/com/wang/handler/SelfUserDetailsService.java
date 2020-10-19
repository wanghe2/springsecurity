package com.wang.handler;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *  ② 根据 username 获取数据库 user 信息
 */
@Component
public class SelfUserDetailsService implements UserDetailsService {
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //构建用户信息的逻辑(取数据库/LDAP等用户信息)
        SelfUserDetails userInfo = new SelfUserDetails();
        userInfo.setUsername(username); // 任意用户名登录
        String encodePassword ="123456"; // 模拟从数据库中获取的密码原为 123456
        userInfo.setPassword(encodePassword);

        Set authoritiesSet = new HashSet();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN"); // 模拟从数据库中获取用户角色

        authoritiesSet.add(authority);
        userInfo.setAuthorities(authoritiesSet);

        return userInfo;
    }
}
