package com.example.spring_aop_example.service;

import com.example.spring_aop_example.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

  void saveUser(String username);

  void deleteUser(Long id);

  void updateUser(Long id, String username);

  User getUser(Long id);
  List<User> getUsers();
}
