package com.example.spring_aop_example.controller;

import com.example.spring_aop_example.entity.User;
import com.example.spring_aop_example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("save/{username}")
    public String saveUser(@PathVariable String username) {
      log.info("controller saveUser: {}", username);
        userService.saveUser(username);
      log.info("controller saveUser end");
      return "success";
    }

    @GetMapping("find/{id}")
    public String getUser(@PathVariable Long id) {
      log.info("controller getUser: {}", id);
      User user = userService.getUser(id);
      log.info("controller getUser end");
      return user.toString();
    }
}
