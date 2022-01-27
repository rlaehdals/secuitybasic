package com.example.securitybasic.config;

import com.example.securitybasic.interceptor.CharacterInterceptor;
import com.example.securitybasic.repository.CharacterRepository;
import com.example.securitybasic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CharacterRepository characterRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CharacterInterceptor(characterRepository))
                .addPathPatterns("/character")
                .excludePathPatterns("/signup","/login","/notAccess","/user","/guest","/");
    }
}
