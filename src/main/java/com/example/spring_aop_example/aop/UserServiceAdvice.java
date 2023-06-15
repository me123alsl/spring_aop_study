package com.example.spring_aop_example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UserServiceAdvice {

  // @Around -> Advice : 적용 시점
  //("execution(....)") -> pointcut : 적용 범위
  @Around("execution(* com.example.spring_aop_example.service.UserService.*(..))")
  public Object log(ProceedingJoinPoint joinPoint) {
    log.info("Around Start - " + joinPoint.getSignature().getDeclaringTypeName() + " "+ joinPoint.getSignature().getName());
    Object proceed = null;
    try {
       proceed = joinPoint.proceed();
    } catch (Throwable t) {
      log.info("Around Exception - Message={} Class={}, Method={}",
          t.getMessage(),
          joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName());
    }
    log.info("Around End - " + joinPoint.getSignature().getDeclaringTypeName() + " " + joinPoint.getSignature().getName());
    return proceed;
  }

  // @Around -> Advice : 적용 시점
  //("within(....)") -> pointcut : 적용 범위
  @Around("within(com.example.spring_aop_example.service.*)")
  public Object log2(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("Around Start - " + joinPoint.getSignature().getDeclaringTypeName() + " "+ joinPoint.getSignature().getName());
    Object proceed = joinPoint.proceed();
    log.info("Around End - " + joinPoint.getSignature().getDeclaringTypeName() + " " + joinPoint.getSignature().getName());
    return proceed;
  }


  @Before("execution(* com.example.spring_aop_example.service.UserService.*(..))")
  public void before() {
    log.info("Before");
  }

  @After("execution(* com.example.spring_aop_example.service.UserService.*(..))")
  public void after() {
    log.info("After");
  }

  @AfterThrowing(value = "execution(* com.example.spring_aop_example.service.UserService.*(..))", throwing = "e")
  public void afterThrowing(RuntimeException e) {
    log.info("AfterThrowing");
    log.info("AfterThrowing- exception: {}", e.getMessage());
  }

  @AfterReturning(value = "execution(* com.example.spring_aop_example.service.UserService.*(..))"
  , returning = "returnValue")
  public void afterReturning(Object returnValue) {
    log.info("AfterReturning");
    log.info("AfterReturning- returnValue: {}", returnValue.toString());
  }

}
