# == vs equals
데이터를 **원시데이터(primitive)**와 **그렇지 않은것**으로 자바는 나눈다.

```java
int p1 = 1;
int p2 = 2;

p1 == p2 //true

String o1 = new String("Java")
String o2 = new String("Java")

o1 == o2 //false
o1.equals(o2) //true

String o3 = "Java1"
String o4 = "Java1"

o3 == o4 //true
```

==는 주소를 반환하고 equals는 주소를 타고 값을 반환한다.