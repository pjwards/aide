package com.pjwards.aide.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home", "/index", "/user/sign_up/").permitAll()
                .antMatchers("/forgot_password/**").permitAll()
                .antMatchers("/conferences/add").hasAuthority("USER")
                .antMatchers("/conferences/**", "/programs/**", "/sessions/**", "/api/**").permitAll()
                .antMatchers("/settings/users").hasAuthority("ADMIN")
                .antMatchers("/public/**", "/resources/**", "/resources/public/**", "/assets/**").permitAll()
                .antMatchers("/css/**", "/lib/**", "/img/**", "/bower_components/**", "/font/**", "/data/**", "/mail/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
            .formLogin()
                .loginPage("/sign_in")
                .failureUrl("/sign_in?error")
                .usernameParameter("email")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/sign_out")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
            .rememberMe();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}