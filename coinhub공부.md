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

## bean

**스프링 빈**은 스프링 Ioc컨테이너에 의하여 관리되는 객체이다. **스프링 Ioc컨테이너**는 빈을 관리하는 객체이다. 빈과 스프링 Ioc컨테이너는 서로를 설명하는 의존적 존재이다.

빈과 의존성 주입





스프링 빈이 필요한 이유

스프링 Ioc컨테이너는 왜 빈을 관리할까?

참조: https://melonicedlatte.com/2021/07/11/232800.html

---

인터페이스 

​	인터페이스는 클래스들에서 공통으로 사용되는 메서드를 상속시켜 반복을 줄일데 사용



https://www.baeldung.com/spring-cloud-openfeign

---

파라미터는 쿼리랑 페스 파라미터로 나뉜다.

페스파라미터는 /변수/~~~

쿼리 파라미터는 /\~\~/\~\~?변수 

---

빌드는 코드를 작성해 컴파일 된것에 외부라이브러리를 추가하여 실행파일로 만드는 과정이다.

컴파일은 우리가짠 코드를 컴퓨터가 사용하는 언어로 변환하는 것이다.

LomBok은 컴파일 하는 과정에서 사용되는 것이다.