# MVC:"어떻게 나눌 것인가"에 대한 해답 중 하나

사용이유: 유지보수가 편하려고

Model: 데이터 관련부분

View: 사용자한테 보이는 부분

Controller M과 V의 연결부분

## 원칙

1. Model내부에  Controller와 View에 관한 코드가 있으면 안된다.

2. View내부에  Model의 코드만 있을 수 있고, Controller의 코드가 있으면 안됨
3. View가 Model로부터 데이터를 받을 때는 다르게 보여주는 데이터만 받는다.
4. Controller 내부에는 Model과 View의 코드가 있을 수 있다.
5. View가 Model로부터 데이터를 받을 때 반드시 Controller 에서 받아야 한다.