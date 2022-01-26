package com.example.securitybasic.service;

import com.example.securitybasic.domain.Character;
import com.example.securitybasic.domain.User;
import com.example.securitybasic.exception.UserNotFound;
import com.example.securitybasic.repository.CharacterRepository;
import com.example.securitybasic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CharacterRepository characterRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    throw new UserNotFound("없는 유저입니다.");
                }
        );
    }

    public User signup(String email, String password){
        userRepository.findByEmail(email).ifPresent(
                a-> {
                    throw new IllegalStateException("이미 가입된 이메일입니다.");
                }
        );
        User user = (User)User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        return userRepository.save(user);
    }

    public Character createCharacter(String name, String email){
        User user = (User) loadUserByUsername(email);
        Character character = Character.createCharacter(name, user);
        return characterRepository.save(character);
    }

    public void updateCharacter(Long id, String name){
        Character character = characterRepository.findById(id).get();
        character.update(name);
    }
}
