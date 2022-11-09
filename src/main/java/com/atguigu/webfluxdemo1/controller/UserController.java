package com.atguigu.webfluxdemo1.controller;

import com.atguigu.webfluxdemo1.entity.User;
import com.atguigu.webfluxdemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/user/{id}")
    public Mono<User> getUserId(@PathVariable("id") int id){
        return userService.getUserById(id);
    }

    @GetMapping("/user")
    public Flux<User> getUsers(){
        return userService.getAllUser();
    }

    @PostMapping("/saveuser")
    public Mono<Void> saveUser(@RequestBody User user){
        Mono<User> userMono = Mono.just(user);
        return userService.saveUserInfo(userMono);

    }
}
