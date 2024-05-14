package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CreateUserRequest;
import com.example.ecommerce.dto.UpdateUserRequest;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/user")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{mail}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String mail){
        return ResponseEntity.ok(userService.getUserByMail(mail));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest userRequest){
        return ResponseEntity.ok(userService.createUser(userRequest));//201
    }
    @PutMapping("/{mail}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String mail,@RequestBody UpdateUserRequest userRequest){
        return ResponseEntity.ok(userService.updateUser(mail,userRequest));//202
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> deactiveUser(@PathVariable("id") Long id){
        userService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> activeUser(@PathVariable("id") Long id){
        userService.activeUser(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
