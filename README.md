# 평화지수 웹 어플리케이션 코드 저장소 

## 프로젝트 설명
평화지수 웹 어플리케이션 코드 저장소 입니다. Thymeleaf를 이용하여 레이아웃과 화면을 작성하였고 form을 전송합니다. JPA를 이용하여 게시판을 조회, RESTful Api를 작성하고 페이지를 검색합니다. 코딩의 신 Spring Boot으로 웹 출시까지 동영상에 큰 도움을 받았습니다.
(https://www.youtube.com/watch?v=FYkn9KOfkx0&list=PLPtc9qD1979DG675XufGs0-gBeb2mrona)

## 환경 및 세팅
* openjdk version "1.8.0_302"
* Spring Boot 2.5.3/ Maven Project/ Dependencies: Spring Boot DevTools, Lombok, Spring Web, Thymeleaf, Spring Data JPA
* MariaDB 10.6.3
* 보다 자세한 환경은 pom.xml를 확인해주세요.

## 코드 및 사용법

### 프로젝트 실행
* src/main/java/com.diplomacy.peaceindex/PeaceindexApplication.java를 실행시키시면 됩니다.

### Thymeleaf
* Thymeleaf는 resources/templates에 html 파일을 이용해 웹페이지를 만든다.
* html파일의 html 태그에 `xmlns:th="http://www.thymeleaf.org"` 속성을 추가한다.
* `th:text="${name}"` th:text는 태그 내부 텍스트에 대한 속성이며 모델 안의 키값에 해당하는 ${key}으로 모델의 해당 키의 밸류값을 받는다. 

### Controller
* @GetMappin("/url") 어노테이션으로 url의 GET 요청을 받는다.
* @RequestParam 어노테이션으로 url/?의 파라매터 값을 받을 수 있다.
* 파라매터로 Model를 정의하면 Model 안에 키-밸류값을 넣을 수 있다.
* 문자열을 리턴하면 templates의 해당 html 파일을 화면에 보여준다. 
* @Controller로 어노테이션으로 컨트롤러임을 알려준다.
  
