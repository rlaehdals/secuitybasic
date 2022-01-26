package com.example.securitybasic.controller;

import com.example.securitybasic.domain.Character;
import com.example.securitybasic.domain.User;
import com.example.securitybasic.dto.UserDto;
import com.example.securitybasic.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/")
    public String index(){
        return "Welcome Home";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserDto userDto){
        User success = userService.signup(userDto.getEmail(), userDto.getPassword());
        return "signup Success";
    }

    @PostMapping("/character")
    public String create(@RequestParam(name = "email") String email,
                         @RequestParam(name = "name") String name){
        Character character = userService.createCharacter(name, email);
        return "ok";
    }

    @PatchMapping("/character")
    public String update(@RequestParam(name = "id") Long id,
                         @RequestParam(name = "name") String name){
        userService.updateCharacter(id,name);
        return "ok";
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/notAccess")
    public String access(){
        return "notAccess";
    }
    @GetMapping("/guest")
    public String guest(){
        return "guest";
    }
    @GetMapping("/success")
    public String success(){
        return "success";
    }
}
