# 평화지수 웹 어플리케이션 코드 저장소 

## 프로젝트 설명
평화지수 웹 어플리케이션 코드 저장소 입니다. Thymeleaf를 이용하여 레이아웃과 화면을 작성하였고 form을 전송합니다. JPA(hibernate)를 이용하여 게시판을 조회, RESTful Api를 작성하고 페이지를 검색합니다. 코딩의 신 Spring Boot으로 웹 출시까지 동영상에 큰 도움을 받았습니다.
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
* 템플릿 엔진.
* Thymeleaf는 resources/templates에 html 파일을 이용해 웹페이지를 만든다.
* html파일의 html 태그에 `xmlns:th="http://www.thymeleaf.org"` 속성을 추가한다.
* `th:text="${name}"` `th:text`는 태그 내부 텍스트에 대한 속성이며 모델 안의 키값에 해당하는 `${key}`으로 모델의 해당 키의 밸류값을 받는다.
* `"th:replace="fragments/common :: menu('home')"` fragments.common.html 에서 `th:fragment="menu"` 속성이 있는 태그를 찾아서 대체한다. 
* `th:classappend="${menu} == 'board'? 'active'"` menu의 밸류가 'board'면 class에 active 속성을 추가한다. 

### Controller
* `@GetMappin("/url")` 어노테이션으로 url의 GET 요청을 받는다.
* `@RequestParam` 어노테이션으로 url/?의 파라매터 값을 받을 수 있다.
* 파라매터로 Model를 정의하면 Model 안에 키-밸류값을 넣을 수 있다.
* 문자열을 리턴하면 templates의 해당 html 파일을 화면에 보여준다. 
* `@Controller`로 어노테이션으로 컨트롤러임을 알려준다.

### Lombok
* 모델 클래스를 만드면 멤버 변수는 프라이빗, 게터/세터를 퍼블릭으로 선언해서 사용해야한다. 게터/세터를 어노테이션 하나로 생성한다. 

### JPA
* 객체 관계형 매핑(Object-relational mapping)은 한마디로 쿼리 없이 클래스만 이용해서 데이터베이스에 쉽게 접근할 수 있도록 하는 기술이다. JPA(Java Persistence API)는 자바에서 ORM을 어떻게 구현하는지에 대한 기술 스펙이다. JPA를 구현한 구현체 중에 가장 유명한 것이 hibernate이다.
* resources/application.propertiesd에 mariadb관련 정보들을 넣는다. 깃허브에서는 보안을 위하여 업로드 하지 않았다.
* model 패키지의 Board 클래스에서 데이터베이스에서 정의해둔 필드들을 클래스의 멤버 변수로 정의한다. lombok의 `@Data` 어노테이션으로 클래스의 멤버 변수들을 외부에서 사용할 수 있다. 데이터베이스 연동을 위한 모델 클래스임을 알려주기 위하여 `@Entitiy` 어노테이션을 추가한다. primary key인 id위에는 `Id` 어노테이션을 추가하고, 자동 증가를 위하여 `@GenerateValue(strategy=GenerationType.IDENTITY)` 를 선언한다. `IDENTITY` 말로 `SEQUENCE`를 사용하면 성능이 보다 좋지만 추가 작업이 필요한다.  
