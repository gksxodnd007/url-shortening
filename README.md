# URL SHORTENING SERVICE

## 개발 환경

- 언어 : JAVA (JDK 1.8)
- 프레임워크 : Spring boot 1.5.9 RELEASE, JPA & Hibernate
- 저장소 : MySQL
- 의존관리 : Maven 3.5.2

## 기능

- URL 입력 폼을 제공 한다.

- 단축 후 결과를 출력한다.

- 동일한 URL을 입력 할 경우 항상 동일한 shortening 결과 값이 나온다.

- shortening 의 결과 값은 8문자 이내로 생성한다.

- 브라우저에서 shortening URL을 입력하면 원래 URL로 리다이렉트한다.

## 단축 URL 생성 알고리즘

Generate shortening URL : within 8 charactors

- 1 번째 문자 : Protocol종류(HTTP : 1, HTTP : 2)
- 2 ~ 4 번째 문자 : schemes를 제외한 url의 base64 인코딩후 결과 출력되는 문자들 중 랜덤 3글자
- 5 ~ 7 번째 문자 : shortening URL을 요청받은 시점의 서버의 시스템 시간을 base64 인코딩한 결과 출력되는 문자들 중 랜덤 3글자

Base64인코딩을 사용한 이유 java API에 url safe한 인코딩을 지원해 줌
> Base64표에서 (62:'+', 63:'/') -> (62:'-', 63:'_')로 url에서 safe하지 않은 문자를 바꿔 줌

Base64인코딩에 관련된 참고자료
> http://effectivesquid.tistory.com/entry/Base64-%EC%9D%B8%EC%BD%94%EB%94%A9%EC%9D%B4%EB%9E%80

### Example
```
- INPUT : https://github.com/gksxodnd007
- OUTPUT : http://localhost:8080/2xVwBB9
```

```
- INPUT : www.kakaopay.com
- OUPUT : 잘못된 URL입니다. 다시 입력해주세요
```

## 프로그램 실행 방법

- test를 거치지 않고 실행하는 법
> mvn spring-boot:run

- test후 실행 법
> mvn clean install

- test로직은 UrlShorteningApplicationTests클래스의 메서드 주석을 참고

위의 명령어 빌드가 성공했다면 target폴더로 들어가 urlShortening.war를 실행한다.
> java -jar urlShortening.war

### 실행 전 주의사항

#### application.properties파일의 다음 속성들을 개인의 환경에 맞게 수정할 것 (DB를 사용한 애플리케이션)

- spring.datasource.url=jdbc:mysql://{ip}:{port}/{databaseName}?{parameterName}={value}...
- spring.datasource.username={userName}
- spring.datasource.password={password}

#### Maven으로 실행 할 경우 설치 유무 확인
#### jdk 버전 확인(해당 애플리케이션은 JDK1.8기준으로 개발됨)

### 실행 화면

![1](https://i.imgur.com/4ugLwQW.png)
![2](https://i.imgur.com/JR0tif7.png)
![3](https://i.imgur.com/rwgupeX.png)
