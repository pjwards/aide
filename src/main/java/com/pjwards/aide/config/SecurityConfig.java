package com.pjwards.aide.config;

import com.pjwards.aide.config.handler.CustomLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
                .antMatchers("/conferences/add").hasAuthority("ADMIN")
                .antMatchers("/conferences/**/admin/**").hasAuthority("USER")
                .antMatchers("/conferences/**/admin/**").hasAuthority("ADMIN")
                .antMatchers("/upload/**").hasAuthority("USER")
                .antMatchers("/upload/**").hasAuthority("ADMIN")
                .antMatchers("/conferences/**", "/programs/**", "/sessions/**", "/api/**", "/messages/**").permitAll()
                .antMatchers("/settings/users").hasAuthority("ADMIN")
                .antMatchers("/public/**", "/resources/**", "/resources/public/**", "/assets/**").permitAll()
                .antMatchers("/css/**", "/lib/**", "/img/**", "/bower_components/**", "/font/**", "/data/**", "/mail/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
            .formLogin()
                .loginPage("/sign_in")
                .failureUrl("/sign_in?error")
                .usernameParameter("email")
                .successHandler(successHandler())
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

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler("/");
    }
}