package com.example.securitybasic.security;

import com.example.securitybasic.domain.User;
import com.example.securitybasic.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String email = (String) token.getName();
        String password = (String) token.getCredentials();

        User user = (User) userService.loadUserByUsername(email);

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new BadCredentialsException("비밀 번호가 틀렸습니다.");
        }

        return new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),user.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
