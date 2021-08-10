# 평화지수 웹 어플리케이션 코드 저장소 

## 프로젝트 설명
평화지수 웹 어플리케이션 코드 저장소 입니다. Thymeleaf를 이용하여 레이아웃과 화면을 작성하였고 form을 전송합니다. JPA(hibernate)를 이용하여 게시판을 조회, RESTful Api를 작성하고 페이지를 검색합니다. 코딩의 신 Spring Boot으로 웹 출시까지 동영상에 큰 도움을 받았습니다.
(https://www.youtube.com/watch?v=FYkn9KOfkx0&list=PLPtc9qD1979DG675XufGs0-gBeb2mrona)

### 평화지수란?


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
#### 기본문법
* `th:text="${name}"` `th:text`는 태그 내부 텍스트에 대한 속성이며 모델 안의 키값에 해당하는 `${key}`으로 모델의 해당 키의 밸류값을 받는다.
* `"th:replace="fragments/common :: menu('home')"` fragments.common.html 에서 `th:fragment="menu"` 속성이 있는 태그를 찾아서 대체한다. 
* `th:classappend="${menu} == 'board'? 'active'"` menu의 밸류가 'board'면 class에 active 속성을 추가한다. 
#### 데이터베이스 조회
* `th:text="${#lists.size(boards)}` 전달 받은 boards 변수(List)의 크기를 알려주는 Thymeleaf 문법 
* `th:each="board : ${boards}"` boards 변수의 각 값을 boards로 변수로 선언하여 받는다.
#### form 전송하기
* `<a type="button"></a>` 으로도 버튼을 줄 수 있다. 
* `th:href="@{/board/form}"` 속성으로 이동하도록 지정한다.
* `th:object="${board}"` 컨트롤러에서 지정해준 board 키를 사용할 수 있다.
* `th:field="*{title}"`로 board 안의 어떤 속성을 사용할지를 th:field 속성으로 알려준다.
* form 태크의 속성에 `th:action="@{/board/form}`으로 포스트 요청을 할 url을 입력한다. `method=post`로 포스트 요청. 
* `th:href="@{/board/form{id=${board.id}}}"`로 board 클래스의 id 속성을 전달한다. 
* `th:classappend=${#fields.hasErrors('title')} ? 'is-invalid'` title과 관련된 이름으로 발생된 에러가 fields에 존재하는지 확인하고 is-invalid 속성 추가
* `th:errors="*{title}"` title 관련 에러 관련된 태크 속성

### Controller
* `@GetMappin("/url")` 어노테이션으로 url의 GET 요청을 받는다.
* `@RequestParam` 어노테이션으로 url/?의 파라매터 값을 받을 수 있다.
* 파라매터로 Model를 정의하면 Model 안에 키-밸류값을 넣을 수 있다.
* 문자열을 리턴하면 templates의 해당 html 파일을 화면에 보여준다. 
* `@Controller`로 어노테이션으로 컨트롤러임을 알려준다.
#### form 전송하기
* `@PostMapping` 어노테이션을 한 컨트롤러의 인풋 값으로 생성해둔 board 클래스를 전달한다. 이 후 model 속성에 board 클래스를 추가한다.
* 완료 시 이동할 페이지는 list인데, 원래 `@GetMapping("/list")`컨트롤러에서는 모델에 키 값 Board가 있고 값을 뿌려줘야 했지만, `@PostMapping("/list")`에서는 값을 뿌려주지 않고 바로 이동하면 되기 때문에 "redirect:/board/list" redirect 키워드를 주면 list로 리다이렉트가 되면서 화면이 이동된다. 
* form으로부터 url/id쿼리의 형태로 전달된 board 클래스를 `@RequestParam` 어노테이션으로 `@GetMapping("/form")` 컨트롤러에서 받는다.
* if 문을 이용하여 id가 null이면 new Board()를 타임리프 form에 전달하고, id값이 있을 경우에는 해당 값을 찾아서 board클래스를 전달한다. 
* hidden type의 `th:field="*{id}`를 추가하여 수정 시에 id 값을 전달해서 새로운 글이 아니라 수정된 글이 만들어지도록 한다. 
* `@ModelAttribute`를 `@Valid`로 바꾸어 주고 BindingResult 클래스를 인풋으로 준다. 
* `BindingResult.hasErrors()`로 체크
* `@AutoWired` 어노테이션을 선언하여 DI, boardValidator 인스턴스를 담기게 한다. 
* `boardRepository.save` 전에 `boardValidator.validate` 메소드에 board와 bindingResult를 파라매터를 건내어 준다. 

### Model
* Lombok: 모델 클래스를 만드면 멤버 변수는 프라이빗, 게터/세터를 퍼블릭으로 선언해서 사용해야한다. 게터/세터를 어노테이션 하나로 생성한다. 
* `@NotNull` `@Size` 어노테이션으로 form에서 들어오는 값을 확인한다. 

### JPA
* 객체 관계형 매핑(Object-relational mapping)은 한마디로 쿼리 없이 클래스만 이용해서 데이터베이스에 쉽게 접근할 수 있도록 하는 기술이다. JPA(Java Persistence API)는 자바에서 ORM을 어떻게 구현하는지에 대한 기술 스펙이다. JPA를 구현한 구현체 중에 가장 유명한 것이 hibernate이다.
* resources/application.propertiesd에 mariadb관련 정보들을 넣는다. 깃허브에서는 보안을 위하여 업로드 하지 않았다.
#### 데이터베이스 조회
* model 패키지의 Board 클래스에서 데이터베이스에서 정의해둔 필드들을 클래스의 멤버 변수로 정의한다. lombok의 `@Data` 어노테이션으로 클래스의 멤버 변수들을 외부에서 사용할 수 있다. 데이터베이스 연동을 위한 모델 클래스임을 알려주기 위하여 `@Entitiy` 어노테이션을 추가한다. primary key인 id위에는 `Id` 어노테이션을 추가하고, 자동 증가를 위하여 `@GenerateValue(strategy=GenerationType.IDENTITY)` 를 선언한다. `IDENTITY` 말로 `SEQUENCE`를 사용하면 성능이 보다 좋지만 추가 작업이 필요한다.  
* `JpaRepository<Board, Long>`를 상속받아서 레포지토리를 만든다.
* Controller의 파라매터에 모델을 추가하여 모델에 레포지토리로부터 가져온 원하는 값을 넣는다. 이를 위하여 private으로 레포지토리를 선언하고 `@AutoWired`로 인스턴스를 받는다(DI). JpaRepository에서 제공하는 `findAll()` 메소드 등을 사용하여 데이터베이스로부터 가져온 값을 모델에 넣는다.
#### form 전송하기
* `save(board)` 메소드로 board 클래스의 내용을 저장한다.

### BoardValidator
* Validator를 상속 받아 구현해야할 메소드들을 구현한다. Spring Boot Validator를 참고하여 형식을 맞춰 준다.
* obj 인풋을 Board로 형변환을 해준 후 필요한 에러를 체크해준다. 
* DI를 위하여 `@Component`로 등록해준다. 
