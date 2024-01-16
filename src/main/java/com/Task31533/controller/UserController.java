package com.Task31533.controller;



import com.Task31533.DTO.UserDTO;
import com.Task31533.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/user/")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api")
    public ResponseEntity<UserDTO> mainPage(Principal principal) {
        return ResponseEntity.ok(modelMapper.map(userService.findByUsername(principal.getName()), UserDTO.class));
    }
}
