## MVC패턴:  "어떻게 나눌 것인가"에 대한 해답 중 하나

- M(model) (feign, DB), V(view), C(controller) 주고 받는것

![img](https://mblogthumb-phinf.pstatic.net/MjAxODEyMDVfMTA1/MDAxNTQ0MDE3MDQ3OTE3.g5uFx4CwmDcihBhk4Yc3DXwMrLE1FtQkRc5dQ3awkZkg.vNLkkt-Xyalvg4AwO65vf3b3bvwVaR0hnM9c639Mftog.JPEG.jhc9639/%25EB%25B8%2594%25EB%25A1%259C%25EA%25B7%25B81.jpg?type=w800)



사용자가 controller를 조작하면 controller는 model을 통해서 데이터를 가져오고 그 정보를 바탕으로 시각적인 표현을 담당하는 View를 제어해서 사용자에게 전달한다.

### Model

어플리케이션의 정보와 데이터를 의미한다. DATA, 정보들의 가공을 책임지는 컴포넌트를 말한다.

- 사용자가 편집하길 원하는 모든 데이터를 가지고 있어야 한다.
- 뷰나 컨트롤러에 대해서 어떤 정보도 알지 말아야 한다.
- 변경이 일어나면, 변경 통지에 대한 처리방법을 구현해야만 한다.

### View

사용자 인터페이스 요소이다.

- 모델이 가지고 있는 정보를 따로 저장해서는 안된다.
- 모델이나 컨트롤러와 같이 다른 구성요소들을 몰라야 된다.
- 변경이 일어나면 변경통지에 대한 처리방법을 구현해야만 한다.

### Controller

데이터와 사용자인터페이스 요소들을 잇는 다리역할이다.

- 모델이나 뷰에 대해서 알고 있어야 한다.
- 모델이나 뷰의 변경을 모니터링 해야 한다.

### 사용이유 및 의의

페이지, 데이터처리, 그리고 이 2가지를 중간에서 제어하는 컨트롤, 이 3가지로 구성되는 하나의 애플리케이션을 만들면 각각 맡은바에만 집중을 할 수 있게하여 **효율성**을 높이려는 것이다. **유지보수성**, 애플리케이션의 **확장성**, 그리고 **유연성**이 증가하고, **중복코딩**이라는 문제점 또한 사라지게 되는 것이다.

참고 https://m.blog.naver.com/jhc9639/220967034588

---

## @Controller vs @RestController 차이

### Controller

@Controller는 주로 View를 반환하기 위해 사용한다.

```java
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/users")
    public @ResponseBody ResponseEntity<User> findUser(@RequestParam("userName") String userName){
        return ResponseEntity.ok(userService.findUser(user));
    }
    
    @GetMapping(value = "/users/detailView")
    public String detailView(Model model, @RequestParam("userName") String userName){
        User user = userService.findUser(userName);
        model.addAttribute("user", user);
        return "/users/detailView";
    }
}
```



### RestController

@RestController는 @Controller에 @ResponseBody가 추가된 것이다. RestController의 주용도는 **Json 형태로 객체 데이터를 반환**하는 것이다. 동작 과정 역시 @Controller에 @ReponseBody를 붙인 것과 완벽히 동일하다.

```java
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/users")
    public User findUser(@RequestParam("userName") String userName){
        return userService.findUser(user);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<User> findUserWithResponseEntity(@RequestParam("userName") String userName){
        return ResponseEntity.ok(userService.findUser(user));
    }
}
```



---

## Spring bean

**스프링 빈**은 스프링 Ioc컨테이너에 의하여 관리되는 객체이다. **스프링 Ioc컨테이너**는 빈을 관리하는 객체이다. 스프링 빈과 스프링 Ioc컨테이너는 서로를 설명하는 의존적 존재이다.

### 빈과 의존성 주입

**직접 의존성을 주입**하는 것은 **의존관계를 모두 파악** 해야하는 불편함이 있다. 또한 **객체의 중복생성**이란 문제가 발생한다. 따라서 의존성 주입이 필요한 객체를 빈으로 등록하여 스프링컨테이너가 관리하게끔 해야 한다.

### 스프링 빈이 필요한 이유

스프링 Ioc컨테이너가 특정 객체의 라이프사이클을 관리한다는 것을 명시

### 스프링 Ioc컨테이너는 왜 빈을 관리할까?

의존성을 사용하는 로직에만 집중이 가능

의존성이 주입 될 객체가 싱글 오브젝트임을 보장



### 등록방법

- Component Scanning
  - @Component
    - @Repository
    - @Service
    - @Controller
    - @Configguration
- 직접 일일히 XML이나 자바 설정 파일에 등록(@Configuration+@Bean)

### 사용법

- @Autowired or @Inject
- ApplicationContext에서 getBean()으로 직접 써내시

### 특징 

- 오로지 빈들만 의존성 주입을 해준다.

참조: https://melonicedlatte.com/2021/07/11/232800.html

​          https://www.youtube.com/watch?v=qaIQfl0ob84

---

## 인터페이스

​	인터페이스는 클래스들에서 공통으로 사용되는 메서드를 상속시켜 반복을 줄일데 사용

### 정의

**인터페이스**란 몸체가 없는 메서드들의 집합이다. 클래스를 구현하기 전에 만들 메서드를 먼저 정해놓는 것이다.

```java
public interface Service {

    public static final number = 10;
        
    String getString(String a);
    int getInteger(int b);
}
// 메서드명, 리턴 타입, 매개변수를 정의
// 인터페이스에서 필드를 선언할 수 있지만 static final이 강제 적용되기 때문에 해당 필드의 값을 변경할 수 없다.
```

```java
public class ServiceImpl implements Service {
        
    public String getString(String a) {
            return a;
    }
    
    public int getInteger(int b) {
            return b;
    }
}
//인터페이스에서 정의한 메서드들을 똑같이 적어 로직을 구현한다. 
	//메서드명이나 매개변수가 다르면 오류
	//메서드들을 구현할 때 접근 제어자는 public이어야 한다

```

### 사용 이유

인터페이스를 이용하여 클래스를 구현하면 **다른 클래스와 대체가 유연**해서 **유지보수**가 편해진다.



참고 : https://kadosholy.tistory.com/101

​            https://lifejusik1004.tistory.com/entry/Java-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4%EC%9D%98-%EC%A0%95%EC%9D%98%EC%99%80-%EC%82%AC%EC%9A%A9%ED%95%98%EB%8A%94-%EC%9D%B4%EC%9C%A0





## OpenFeign

OpenFeign은 REST Call을 위해 호출하는 클라이언트를 보다 쉽게 작성할 수 있도록 도와주는 라이브러이다.

참조: https://www.baeldung.com/spring-cloud-openfeign

​	      https://sharplee7.tistory.com/68

---

## 파라미터 

파라미터는 쿼리랑 페스 파라미터로 나뉜다.

페스파라미터는 /변수/~~~

쿼리 파라미터는 /\~\~/\~\~?변수 

---

## 기타(1)

**빌드**는 코드를 작성해 **컴파일 된 것**에 외부 **라이브러리를 추가**하여 **실행파일로 만드는 과정**이다.

**컴파일**은 우리가짠 코드를 **컴퓨터가 사용하는 언어**로 **변환**하는 것이다.

**LomBok**은 **컴파일 하는 과정에서 사용**되는 것이다.

---

## Test

**전체적**으로 C S F에 잘 되는 지 확인 하는 것이 **통합 테스트**이고 단계 **단계를 끈어**서 하는 것이 **유닛 테스트**이다. **mock**을 사용하여 **임시데이터**를 받아야 유닛테스트가 가능하다.

### UnitTest

로컬테스를 통과했어도 다른곳에도 문제가 있을 수 있기 때문에 유닛 테스트가 필요하다.

### 통합 테스트(Integration Test)

통테는 여러 모듈들을 모아 이들이 의도대로 협력하는지 확인하는 테스트이다.

 

