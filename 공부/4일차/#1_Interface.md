# Interface : 설계도


## 정의

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

## 사용 이유

### 1. Cooperation (협업)

**사용 전**

```java
/**
 * @author 팀원1
 */
public class EnglishTrans {
  public String koreanToEnglish(String str) {
    //TODO: 한국어를 영어로 번역
  }
  public String englishToKorean(String str) {
    //TODO: 영어를 한국어로 번역
  }
}
```
```java
/**
 * @author 팀원2
 */
public class ChineseRendering {
  public String translateChinese(String str) {
    //TODO: 한국어를 중국어로 번역
  }
  public String translateKorean(String str) {
    //TODO: 중국어를 한국어로 번역
  }
}
```
```java
EnglishTrans trans = new EnglishTrans();
String str = trans.koreanToEnglish(str);
// TODO: Business Login
...

ChineseRendering rendering = new ChineseRendering();
String str = rendering.translateChinese(str);
// TODO: Business Login
...
```
```java
public interface Translation {
  // 한국어를 번역하다
  public String translate(String str);
  // 한국어로 번역하다
  public String translateInto(String str);
}
```
 개인의 개성이 심하게 반영

 **사용 후**

```java
/**
 * @author 팀원1
 */
public class EnglishTranslation implements Translation {
  @Override
  public String translate(String str) {
    //TODO: 한국어를 번역
  }
  @Override
  public String translateInto(String str) {
    //TODO: 한국어로 번역
  }
}
```
```java
/**
 * @author 팀원2
 */
public class ChineseTranslation implements Translation {
  @Override
  public String translate(String str) {
    //TODO: 한국어를 번역
  }
  @Override
  public String translateInto(String str) {
    //TODO: 한국어로 번역
  }
}
```
```java
Translation trans = new EnglishTranslation();
String str = trans.translate(str);
// TODO: Business Login
...

Translation trans = new ChineseTranslation();
String str = trans.translate(str);
// TODO: Business Login
...
```

### 2. Easy to replace (교체 용이)
```java
public interface Crypto {
  // 암호화
  public String encrypt(String str);
  // 복호화
  public String decrypt(String str);
}
```

```java
public class SHA512Crypto implements Crypto {
  @Override
  public String encrypt(String str) {
    // TODO: SHA512 방식으로 암호화
  }
  @Override
  public String decrypt(String str) {
    // TODO: SHA512 방식으로 복호화
  }
}
```
```java
Crypto crypto = new SHA512Crypto(); // 기존 SHA512 방식
String enc = crypto.encrypt("1234");
교체

Crypto crypto = new SuperPowerCrypto(); // 신규 개발한 암호화 방식
String enc = crypto.encrypt("1234");
```
### 3. Multiple inheritance (다중 상속)