package com.example.securitybasic.config;

import com.example.securitybasic.security.CustomAccessDeniedHandler;
import com.example.securitybasic.security.CustomAuthenticationFilter;
import com.example.securitybasic.security.CustomAuthenticationProvider;
import com.example.securitybasic.security.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final CustomAuthenticationProvider customAuthenticationProvider;

    // 정적 컨텐츠가 있다면 작성합니다.
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        /* ex
        web.ignoring().antMatchers("/~~"); 경로를 적어줍니다.
         */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login","/signup","/","/notAccess").permitAll()
                .antMatchers("/user","/character").hasRole("USER")
                .antMatchers("/guest","/success").authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/notAccess"))
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .logout()
                .invalidateHttpSession(true)
                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setAuthenticationSuccessHandler(new CustomLoginSuccessHandler());
        return customAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }
}
