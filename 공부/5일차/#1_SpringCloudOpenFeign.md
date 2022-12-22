# Spring Cloud OpenFeign

---

## 0. 요약

Open Feign은 Declarative(선언적인) HTTP Client 도구로써, 외부 API 호출을 쉽게할 수 있도록 도와준다. 여기서 “선언적인” 이란 어노테이션 사용을 의미하는데, Open Feign은 인터페이스에 어노테이션들만 붙여주면 구현이 된다. 이러한 방식은 Spring Data JPA와 유사하며, 상당히 편리하게 개발을 할 수 있도록 도와준다.

### OpenFeign의 장점

- 인터페이스와 어노테이션 기반으로 작성할 코드가 줄어들음
- 익숙한 Spring MVC 어노테이션으로 개발이 가능함
- 다른 Spring Cloud 기술들(Eureka, Circuit Breaker, LoadBalancer) 과의 통합이 쉬움

### OpenFeign의 단점 및 한계

- 기본 Http Client가 Http2를 지원하지 않음 → Http Client에 대한 추가 설정 필요
- 공식적으로 Reactive 모델을 지원하지 않음 → 비공식 오픈소스 라이브러리로 사용 가능
- 경우에 따라서 애플리케이션이 뜰 대 초기화 에러가 발생할 수 있음 → Object Provider로 대응 필요
- 테스트 도구를 제공하지 않음 → 별도의 설정 파일을 작성하여 대응 필요

---

 Spring Cloud OpenFeign는 Spring Boot 앱용 선언적 REST 클라이언트이다.

Feign은 Feign 주석 및 JAX-RS 주석을 포함하는 플러그 가능한 주석 지원을 통해 웹 서비스 클라이언트 작성을 더 쉽게만들어 준다.

- JAX-RS: 자바 플랫폼에서 경량화된 REST 방식의 웹 애플리케이션 구현을 지원하는 자바 API

Spring Cloud는 Spring MVC annotation  및 Spring Web에서 사용되는 것과 동일한 HttpMessageConverters의 지원을 추가한다.

## 1.Dependencies

`build.gradle`에 종속성을 추가한다.

```groovy
plugins {
	id 'java'
	id 'org.springframework.boot' version '2.7.6'
	id 'io.spring.dependency-management' version '1.0.15.RELEASE'
}

group = 'com.hwang'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '17'

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
//추가 부분 ---------
	// https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign
	implementation 'org.springframework.cloud:spring-cloud-starter-openfeign:3.1.4'
// 추가 부분--------
}

tasks.named('test') {
	useJUnitPlatform()
}
```

## 2.Feign Client

매인 클래스에 @EnableFeignClients을 추가 한다.

```java
package com.hwang.coinhubre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CoinhubreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinhubreApplication.class, args);
	}

}
```

@EnableFeignClients를 이용하여 @FeignClents라고 선언된 인터페이스를 스캔할 수 있다.

```java
package com.hwang.coinhubre.feign;

import com.hwang.coinhubre.model.UpbitCoinPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "upbit", url = "https://api.upbit.com/v1")
public interface UpbitFeignClient {

    @GetMapping("/ticker")
    List<UpbitCoinPrice> getCoinPrice(@RequestParam("markets") String coin);
}
```

위 예제에서는 클라이언트가 upbit API에서 읽도록 구성하였다.

value는 필수이며 임의의 값을 넣을 수 있다. url을 이용하여 API의 기본 url을 지정한다. 

이 인터페이스는 Feign 클라이언트이므로 Spring Web 주석을 사용하여 도달하려는 API를 선언할 수 있다.

##  3.구성 

Spring Cloud 는 사용자 정의할 수 있는 FeignClientsConfiguration 클래스를 사용하여 명명된 각 클라이언트에 대한 요청 시 새 기본 세트를 생성한다.

- Decoder – *ResponseEntityDecoder*, which wraps *SpringDecoder*, used to decode the *Response*
- Encoder – *SpringEncoder* is used to encode the *RequestBody*.
- Logger – *Slf4jLogger* is the default logger used by Feign.
- Contract – *SpringMvcContract*, which provides annotation processing
- Feign-Builder – *HystrixFeign.Builder* is used to construct the components.
- Client – *LoadBalancerFeignClient* or default Feign client

### 3.1.Custom Beans Configuration

### 3.2.Configuration Using Properties

## 4.Interceptors

모든 HTTP 요청/응답에 대해 인증에서 로깅에 이르기까지 다양한 암시적 작업을 수행할 수 있다.

### 4.1.Implementing *RequestInterceptor*

### 4.2.Using *BasicAuthRequestInterceptor*

## 5.히스트릭스 지원

Feign은 **Hystrix**를 지원하므로 활성화한 경우 **대체 패턴을 구현**할 수 있다.

## 6.Logging

각 Feign 클라이언트에 대해 기본적으로 로거가 생성된다.

로깅을 활성화하려면 클라이언트 인터페이스의 패키지 이름을 사용하여 *application.propertie* 파일 에서 로깅을 선언해야한다.

 **Feign logging**는 디버그 레벨에서만 지원된다.

## 7.오류 처리

Feign의 기본 오류 처리기인 *ErrorDecoder.default* 는 항상 *FeignException* 을 발생시킨다. 따라서 **던져진 예외를 사용자 정의하기 위해 CustomErrorDecoder** 를 사용하여야 한다.