# ToDo-List

스프링부트과 JPA를 어느정도 공부한 후 일단 만들어본 ToDo 프로젝트입니다.<br>
기본적인 CRUD를 중심으로 개발했으며 첫 프로젝트이기에 부족한 부분이 많아도 양해 부탁드립니다.<br>
**어떤 형태라도 피드백 주시면 감사하겠습니다**

https://hhj-todo.herokuapp.com/ 에서 사용해 보실 수 있습니다. (heroku eco dyno를 사용하여 최초 접속 시 로딩이 느릴 수 있습니다. sleep : 마지막 요청으로부터 30분 지나면 'sleep' 상태로 진입하여 애플리케이션이 중단)<br>
* 프로젝트 자체의 보안 수준이 낮기 때문에 체험 용도로만 사용하시고 민감한 정보는 등록하지 않는 것을 추천드립니다.

개발 과정은 [블로그](https://velog.io/@gudwn357/series/example)에 작성했으니 참고하시길 바랍니다.

## 개발환경
* IDE : IntelliJ IDEA Ultimate
* OS : Window 10
* SpringBoot 2.7.3
* Java 11
* Gradle 7.5

## 라이브러리
* Spring Web 
* Spring Data Jpa
* H2 Database (MySQL)
* Validation
* Thymeleaf
* Lombok
* Spring Security 5.7.3

로컬에선 H2 Database를 사용했고 heroku에 배포 시에는 heroku 내장 db(JawsDB MySQL)를 사용했습니다.<br>
디자인에 사용한 css와 js는 부트스트랩을 활용했습니다.

## 핵심 기능
* 회원 가입
![image](https://user-images.githubusercontent.com/80512150/196022943-34815b35-b1d0-4e68-9506-12496dced63e.png)

`Bean Validation`을 활용한 양식 검증 로직과 로그인 ID 중복 검증 로직 포함, 검증 오류 시 작성한 데이터는 남긴 채 에러메시지가 표시, 이후 Spring Security를 도입하여 비밀번호 암호화<br>

* 로그인
![image](https://user-images.githubusercontent.com/80512150/196023148-963982e6-a55d-4e1f-a3dc-c205d84e966c.png)

개발 초기에는 직접 로그인 기능(LoginService, LoginController)과 사용자 인증(LoginInterceptor)를 구현, 이후 Spring Security로 마이그레이션 <br>

* ToDo CRUD 
![image](https://user-images.githubusercontent.com/80512150/196023910-214c2f00-8c3c-40de-8da4-a0cd667dc1c0.png)
기본적인 CRUD 기능과 마감일, 생성일을 기준으로 정렬하여 출력하고 별도의 검증 로직 포함
![image](https://user-images.githubusercontent.com/80512150/196023952-36ae58fa-3f9b-493e-ba2c-cfea914afd1a.png)
![image](https://user-images.githubusercontent.com/80512150/196023966-c9c6dabe-fcbe-470d-970d-8e4f9b6f10dd.png)

ToDo 생성과 수정은 별도의 폼에서 처리<br>

## 세부 기능
* Spring Interceptor<br>
스프링 인터셉터를 활용하여 ~~비로그인 사용자의 경우 ToDo 관련 페이지 접근 불가~~(스프링 시큐리티로 대체), 다른 사용자의 ToDo에 접근 불가<br>
로그인 사용자에게는 상단에 로그아웃 버튼 생성, 첫 페이지에서 `로그인` 버튼 대신 `할 일` 버튼 표시, 로그인 페이지 접근 불가

* Spring Security<br>
프로젝트 중반, 스프링 시큐리티를 도입하여 회원 정보 암호화, 로그인, 사용자 인증, csrf 공격 방어 기능 처리
