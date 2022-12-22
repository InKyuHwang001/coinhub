# Gradle

## 정의

**Groovy** 기반 **빌드 **자동화 시스템

- 빌드 : 소스 코드를 compile, 테스트, 정적 분석 등을 실행하여 실행 가능한 어플리케이션으로 만들어주는 과정

문제점(다양한 라이브러리 등장): 

- 라이브러리를 다운 추가 번거러움
- 개발자들 간의 버젼 관리 문제
- jar파일의 보안 위험

한번에 해결할 수 있는 도구가 있으면 Good

-> 빌드 도구의 등장



- Groovy:  JVM 상에서 실행되는 스크립트 언어 
  - java와 호환성이 높음 

## 장점

1. 프로젝트를 **설정 주입 방식**으로 정의
   - 필요한 정보가 있어야 주입한다
   - Maven의 상속 구조보다 **재사용이 용이**
   - 프로젝트의 조건을 체크랗 수 있어서 프로젝트별로 주입되는 설정을 다르게 할 수
2. **멀티 프로젝트** 빌드
   - 하나의 repository내에 여러개의 하위 프로젝트를 구성할 수 
     - 공통인거 따라 각자 필요한거 따로 구성할 수 있다.
3. **빌드 속도**의 빠름
   - 점진적 빌드
     - Gradle은 빌드 실행 중 마지막 빌드 호출 이후에 task의 입출력, 구현이 변경됬ㅈ는지 확인한다.
     - 최신 상태로 간주하지 않는다면 빌드는 실행되지 않는다.
   - 빌드 캐시
     - 하나의 빌드에서 사용되는 파일들이 다른 빌드들에 사용된다면 Gradle은 빌드 캐시를 이용해 이전 빌드의 결과물을 다른 빌드에도 사용
   - 데몬 프로세스
     - 서비스의 요청에 응답하기 위해 오래 살아있는 프로세스
     - Gradle의 데몬 프로세스는 메모리 상에 빌드 결과물을 보관
       - 한반 빌드된 프로젝트는 다음 빌드에서 매우 적은 시간만 소요

​	Maven 보다 2배 이상 최대 100배 빠르다.

## Gradle 사용 이유
Maven은 기존에 많이 사용했기에 자료가 많은 편이다.
하지만 새로운 프로젝트의 경우에는 성능상 Gradle을 사용하는 것이 유리하다.

## 사용 방법
```groovy
plugins { //플러그인
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
	mavenCentral() //의존 라이브러리들이 저장되어 있는 장소설정
}


dependencies { //의존성
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
	useJUnitPlatform()
}
```

### 의존성

- spring 관련 의존성은 컴파일과 런타임 모두에 사용
  - implementataion
- Lombok은 **컴파일**에만
  - compileOnly 
- h2database는 **런타임**시에만
  - runtimeOnly
- Spring-boot-starter-test는 **테스트**에서만  사용
  - testImplementation

---

- api- 내부 의존성을 컴파일과 런타임 모두에 보이는  API의존성

- implementation - 내부 의존성을 런타임에서만 보이는 구현 의존성



- complieOnly- 컴파일에만 사용되는 의존성 정의 

- runttimeOnly - 런타임에만 사용되는 의존성 정의

 

- test + Implementation, CompileOnly, RuntimeOnly
  - 해당 의존성을 테스트 시에만 사용하도록 정의

---
