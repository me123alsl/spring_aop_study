package com.example.spring_aop_example.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class globalExceptionHandler {

  @ExceptionHandler({RuntimeException.class})
  public String handleRuntimeException(RuntimeException e) {
    log.info("handleRuntimeException: {}", e.getMessage());
    return "error/500";
  }


}
