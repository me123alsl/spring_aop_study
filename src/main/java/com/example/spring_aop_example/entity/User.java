package com.example.spring_aop_example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "user_table")
@ToString
public class User {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @Builder
  public User(String username) {
    this.name = username;
  }

  public void changeName(String newName) {
    this.name = newName;
  }

}
