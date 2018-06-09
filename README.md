# URL SHORTENING SERVICE

## 기능

- URL 입력 폼을 제공 한다.

- 단축 후 결과를 출력한다.

- 동일한 URL을 입력 할 경우 항상 동일한 shortening 결과 값이 나온다.

- shortening 의 결과 값은 8문자 이내로 생성한다.

- 브라우저에서 shortening URL을 입력하면 원래 URL로 리다이렉트한다.

### Example
```
- INPUT : https://github.com/gksxodnd007
- OUTPUT : http://localhost:8080/2xVwBB9
```

```
- INPUT : www.naver.com
- OUPUT : 잘못된 URL입니다.
```

## 개발 환경

- 언어 : JAVA (JDK 1.8)
- 프레임워크 : Spring boot 1.5.9 RELEASE, JPA & Hibernate
- 저장소 : MySQL
- 의존관리 : Maven 3.5.2

## 프로그램 실행 방법

> mvn spring-boot:run
