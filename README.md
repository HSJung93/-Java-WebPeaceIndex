# 평화지수 프로젝트
* 평화지수 웹 어플리케이션 코드 저장소 입니다.
* 국가 간의 관계를 다룬 영문 기사에 대한 대중들의 평가를 바탕으로 국가 간의 평화를 지수화하였습니다.
* 프로젝트의 최종 단계로서 지수화한 평화지수의 결과를 웹 어플리케이션을 통하여 공유하고자 합니다.

## 평화지수란?
### 미중관계 평화지수
![image01](https://user-images.githubusercontent.com/50201956/128863840-92bd9aa7-d1dd-42c8-97b9-b528e3779cec.png)
* 미중 평화지수(검정색), 중국의 미국 외 국가와의 평화지수(파랑색), 미국의 중국 외 국가와의 평화지수(붉은색)
* 미중관계(검정색)은 2019년 7월부터 2020년 10월까지 줄곧 음의 값을 보여주고 있으며 미국과 중국이 맺은 모든 국가간 관계에서 미중관계가 가장 낮은 점수를 보여주었다.
* 2019년 말과 2020년 1월 코로나바이러스 확산 직전 미중관계의 반등은, 미중 무역 갈등에 대한 1단계 타결 시기와 일치한다.
* 미중관계는 코로나바이러스가 확산된 2020년에 접어들면서 악화되었고, 2020년 6월 이후 본격적인 미국 대선시즌이 시작되면서 더욱 악화되는 모습을 보여주었다.
### 한일관계 평화지수
![image02](https://user-images.githubusercontent.com/50201956/128863872-be0189ce-b5e4-41bb-a1a0-08db472d3fe2.png)
* 한일 평화지수(검정색), 일본의 한국 외 국가와의 평화지수(파랑색), 한국의 일본 외 국가와의 평화지수(붉은색)
* 2019년 7월 1일 일본은 한국을 상대로 반도체 부품 관련 수출을 규제하였고, 평화지수에서도 2019년 7월의 한일 평화지수가 가장 낮은 수치를 보였다.
* 2020년 5월 19일 일본 외무성의 외교청서에 "한국은 중요한 이웃나라"라는 문구가 3년만에 다시 등장, 평화지수에서도 해당 시기에 관계 호전의 가능성을 보였다.
* 2020년 6월 2일 산업통상자원부가 일본의 수출규제에 대한 세계무역기구 제소를 재실시 하였고, 해당 기간 평화지수 또한 급속도록 하락하는 모습을 보였다.

## 웹 어플리케이션에 대한 간략한 설명
* SpringBoot와 Thymeleaf를 이용하여 레이아웃과 화면을 작성하였고 Form을 전송 기능을 구현합니다.
* JPA(hibernate)를 이용하여 게시판을 조회, RESTful Api를 작성하고 페이지를 검색합니다.
* [Spring Boot으로 웹 출시까지][coding-god]를 바탕으로 구현하였습니다.

### 환경 및 세팅
* openjdk version "1.8.0_302"
* Spring Boot 2.5.3/ Maven Project/ Dependencies: Spring Boot DevTools, Lombok, Spring Web, Thymeleaf, Spring Data JPA
* MariaDB 10.6.3
* 보다 자세한 환경은 pom.xml를 확인해주세요.

### 프로젝트 실행
* `java/~/PeaceindexApplication.java`
* src/main/java/com.diplomacy.peaceindex/PeaceindexApplication.java를 실행시키시면 됩니다.

## 웹 어플리케이션에 대한 자세한 설명
* 이하에서는 구현된 패키지 별로 각 기능에 필요한 코드들에 대한 설명을 덧붙였습니다.
* 구현된 패키지와 기능들
  * 패키지
    * 템플릿(1.)
    * 컨트롤러(2.)
    * 모델(3.)
    * 레포지토리(4.)
    * 서비스(5.)
    * 밸리데이터(6.)
    * 컨피그(7.)
  * 기능
    * 타임리프로 화면 작성(1))
    * 타임리프로 레이아웃 만들기(2))
    * JPA로 게시판 조회(3))
    * Form 전송하기(4))
    * 밸리데이션(5))
    * JPA로 API(6))
    * JPA로 페이지 처리 및 검색(7))
    * Spring Security로 로그인 처리(8))
    * JPA로 @OneToMany 관계 설정하기(9))
    * JPA로 FetchType 설정하기(10))
    * 권한에 맞는 화면 구성 및 API 호출(11))
    * JPA 이용한 커스텀 쿼리 만들기(12))
* **_예를 들어 컨트롤러(2.) 패키지에 작성된 Form 전송하기(4)) 관련 코드를 보고 싶으시면, 2. 4) 항목을 확인하시면 됩니다._**

### 1. 템플릿
* `resources/templates/~`
* Thymeleaf는 템플릿 엔진으로, resources/templates에 html 파일을 이용해 웹페이지를 만든다.
* html파일의 html 태그에 `xmlns:th="http://www.thymeleaf.org"` 속성을 추가한다.
#### 1) 타임리프로 화면 작성
* `index.html`, `fragments/common.html`
* `index.html`
  * `"th:replace="fragments/common :: menu('home')"` 속성은 fragments.common.html 에서 `th:fragment="menu"` 속성이 있는 태그를 찾아서 대체한다.
* `fragments/common.html`
  * `th:text="${name}"` `th:text`는 태그 내부 텍스트에 대한 속성이며 모델 안의 키값에 해당하는 `${key}`으로 모델의 해당 키의 밸류값을 받는다.
  * `th:classappend="${menu} == 'home'? 'active'"` menu의 밸류가 'home'이면 class에 active 속성을 추가한다.
#### 2) 타임리프로 레이아웃 만들기
* `board/list.html`, `fragments/common.html`
* `board/list.html`
  * `index.html`을 이용하여 `list.html`를 생성한다. 생성 시 `fragments/common.html` 네임스페이스를 추가한다.
  * `"th:replace="fragments/common :: head('게시판')"`으로 헤드 태그를 대체한다.
* `fragments/common.html`
  * `th:classappend="${menu} == 'board'? 'active'"`으로 조건부로 active 속성을 추가한다.
  * `th:if="${menu}=='home'`과 `th:if="${menu}=='board'`속성으로 활성화된 메뉴일때만 current 지시자가 화면에 표시되도록 한다.
  * `th:href="@{/}`과 `th:href="@{/board/list}`로 링크를 연결해준다.
#### 3) JPA로 게시판 조회
* `board/list.html`
* `th:text="${#lists.size(boards)}` 전달 받은 boards 변수(List)의 크기를 알려주는 size 문법을 사용한다.
* `th:each="board : ${boards}"` boards 변수의 각 값을 boards로 변수로 선언하여 받는다.
#### 4) Form 전송하기:
* `board/list.html` `board/form.html`
* `board/form.html`
  * `list.html`를 이용하여 `form.html`를 생성한다. 부트스트랩의 `form` 예제를 사용한다.
  * `form.html`의 취소 버튼을 통하여 list.html로 돌아가는 기능 구현
    * `<a type="button"></a>` 으로 쓰기 버튼을 만들 수 있다. `th:href="@{/board/form}"` 속성으로 list.html로 이동하도록 지정한다.
  * 글을 새롭게 생성하는 기능 구현:
    * `form.html`의 쓰기 버튼을 통하여 컨트롤러에 값들을 보내는 기능 구현
    * `th:action="@{/board/form}`과 `method=post` 으로 데이터를 `@PostMapping("/form")` 컨트롤러에 포스트 요청을 한다(보낸다).
    * `th:object="${board}"`, `th:field="*{title}"`, `th:field="*{content}"`으로 속성을 지정해준다.
  * 글을 수정하는 기능 구현
    * 수정하기 위해서는 id값을 바탕으로 원래 값을 받아와야 한다
      * `GetMapping("/form")`에서 값들을 받아와 화면에 띄우는 기능 구현
      * 전체 폼 태그에서는 `th:object="${board}"` 겟 매핑 컨트롤러 전달 받은 모델의 board 키를 가진 속성을 사용한다.
      * `th:field="*{title}"`과 `th:field="*{content}"` board 안의 title 속성과 content 속성을 사용하여 form 태그의 세부 `div`를 채운다.
    * 수정한 값을 보낼 때는 id도 보내야 컨트롤러에서 수정을 한다.
      * hidden type의 `th:field="*{id}`를 추가하여 수정 시에 id 값을 전달해서 새로운 글이 아니라 수정된 글이 만들어지도록 한다.
* `board/list.html`
  * 글을 새롭게 생성하는 기능 구현: id 없이 form.html로 보내는 버튼 생성
    * `<a type="button"></a>` 으로 쓰기 버튼을 만들 수 있다. `th:href="@{/board/form}"` 속성으로 form.html로 이동하도록 지정한다.
  * 글을 수정하는 기능 구현: form.html로 id값과 함께 전달하고, 컨트롤러에서 id 값에 맞는 데이터를 조회하도록 한다.
    * `list.html`의 작성된 글의 제목을 클릭하면 그 글의 id값을 전달하면서 `form.html`로 보내도록 한다.
    * `th:href="@{/board/form{id=${board.id}}}"`로 board 클래스의 id 속성을 전달한다. 이는 컨트롤러의 `GetMapping("/form")`에서 `@RequestParam`이라는 어노테이션으로 파라매터로 전달 받을 것이다.
    * 이 id 값을 바탕으로 `GetMapping("/form")`에서 데이터를 조회할 것이다.
#### 5) 밸리데이션
* `board/form.html`
* `th:classappend=${#fields.hasErrors('title')} ? 'is-invalid'` title과 관련된 이름으로 발생된 에러가 fields에 존재하는지 확인하고 is-invalid 속성 추가
* `th:errors="*{title}"` title 관련 에러 관련된 태크 속성
#### 7) JPA로 페이지 처리 및 검색
* 전체 건수: `boards.getTotalElements()` 값을 타임리프에서는 `${boards.totalElements}`로 불러와서 사용한다.
* `li` 태그의 속성으로 `th:each=`로 컨트롤러에서 작성한 페이지 변수값을 가져온다.
* `i : ${#numbers.sequence(startPage, endPage)}`로 두 변수값으로 만든 시퀀스의 숫자 값 i를 정의한 후, `th:text=${i}`로 보여준다.
* 페이지가 본인 페이지일 때 본인, 페이지가 처음일때 previous 끝일때 next를 클릭하지 못하도록한다.
  * `th:classappend="${i == boards.pageable.pageNumber + 1} ? 'disabled'"`으로 조건부로 클래스 값을 추가해준다.
    * 조건문을 만들 때, 컨트롤러의 `boards`에서는 `getPageable()`과 `getPageNumber()`로 가져올 수 있었던 값들을 사용한다.
  * `i` 를 `boards.totalPages` 나 `1`로 바꾸어서 next와 previous 태그에 `disabled` 속성을 더해준다.
* 페이지 이동 링크를 `th:href=`로 주되, 앞에서 컨트롤러에 파라매터로 준 `pageable` 변수의 입력 값을 `GetMapping("/list")` 에 전달한다.
  * 타임리프 문법에 따르면 `th:href=@{/boards/list(page=${boards.pageable.pageNumber - 1})}` 로 `()`안에 변수의 입력값을 준다.
  * 이 때 현재 페이지를 만들 때 받아온 `boards.pageable.pageNumber`를 이용한다.
* 검색 기능 구현
  * `"searchTable"`이라는 `for` 속성을 가진 `label` 태그와 `name` 속성을 가진 `input` 태그, `button`태그를 가진 form을 부트스트랩으로 부터 가져온다.
  * `d-flex justify-content-end` 클래스로 우측 정렬을 한다.
  * `btn-light`로 버튼 색깔을 바꾼다.
  * form 태그에만 `method="GET"`, `th:action="@{/board/list}"`으로 지정해주면, form 태그 내부의 `name=searchText` 값을 파라매터로 전달한다.
  * `th:value=${param.searchText}`로 url에 있는 파라매터 값을 보여줄 수 있다.
  * `th:href=@{/boards/list(page=${boards.pageable.pageNumber - 1}, searchText=${param.searchText} )}`로 페이지를 이동해도 `searchText` 값을 전달하여 검색이 유지되도록 한다.
#### 8) Spring Security로 로그인 처리
* `/fragments/common.html`, `/account/login.html`, ``/account/register.html``
* `common.html`
  * css 파일의 주소를 `th:href="@{/css/starter-template.css}`로 수정한다.
  * a 태그와 button 클래스를 이용한 Login 버튼을 추가한다.
    * `th:href="@{/account/login}"`
    * `sec:authorize="!isAuthenticated()"`로 로그인 되지 않은 사람에게만 보여준다.
  * button 태그를 이용한 Logout 버튼을 추가한다.
    * `class="text-white mx-2"` 로 흰색 글씨와 x방향 2의 마진을 준다.
    * `sec:authorize="isAuthenticated()"`로 로그인된 사람에게만 보여준다.
    * 사용자와 권한 span에 `sec:authentication`을 추가하여 해당 항목을 표시해준다.
    * `th:action="@{/logout}" method ="POST"`로 포스트 요청을 보내서 로그아웃이 되도록 한다.
  * 메이븐에 타임리프의 SpringSecurityIntegrationModule을 추가한뒤 html 속성에 주소를 달아 sec 네임스페이스를 사용한다.
  * Login 버튼 뿐 아니라 회원가입 버튼도 추가한다.
* `login.html`
  * `index.html`를 복사한 뒤, 헤드와 바디를 부트스트랩 소스를 가져와 수정한다.
  * body에는 `class="text-center"` 속성을 추가한다.
  * signin.css를 추가한뒤, `th:href="@{/css/signin.css}`를 지정해준다.
  * img의 이미지 주소를 부트스트랩 원본 소스 파일을 바탕으로 수정해준다.
  * label의 email을 username으로 수정하고, input의 type, id, placeholder 또한 text, username, Username으로 수정해준다.
  * 에러 메시지를 form 태그 위에 넣는다.
    * `class = "alert alert-primary"`와 `role="alert"`를 추가한다.
  * form 태그의 `th:action="{@/account/login}"`과 `method="post"`으로 타임리프 속성을 이용하여 csrf 토큰도 같이 전달해준다.
  * input 속성으로 name을 추가하여 컨트롤러에서 사용할 수 있도록 한다.
  * div의 `th:if="${param.error}"`와  `th:if="${param.logout}"`으로 해당 요청 시 보여줄 요소를 지정한다.
  * 이미지에 홈에 돌아오는 앵커 태그를 추가한다.
* `register.html`
  * `login.html`를 복사한 뒤, Sign in을 회원가입으로 포스트 요청을 보낼 곳을 `account/register`로 수정한다.
  * 이미지에 홈에 돌아오는 앵커 태그를 추가한다.
#### 9) JPA로 @OneToMany 관계 설정하기
* `list.html`, `form.html`
* `list.html`: 기존에는 직접 입력했던 사용자 이름을 `th:text="${board.user.username}"`으로 대체한다.
* `form.html`: api를 사용하고 크롬을 통하여 디버깅을 해보면 hidden type 속성과 _csrf name 속성의 그리고 암호화된 키값의 value 속성을 가진 input이 존재한다. 스프링 시큐리티의 `th:action`에 csrf 옵션이 활성화되어 있는 것이다. `WebSecurityConfig`에서 수정가능하다.

#### 11) 권한에 맞는 화면 구성 및 API 호출
* `form.html`, `common.html`
* ROLE_ADMIN만 삭제할 수 있도록 한다.
  * 삭제 버튼을 ROLE_ADMIN 에게만 표시되도록 한다: `sec:authorize="hasRole('ROLE_ADMIN')"`을 버튼의 속성에 더한다.
  * 삭제 기능은 자바스크립트 ajax로 구현한다.
    * `th:onclick="|deleteBoard(*{id})|` 로 타임리프 문법에 맞게 id 변수를 deleteBoard 함수에 넣는다.
    * `$.ajax()` 의 url과 type를 입력하고, 성공시 콘솔 출력 함수를 만들어서 BoardApiController의 `@DeleteMapping("/boards/{id}")`를 호출한다.
    * script의 src의 jqeury 버전을 slim에서 전체로 수정한다.
    * `window.location.href = "/board/list"`로 이동할 주소를 설정한다.
  * 이 때 버튼만 가리면 postman등을 이용하여 사용자가 삭제할 수 있는 문제점이 있다. 따라서 서버에서 처리할 필요가 있다. 이를 위해 `MethodSecurityConfig`를 만들고 컨트롤러에 제약을 준다.
* 자바스크립트 jquery를 불러오는 부분을 footer라고 이름 붙이고 common.html에만 남겨둔채, 다른 템플릿에서는 `th:replace="fragments/common :: footer`로 구현한다.

### 2. 컨트롤러
* `java/~/controller/~`
#### 1) 타임리프로 화면 작성
* `HomeController`
* `@Controller`로 어노테이션으로 컨트롤러임을 알려준다.
* `@GetMapping("/url")` 어노테이션으로 url의 GET 요청을 받는다.
* 문자열을 리턴하면 templates의 해당 html 파일을 화면에 보여준다.
#### 2) 타임리프로 레이아웃 만들기
* `BoardController`
* `@RequestMapping("/board")`와 `@GetMapping("/list")`로 `"/board/list"` 경로 지정
* `@RequestParam` 어노테이션으로 url/?의 파라매터 값을 받을 수 있다.
* 파라매터로 Model를 정의하면 Model 안에 키-밸류값을 넣을 수 있다.
#### 3) JPA로 게시판 조회
* `BoardController`
* 게시판 호출을 위한 데이터 값을 넘겨 받기 위하여 파라매터로 `Model model`을 추가한다.
* `BoardRepository`를 `private boardRepository`으로 선언하고 `@Autowired`로 DI한다.
* 이후 list 메소드에서 `List<Boards> boards = boardRepository.findAll();` 로 데이터를 받고, `addAttribute("boards", boards);`로 "boards"를 키 값으로 `model`에 전달한다. 이제 모델에 담긴 데이터를 타임리프에서 사용할 수 있다.
#### 4) Form 전송하기
* `BoardController`
* `@GetMapping("/form")`으로 `form.html`으로 연결한다.
  * JPA로 게시판을 조회할 때처럼 모델 클래스를 받고 속성을 추가하는데, `new Board()`로 새로운 board 모델 클래스를 속성에 넣어준다.
  * `@RequestParam(required=false) Long id` 어노테이션으로 `url/id`쿼리의 형태로 전달된 필수가 아닌 데이터를 받는다.
    * 이 데이터를 바탕으로 id가 없으면, 기존대로 `new Board()`를 모델에 속성으로 전달한다.
    * id가 있으면, `boardRepository.findById(id);`로 데이터베이스를 조회하고 없으면 `.orElse(null)`로 null 값을 board에 넣어서 모델에 속성으로 전달한다.
* `@PostMapping("/form")`
  * `@ModelAttribute Board board`로 인풋 값으로 생성해둔 board 모델 클래스를 전달받는다.
  * JPA로 게시판을 조회할 때처럼 `BoardRepository`가 기본적으로 제공하는 메소드를 사용하는데 이번에는 `findAll();`이 아니라 `save(board)`로 전달받은 board 모델 클래스를 저장한다. 이때 id 값이 있느냐에 따라서 자동으로 INSERT 혹은 UPDATE를 해준다.
  * 완료 시 이동할 페이지는 list인데, `return "redirect:/board/list";`로 redirect 키워드를 주면 list로 리다이렉트가 되면서 화면이 이동된다.
    * 원래 `@GetMapping("/list")`컨트롤러에서는 모델에 전달할 키 값 `"boards"`가 있고 값을 뿌려줘야 했다. 하지만 `@PostMapping("/form")`에서는 값을 뿌려주지 않고 `"board/list"`로 바로 이동하면 되기 때문에 리다이렉트로 충분하다.
#### 5) 밸리데이션
* `BoardController @PostMapping("/form")`
* `@ModelAttribute`를 `@Valid`로 바꾸어 주고 BindingResult 클래스를 파라매터로 준다.
* `BindingResult.hasErrors()`로 `Board`에서 `@NotNull` `@Size` 어노테이션으로 제한한 사항이 맞는지 체크한다.
  * 에러가 나면 리다이렉트 하지 않고 `@GetMapping("/form")`으로 연결한다.
* 밸리데이터 사용 시
  * `@AutoWired` 어노테이션을 선언하여 DI하고, boardValidator 선언한다.
  * `boardRepository.save` 전에 `boardValidator.validate` 메소드에 board와 bindingResult를 파라매터를 건내어 확인한다.
#### 6) JPA로 API
* `BoardApiController`
* `@RequestMapping("/api")`로 `BoardController`와 구분한다.
* C: `@PostMapping`어노테이션과 `save();` 메소드를 사용한다.
* R: `@GetMapping()`어노테이션과 `findAll();` `findById();` 메소드를 사용한다. 값이 없으면 에러가 발생하게 할 수도 있지만, `orElse(null)`로 null 값을 리턴하게 한다.
  * 제목(컨텐트)으로 검색하기 위해서는 `@RequestParam()`으로 title 파라매터를 추가한 뒤, 값이 전달이 되면 해당 값으로 찾아서 검색하도록 한다.
  * `defaultValue = ""` 옵션으로 기본값을 정의할 수 있다.
  * 검색을 위한 메소드는 `@Autowired private`으로 선언해 둔 `BoardRepository`에서 만든다.
* U: `@PutMapping()`어노테이션을 사용하고 `findById();`로 확인한다.
  * 데이터베이스에 값이 존재하면 `board.setName(newBoard.getName());`로 데이터베이스 저장한다.
  * 존재하지 않으면 `orElseGet(()->{ newBoard.setId(id) })`로 id 지정 후 `save(newBoard)`로 저장한다.
* D: `@DeleteMapping`어노테이션
#### 7) JPA로 페이지 처리 및 검색
* `BoardController`
* `@GetMapping("/list")`에서 `findAll()` 메소드 안에 `PageRequest.of(페이지_인덱스, 페이지_크기)`를 넣으면 `Page<Board>` 변수를 얻을 수 있다.
* `boards.getTotalElement()` 메소드로 전체 개수 값을 가져온다.
* 이런 하드 코딩 대신에 Request 파라매터로 `Pageable pageable`(springframework 패키지)을 받고, `findAll(pageable)`로 값을 지정한다.
  * `@PageableDefault(size=2)`로 size에 대한 기본값을 지정해줄 수 있다.
* `@GetMapping("/list")`에 `pageable` 파라매터를 추가 했으므로 `?page=1&size2` 등으로 url을 통하여 page 변수(와 파라매터)를 전달할 수 있다.
* list.html에 page 속성 전달
  * `boards.getPageable().getPageNumber()`로 현재 페이지 값을 가져온다.
  * `model.addAttribute("startPage", startPage)`로 startPage변수를 넣고 같은 방법으로 endPage 변수도 넣는다.
    * `Math.max`과 `Math.min`을 사용하여, 1과 `boards.getTotalPages()` 사이의 startPage와 endPage를 구한다.
* 검색 기능 구현
  * `@GetMapping("/list")` list.html로부터 받아온 `String searchText`를 파라매터로 준다. 이 파라매터로 검색하는 기능을 `BoardRepository`에서 구현한다.
  * 구현한 `findByTitleContainingOrContentContaining();`를 바탕으로 값을 boards에 넣는다.
  * `@RequestParam(required=false, defaultValue="")`로 값이 입력되지 않았을 때에도 작동하도록 한다.
#### 8) Spring Security로 로그인 처리
* `AccountController`
* `AccountController`에는 `@RequestMappin("/account")`, `login()` 에는 `@GetMapping("/login")`하고 `"/account/login"` 리턴.
* `register`에는 `@PostMappin(/register)`로 폼을 받을 것인데,
  * 그 이전에 user와 role을 받을 모델 클래스가 내부적으로 필요하다. -> User, Role 모델 클래스와 UserRepository를 구현한 후 User를 인자로 받는다.
  * 암호화와 권한 관리 등의 서비스를 처리할 패키지가 필요하다. -> UserService를 `@AutoWired`로 선언한 후, userService의 save메소드로 user를 저장한다.
  * 이 후 홈으로 돌려보내면 되므로 "redirect:/"를 리턴한다.
  * User를 인자로 받고 있기 때문에 User 클래스에서 선언한 값 username 과 password 에 맞춰서 값을 받게 된다.
* `@GetMapping("/register")` 도 하나 추가한다.
  * "/account/register"를 리턴한다.
#### 9) JPA로 @OneToMany 관계 설정하기
* `BoardController`, `UserApiController`
* `BoardController` 의 `@PostMapping("/form")`
  * `board.setUser(user);`로 board에 유저값을 담으면 유저의 키값을 참조해서 user id값이 담길 것이지만 보안이 문제가 된다.
  * 스프링 시큐리티에서 제공하는 `Authentication`을 파라미터로 받고 `.getName()`을 통하여 username 값을 받아서 처리한다.
  * `BoardService`에 board와 username을 저장해 서비스에서 저장할 수 있도록 한다.
* `UserApiController`
  * `BoardApiController`를 복사한 뒤 리팩토링한다. 게시글을 검색하는 `all()` 메소드는 간단하게 `.findAll()`로 수정한다.
  * 기존 `@PutMapping("/users/{id}")`에서 새로운 값을 부여하는 `setTitle`와 `setContent`는 주석처리한다.
  * `User` 클래스의 `@OneToMany` 매핑에서 사용한다.
  * 매핑이 완료되었으므로 UserApiController로도 boards를 수정할 수 있다.
  * api 요청을 newUser로 받았으므로 `user.setBoards(newUser.getBoards());`로 값을 저장한다. 그런데 DB에 값이 저장되지 않는다. User 클래스의 boards에 추가적인 설정이 필요하다.
    * -> `user.getBoards().clear();` 이후 `user.getBoards().addAll(newUser.getBoards());`로 기존의 데이터를 삭제한 후 데이터를 새롭게 넣어주는 코드로 변경한다.
  * 설정 후 user.getBoards()안의 board에 user 값을 넣어주면 마리아 DB에 정상적으로 저장이 된다.
  * `all()` 메소드에서 LAZY 옵션을 확인하도록 Log.debug()를 사용한다. 로그를 보기 위하여 application.properties의 설정도 수정한다.
  * 데이터를 따로 가져오는 LAZY 방식의 문제점은 UserRepository에서 해결한다.

#### 11) 권한에 맞는 화면 구성 및 API 호출
* `BoardApiController`
* `form.html`의 자바스크립트 ajax에서 delete 매핑을 호출한다.
* `MethodSecurityConfig`에서 호출한 `@Secured("ROLE_ADMIN")`으로 ROLE_ADMIN 사용자만 delete 매핑을 호출할 수 있도록 한다. 버튼을 누르면 403 에러가 나타난다.


### 3. 모델
* `java/~/model/~`
#### 3) JPA로 게시판 조회
* `Board`
* Board 클래스에서 데이터베이스에서 정의해둔 필드들을 `private`으로 클래스의 멤버 변수로 정의한다.
* lombok의 `@Data` 어노테이션으로 게터/세터를 만들어 클래스의 멤버 변수들을 외부에서 사용할 수 있다.
* 데이터베이스 연동을 위한 모델 클래스임을 알려주기 위하여 `@Entity` 어노테이션을 추가한다. 컨트롤러에서 Model를 파라매터에서 불러오면 그 때 속성에 넣어줄 수 있다.
* primary key인 id위에는 `Id` 어노테이션을 추가하고, 자동 증가를 위하여 `@GenerateValue(strategy=GenerationType.IDENTITY)` 를 선언한다.
* `IDENTITY` 말고 `SEQUENCE`를 사용하면 성능이 보다 좋지만 추가 작업이 필요한다.
#### 5) 밸리데이션
* `Board`
* `@NotNull` `@Size` 어노테이션으로 form에서 들어오는 값을 확인한다.
* `@Size`에 최대 최소 길이, 오류 시 출력한 메세지를 지정할 수 있다.
#### 6) JPA로 API
* `Board`
* Board 클래스를 그대로 사용한다.
#### 8) Spring Security로 로그인 처리
* `User`, `Role`
* `User`
  * `Board`와 같이 `@Entity`와 `@Data` 어노테이션 처리를 해준 뒤, id 도 선언한다.
  * user 테이블의 username, password, enabled를 선언한다.
  * JPA를 이용하여 조인을 하여 ManyToMany 매핑을 한다.
    * `List<Role> roles`로 멤버변수를 선언한다. 이후 `UserRepository`로 불러온다.
    * `@ManyToMany`어노테이션 말고도 `JoinTable`로 "user_role"에 연결될 두 컬럼명을 적어준다.
  * 사용자를 저장할때 권한까지 저장하는게 바람직하지 않아서 `cascade` 옵션은 주지 않는다.
  * 서비스를 구현한 뒤, `roles`를 선언할 때, null 예외처리를 피하기 위해 `ArrayList`로 선언해준다.
* `Role`
  * `User`를 복사해서 만들되 이번에는 name을 선언한다.
  * `List<User> users`를 가져올 때 `@ManyToMany(mappedBy="roles")` 로 유저 클래스에서 이미 설정해둔 컬럼명인 roles를 지정하여 같은 설정에 맞게 동일하게 가져오도록 양방향 매핑을 한다.
#### 9) JPA로 @OneToMany 관계 설정하기
* `Board`, `User`, `Role`
* `Board`
  * 기존에는 템플릿에서 사용자 이름을 입력했지만, 이제는 로그인하여 글을 쓴 사용자 정보를 표시하도록 한다.
  * 먼저 Many의 입장인 보드 클래스에서 One의 입장인 유저 클래스를 가져오고 `@ManyToOne` 어노테이션을 해준다.
  * `JoinColumn()` 어노테이션으로 마리아 DB의 테이블들을 연결해준다. 이를 위하여 DB의 board 테이블에 user_id 컬럼을 추가하고 user 테이블의 id값과 외래키로 연결해준다. 이 user_id 키값을 이용해서 user의 사용자 이름을 가져오는 로직을 작성하게 된다.
    * `name = "user_id"`와 `referencedColumnName = "id"`로 마리아 DB의 테이블과 컬럼을 지정해줄 수 있다. 다만 id의 경우 User 클래스에서 `@Id` 어노테이션 지정을 해주었기 때문에 생략할 수 있다.
  * `@JsonIgnore`로 api 요청 시에 재귀적으로 users가 표시되지 않도록 한다.
* `User`
  * `@OneToMany` 어노테이션을 준 `List<Board>`를 가져온다.
  * `mappedBy = "user"`로 Board 클래스의 변수명을 적어주면 user에 적어둔 `JoinColumn()` 설정을 그대로 사용할 수 있다.
  * `Board`와 `User`가 서로를 가지고 있어 서로를 조회할 수 있는 양방향 매핑이 성립되었다.
    * 통상적으로 양방향 매핑의 경우 `@ManyToOne`에 키에 대한 설정을 적어둔다.
  * `cascade = CascadeType.ALL`는 Hibernate의 ALL, REMOVE 등의 메소드를 지정해줄 수 있다.
    * 예를 들어 REMOVE 메소드를 사용하면, user값을 제거할 때 연쇄적으로 boards가 먼저 삭제가 되고 user가 삭제가된다.
  * `orphanRemoval = true`로 api로 데이터를 수정하면서 db에 저장된 기존의 user의 다른 데이터를 자동으로 삭제할 수 있다. 기본값이 false이다.
* `Role`
  * `@JsonIgnore`로 api 요청 시에 재귀적으로 users가 요청되지 않도록 한다.
#### 10) JPA로 FetchType 설정하기
* `User`
* `@OneToMany()`의 `fetch = FetchType.EAGAR` 옵션은 사용자 조회 시에 boards 클래스에 대한 데이터를 같이 가져올 지(EAGAR) 아니면, 나중에 필요할 때 가져올지(LAZY) 에 대한 설정이다.
  * `@OneToOne`과 `@ManyToOne` 기본값이 EAGAR이며, `@OneToMany`와 `@ManyToMany`의 기본값은 LAZY이다. 자동으로 불러와야할 컬럼이 하나인지 여러개 인지에 따라서 성능상의 부하를 고려한 기본값이다.
  * api 테스트를 위하여 roles는 `@JsonIgnore`로 데이터가 표시되지 않도록 한다.
  * LAZY로 설정하고 `UserApiController`의 `all()` 메소드를 사용하면 모든 보드(N)에 유저(1)까지 호출을 하는 N+1 문제가 발생하지 않는다.


### 4. 레포지토리
* `java/~/repository/~`
#### 3) JPA로 게시판 조회
* `BoardRepository`
* `BoardRepository`: `JpaRepository<Board, Long>`를 상속받아서 레포지토리를 만든다. 앞서 생성한 `Board` 모델 클래스를 불러와준다.
* 메소드들의 이름을 규칙에 따라서 필드명을 포함시켜 만들면, 인터페이스를 구현할 필요없이 데이터를 가져올 수 있다.
* 컨트롤러에서 레포지토리로 만든 Board 모델의 리스트를 속성에 넣어줄 수 있다.
#### 6) JPA로 API
* `BoardRepository`
* 제목으로 검색을 하기 위한 메소드는 `List<Board> findBy필드명`으로 인터페이스만 정의하면 알아서 구현해준다.
* `findById필드명Or필드명`이나 `findById필드명And필드명`으로 여러 조건으로 검색하는 메소드를 구현할 수 있다. Spring Data JPA의 Query Creation 참고하면 된다.
* `@Query(value="", nativeQuery=true)`로 커스텀 메소드를 만들 수 있다.
#### 7) JPA로 페이지 처리 및 검색
* `BoardRepository`
* 검색 기능 구현
  * `Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);`로 title과 content를 가진 데이터를 검색하여 pageable에 넣는 메소드를 구현한다.
#### 8) Spring Security로 로그인 처리
*`UserRepository`
* 빈 UserRepository를 설정한 뒤, AccountController의 register에서 이용한다.
#### 9) JPA로 @OneToMany 관계 설정하기
* `UserRepository`
* `findByUsername`로 username이라는 컬럼에서 데이터를 찾아오도록 한다.
* 새롭게 `findAll();` 메소드 명을 만든다. `@EntityGraph(attributePaths = { "boards" })`로 LAZY 옵션을 준 boards를 입력하면, FetchType을 무시하고 join방식으로 데이터를 한꺼번에 불러와서 해결한다. N+1의 쿼리가 만들어져 성능 상의 문제가 일어날 경우 이렇게 join 방식으로 쿼리를 짜주는 `@EntityGraph` 어노테이션으로 해결할 수 있다.


### 5. 서비스
* `java/~/service/~`
#### 8) Spring Security로 로그인 처리
* `UserService`
* `@Service` 어노테이션으로 비즈니스 로직을 구현하고, 테스트를 구현하기도 용이하다.
* AccountController에서 선언하여 사용하게 된다.
* User를 인풋과 아웃풋으로 하는 save 메소드를 작성한다. 이때 `@AutoWired`로 UserRepository를 선언하고 `userRepository.save(user)`를 리턴한다.
* 패스워드를 암호화한다.
  * `WebSecurityConfig`에서 설언해둔 `PasswordEncoder`를 `@AutoWired`로 선언하여 사용한다.
  * `user.getPassword()`로 가져온 패스워드를 `passwordEncoder.encode()`메소드로 암호화한다.
  * 이 값을 `user.getPassword()`로 새롭게 저장해준다.
  * `user.setEnabled()`도 기본적으로 가입하면 true로 해준다.
* 아이디와 패스워드 뿐만이 아니라 role 또한 저장한다.
  * 어레이리스트 형태로 불러온 role를 저장하려고 하는데, 데이터베이스에서 또다시 불러오기 위하여 Repository를 또 만들기 보다는, id를 하드코딩을 해서 데이터베이스에 만들어둔 role 값을 불러와서 넣어준다.
  * 그러면 user_role 테이블에 role 테이블 값이 저장된다.
#### 9) JPA로 @OneToMany 관계 설정하기
* `BoardService`
* `boardRepository`를 선언하고 save 메소드를 만들면 `BoardController`의 `@PostMapping`에서 `save(username, board);`를 사용할 수 있다.
* 컨트롤러에서 보내준 username과 board를 바탕으로, 레포지토리에서 정의한 `findByUsername` 메소드를 사용하여 user 클래스를 받는다.
* 이를 board 안에 user에 세팅한 후 `boardRepository`에 저장한 값을 리턴한다.

### 6. 밸리데이터
* `java/~/validator/~`
#### 5) 밸리데이션
* `BoardValidator`
* `Board` 클래스의 어노테이션을 통한 구현 이상을 원할 때, 커스텀 클래스를 만든다.
* `Validator`를 상속 받아 구현해야할 메소드들을 구현한다. Spring Boot Validator를 참고하여 형식을 맞춰 준다.
* `validate`메소드
  * obj 인풋을 Board로 형변환을 해준 후 필요한 에러를 체크해준다.
  * DI를 위하여 `@Component`로 등록해준다.

### 7. 컨피그
* `java/~/config/~`
#### 8) Spring Security로 로그인 처리
* `WebSecurityConfig`
* `WebSecurityConfigurerAdapter` 를 상속 받은 `WebSecurityConfig`에 `@Configuration` `@EnableWebSecurity` 어노테이션을 추가한다.
  * `@Configuration` 어노테이션을 추가해주면 `userDetailsService()`에서 `@Bean` 어노테이션을 사용할 수 있다. 이제 다른 클래스에서 `@AutoWired` 어노테이션을 사용해서 이 메소드를 사용할 수 있다. 현 프로젝트에서는 마리아 DB에 만든 사용자 테이블에서 사용자를 조회해서 로그인 처리를 할 것이므로 `userDetailsService()`가 필요하지 않다.
* `WebSecurityConfigurerAdapter`에서 이미 정의된 `configure()` 메소드에서는 `HttpSecurity`를 인자로 받아서 보안 설정을 해준다.
  * `authorizeRequests()` 에서는
    * `antMatcher("url").permitAll()`로 해당 url를 모든 사용자에게 열어둔다.
      * `/static/css` 폴더 또한 추가를 해준다.
      * `/account/register` 회원가입 페이지도 추가한다.
    * `anyRequest().authenticated`로 다른 요청은 로그인을 해야 페이지를 볼수 있도록 한다.
    * `and()`로 관련 설정을 끝낸다.
  * `formLogin()`로 로그인 페이지를 설정해준다.
    * `loginPage("/account/login")` 다른 페이지를 가면 자동으로 템플릿에 맞춘 로그인 페이지로 리다이렉트하도록 한다.
    * 로그인 되지 않은 사용자이므로 `permitAll()`을 해준다.
  * `logout()` 도 유사하게 설정한다.
* `configureGlobal`
  * `AuthenticationManagerBuilder`를 인자로 받고 내부에서 설정을 하게 되면, 인스턴스를 받아서 스프링에서 인증처리를 해준다.
  * `private DataSource datasource`에 `@Autowired` 어노테이션을 통하여 인스턴스를 주입한다. application.properties에 정의된 데이터 소스를 사용하게 한다. 이 데이터 소스를 `jdbcAuthentication().dataSource()`에 전달하면 알아서 인증처리를 해준다.
  * `userByUsernameQuery()`를 통하여 마리아 db의 user 테이블의 username, password, enabled 순서대로 값을 가져온다.
  * 스트링에 여백을 두어야 쿼리에서 오류가 나지 않으며, ?에는 값을 넣어준다.
  * `authoritiesByUsernameQuery`에서는 마리아 db의 role 테이블을 통하여 권한 처리를 위해 필요한 쿼리를 작성한다.
    * role과 user를 연결하는 user_role 테이블을 만든 후, user_role.user_id와 user.id를 inner join 해준다.
    * user_role.role_id와 role.id또한 inner join 해준다.
* `PasswordEncoder`를 통하여 비밀번호를 안전하게 암호화하는 기능을 스프링에서 제공해주고 있다.
  * 이 인스턴스 또한 `jdbcAuthentication()`에 `passwordEncoder()`에 넣어주면 이 인코더를 통하여 암호처리를 해준다.
* Authentication은 인증으로 로그인 관련된 처리, Authorization은 로그인 이후에 권한 처리를 의미한다.
#### 9) JPA로 @OneToMany 관계 설정하기
* `WebSecurityConfig`
* `.permitAll()`를 `antMatchers`에 더하여 api 테스트를 가능하게 한다.
* `.csrf.disable()`로 테스트를 가능하게 한다.

#### 11) 권한에 맞는 화면 구성 및 API 호출
* `MethodSecurityConfig`
* `GlobalMethodSecurityConfiguration`을 상속받고 사용할 방법을 `@EnableGlobalMethodSecurity`어노테이션 옵션으로 설정해준다. 이제 `BoardApiController`에서 `@Secured`어노테이션을 사용할 수 있다.


[coding-god]: https://www.youtube.com/watch?v=FYkn9KOfkx0&list=PLPtc9qD1979DG675XufGs0-gBeb2mrona
