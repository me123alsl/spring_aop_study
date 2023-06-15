# AOP - Aspect oriented programming

---
<!-- TOC -->
* [AOP - Aspect oriented programming](#aop---aspect-oriented-programming)
  * [1. AOP 용어](#1-aop-용어)
  * [2. AOP 구현체](#2-aop-구현체)
  * [3. Spring AOP vs AspectJ](#3-spring-aop-vs-aspectj)
  * [4. Spring AOP](#4-spring-aop)
    * [4.1. 의존성 추가](#41-의존성-추가)
    * [4.2 AOP 설정](#42-aop-설정)
    * [4.3. Advice 종류](#43-advice-종류)
    * [4.4. Advice 실행 순서](#44-advice-실행-순서)
    * [4.5. PointCut 표현식](#45-pointcut-표현식)
    * [4.6. Advice 파라미터](#46-advice-파라미터)
    * [4.7. JoinPoint](#47-joinpoint)
    * [4.8. ProceedingJoinPoint](#48-proceedingjoinpoint)
    * [4.9. Advice Order](#49-advice-order)
    * [4.10. Advice 적용 제외](#410-advice-적용-제외)
    * [4.11.](#411-)
<!-- TOC -->

---

## 1. AOP 용어

- `Target`
    - 부가기능을 부여할 대상
- `Advice`
    - 어떤 부가기능
- `JoinPoint`
    - Advice 가 적용될 수 있는 위치 (메서드, 필드, 객체 등)
- `PointCut`
    - Advice 가 적용될 JoinPoint

## 2. AOP 구현체

- `Spring AOP`
    - Spring Framework 에서 제공하는 AOP 구현체
    - Proxy 기반 AOP 구현체
    - Spring Bean 에만 AOP 를 적용할 수 있음
    - 런타임 시점에 Proxy 객체를 생성해 AOP 를 적용함
- `AspectJ`
    - 독립된 AOP 프레임워크
    - Proxy, Weaving 기반 AOP 구현체
    - 모든 객체에 AOP 를 적용할 수 있음
    - 컴파일 시점에 바이트 코드를 조작해 AOP 를 적용함
    -

## 3. Spring AOP vs AspectJ

|    구분     |      Spring AOP       |           AspectJ           |
|:---------:|:---------------------:|:---------------------------:|
|    구현체    |        Spring         |           AspectJ           |
| AOP 지원 범위 |      Spring Bean      |            모든 객체            |
| PointCut  |          메서드          |        메서드, 필드, 객체 등        |
|  Advice   | Around, Before, After | Around, Before, After 등 7가지 |
|  컴파일 시점   |          런타임          |             컴파일             |
|  스프링 버전   |        2.0 이후         |           1.0 이후            |
|    장점     |        스프링 기반         |        모든 객체에 적용 가능         |
|    단점     | Spring Bean 에만 적용 가능  |   컴파일 시점에 바이트 코드를 조작해야 함    |

## 4. Spring AOP

### 4.1. 의존성 추가

```xml
<!-- Maven -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-aop</artifactId>
  <version>${spring.version}</version>
</dependency>
```

```yaml
# Gradle
dependencies {
  # Spring Framework AOP
implementation 'org.springframework:spring-aop:${spring.version}'
  # SpringBoot AOP
implementation 'org.springframework.boot:spring-boot-starter-aop'
}
```

### 4.2 AOP 설정

```java
/*-- Aspect  --*/
@Component
@Aspect
public class MyAspect {

  /*
   * @Around : 실행 시점
   * ("execution(...)"): 적용할 대상
   * ProceedingJoinPoint : JoinPoint 의 하위 인터페이스 (실제 로직 진입)
   * */
  @Around("execution(* com.example.demo..*.*(..))")
  public Object log(ProceedingJoinPoint joinPoint) {
      System.out.println("START: " + joinPoint);
      joinPoint.proceed();
      System.out.println("END: " + joinPoint);
    }
  }

```

```java
/*-- Business Logic --*/
@Slf4j
@Service
public class SampleService {

  public String getName() {
    log.info("SampleService.getName()");
    return "SampleService";
  }
}
```

``` bash 
# 실행 결과
START: execution(String com.example.demo.SampleService.getName())
SampleService.getName()
END: execution(String com.example.demo.SampleService.getName())
```

### 4.3. Advice 종류

|     Advice     | 설명          |
|:--------------:|:------------|
|     Before     | 메서드 실행 전    |
|     After      | 메서드 실행 후    |
| AfterReturning | 메서드 정상 종료 후 |
| AfterThrowing  | 메서드 예외 종료 후 |
|     Around     | 메서드 실행 전/후  |

### 4.4. Advice 실행 순서

| 순서 |                 Advice                 |
|:--:|:--------------------------------------:|
| 1  |               **Around**               |
| 2  |               **Before**               |
| 3  | **AfterReturning** / **AfterThrowing** |
| 4  |               **After**                |
| 5  |               **Around**               |

### 4.5. PointCut 표현식

|     표현식     |     설명     |                         예시                          |                         구문                          |
|:-----------:|:----------:|:---------------------------------------------------:|:---------------------------------------------------:|
|  execution  |  메서드 실행 시  |       execution(* com.example.demo..*.*(..))        | execution({접근제한자} {리턴타입} {패키지}.{클래스}.{메서드}({파라미터})) |
|   within    |   타입 내부    |             within(com.example.demo.*)              |                 within({패키지}.{클래스})                 |
|    this     |    빈 객체    |                     this(bean)                      |                    this({빈 객체})                     |
|    bean     |    빈 이름    |                 bean(sampleService)                 |                    bean({빈 이름})                     |
|    args     |   메서드 인자   |                   args(name, age)                   |                    args({파라미터})                     |
|   @target   |   어노테이션    |   @target(org.springframework.stereotype.Service)   |                  @target({어노테이션})                   |
|    @args    | 어노테이션 파라미터 |    @args(org.springframework.stereotype.Service)    |                   @args({어노테이션})                    |
|   @within   |  타입 어노테이션  |   @within(org.springframework.stereotype.Service)   |                  @within({어노테이션})                   |
| @annotation | 메서드 어노테이션  | @annotation(org.springframework.stereotype.Service) |                @annotation({어노테이션})                 |

### 4.6. Advice 파라미터

|         파라미터         | 설명                              |
|:--------------------:|:--------------------------------|
|      JoinPoint       | JoinPoint 의 하위 인터페이스 (실제 로직 진입) |
| JoinPoint.StaticPart | JoinPoint 의 하위 인터페이스 (실제 로직 진입) |
| ProceedingJoinPoint  | JoinPoint 의 하위 인터페이스 (실제 로직 진입) |

### 4.7. JoinPoint

|                메서드                 | 설명                              |
|:----------------------------------:|:--------------------------------|
|       String toLongString()        | JoinPoint 의 상세 정보               |
|       String toShortString()       | JoinPoint 의 간략한 정보              |
|          Object getThis()          | 대상 객체                           |
|         Object getTarget()         | 대상 객체                           |
|         Object[] getArgs()         | 파라미터                            |
|      Signature getSignature()      | 대상 메서드 정보 (리턴타입, 이름)            |
| SourceLocation getSourceLocation() | 대상 메서드 위치 (파일 위치)               |
|          String getKind()          | JoinPoint 종류 (method-execution) |
|     StaticPart getStaticPart()     | JoinPoint 의 정적인 정보              |

### 4.8. ProceedingJoinPoint

|              메서드              | 설명                  |
|:-----------------------------:|:--------------------|
|       Object proceed()        | 대상 메서드 실행           |
| Object proceed(Object[] args) | 대상 메서드 실행 (파라미터 변경) |

### 4.9. Advice Order

설명 : `@Order` 어노테이션을 사용하여 Advice 의 실행 순서를 지정할 수 있다.

```java
/*-- Aspect  --*/
@Component
@Aspect
@Order(1)
public class MyAspect {
  // ...
}

/*-- Aspect  --*/
@Component
@Aspect
@Order(2)
public class MyAspect2 {
  // ...
}
``` 

### 4.10. Advice 적용 제외

설명 : `@Around` 어노테이션의 `!` 를 사용하여 적용 제외할 수 있다.

```java
/*-- Aspect  --*/
@Component
@Aspect
public class MyAspect {

  @Around("execution(* com.example.demo..*.*(..)) && !execution(* com.example.demo.SampleService.getName(..))")
  public Object log(ProceedingJoinPoint joinPoint) {
      System.out.println("START: " + joinPoint);
      Object o = joinPoint.proceed();
      System.out.println("END: " + joinPoint);
      return o;
    }
  }
```

### 4.11. 