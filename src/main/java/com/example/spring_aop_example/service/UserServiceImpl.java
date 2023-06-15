package com.example.spring_aop_example.service;

import com.example.spring_aop_example.entity.User;
import com.example.spring_aop_example.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;

  @Override
  public void saveUser(String username) {
    log.info("saveUser: {}", username);
    userRepository.save(User.builder().username(username).build());
  }

  @Override
  public void deleteUser(Long id) {
    log.info("deleteUser: {}", id);
    userRepository.deleteById(id);
  }

  @Override
  public void updateUser(Long id, String username) {
    log.info("updateUser: id={}, username={}", id, username);
    User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    user.changeName(username);
    userRepository.save(user);
  }

  @Override
  public User getUser(Long id) {
    log.info("getUser: {}", id);
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));
    return user;
  }

  @Override
  public List<User> getUsers() {
    log.info("getUsers");
    List<User> findUsers = userRepository.findAll();
    if (findUsers.size() > 0) {
      return findUsers;
    } else {
      throw new RuntimeException("Users not found");
    }
  }
}
