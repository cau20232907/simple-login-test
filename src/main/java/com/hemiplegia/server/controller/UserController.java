package com.hemiplegia.server.controller;

import com.hemiplegia.server.dto.UserDto;
import com.hemiplegia.server.entity.UserEntity;
import com.hemiplegia.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public long signup(String username, String password) {
        UserDto userDto= UserDto.builder().username(username).password(password).build();
        return userService.save(userDto).getId();
    }

    @GetMapping("/")
    public String mainPage() {
        return "Main Page";
    }

    @GetMapping("/authonly")
    public UserEntity getUser(@AuthenticationPrincipal UserEntity userEntity) {
        return userEntity;
    }
}
