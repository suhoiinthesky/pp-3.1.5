package com.Task31533.controller;


import com.Task31533.DTO.UserDTO;
import com.Task31533.Service.UserServiceIpm;
import com.Task31533.model.User;
import com.Task31533.util.PersonErrorResponse;
import com.Task31533.util.PersonNotFoundException;
import com.Task31533.util.WrongUserParamException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Здесь можно было бы создать метод для конвертации User в UserDTO и наоборот
 * Но контроллер не большой, так что обойдемся без этого
 */

@RestController
@RequestMapping("/api")
public class AdminController {
    private final UserServiceIpm userService;
    private final ModelMapper modelMapper;

    public AdminController(UserServiceIpm userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = userService.findAll();
        System.out.println(users);
        List<UserDTO> userDTOS = users.stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
        System.out.println(userDTOS);
        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOneDTO(@PathVariable("id") Long id) {
        return ResponseEntity.ok(modelMapper.map(userService.findById(id), UserDTO.class));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append(" "));
            throw new WrongUserParamException(sb.toString());
        } else {
            userService.save(modelMapper.map(userDTO, User.class));
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append(" "));
            throw new WrongUserParamException(sb.toString());
        } else {
            userService.update(modelMapper.map(userDTO, User.class));
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }


    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handleException(WrongUserParamException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
