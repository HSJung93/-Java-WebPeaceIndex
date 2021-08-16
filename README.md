# 평화지수 웹 어플리케이션 코드 저장소 

## 프로젝트 설명
평화지수 웹 어플리케이션 코드 저장소 입니다. 국가 간의 관계를 다룬 영문 기사에 대한 대중들의 평가를 바탕으로 국가 간의 평화를 지수화하였습니다. 프로젝트의 최종 단계로서 지수화한 평화지수의 결과를 웹 어플리케이션을 통하여 공유하고자 합니다.

### 평화지수란?
#### 미중관계 평화지수
![image01](https://user-images.githubusercontent.com/50201956/128863840-92bd9aa7-d1dd-42c8-97b9-b528e3779cec.png)
* 미중 평화지수(검정색), 중국의 미국 외 국가와의 평화지수(파랑색), 미국의 중국 외 국가와의 평화지수(붉은색)
* 미중관계(검정색)은 2019년 7월부터 2020년 10월까지 줄곧 음의 값을 보여주고 있으며 미국과 중국이 맺은 모든 국가간 관계에서 미중관계가 가장 낮은 점수를 보여주었다.
* 2019년 말과 2020년 1월 코로나바이러스 확산 직전 미중관계의 반등은, 미중 무역 갈등에 대한 1단계 타결 시기와 일치한다. 
* 미중관계는 코로나바이러스가 확산된 2020년에 접어들면서 악화되었고, 2020년 6월 이후 본격적인 미국 대선시즌이 시작되면서 더욱 악화되는 모습을 보여주었다.
#### 한일관계 평화지수
![image02](https://user-images.githubusercontent.com/50201956/128863872-be0189ce-b5e4-41bb-a1a0-08db472d3fe2.png)
* 한일 평화지수(검정색), 일본의 한국 외 국가와의 평화지수(파랑색), 한국의 일본 외 국가와의 평화지수(붉은색)
* 2019년 7월 1일 일본은 한국을 상대로 반도체 부품 관련 수출을 규제하였고, 평화지수에서도 2019년 7월의 한일 평화지수가 가장 낮은 수치를 보였다.
* 2020년 5월 19일 일본 외무성의 외교청서에 "한국은 중요한 이웃나라"라는 문구가 3년만에 다시 등장, 평화지수에서도 해당 시기에 관계 호전의 가능성을 보였다.
* 2020년 6월 2일 산업통상자원부가 일본의 수출규제에 대한 세계무역기구 제소를 재실시 하였고, 해당 기간 평화지수 또한 급속도록 하락하는 모습을 보였다.

### 웹 어플리케이션
* SpringBoot와 Thymeleaf를 이용하여 레이아웃과 화면을 작성하였고 Form을 전송 기능을 구현합니다. 
* JPA(hibernate)를 이용하여 게시판을 조회, RESTful Api를 작성하고 페이지를 검색합니다. 
* [Spring Boot으로 웹 출시까지][coding-god]에 큰 도움을 받았습니다.

## 환경 및 세팅
* openjdk version "1.8.0_302"
* Spring Boot 2.5.3/ Maven Project/ Dependencies: Spring Boot DevTools, Lombok, Spring Web, Thymeleaf, Spring Data JPA
* MariaDB 10.6.3
* 보다 자세한 환경은 pom.xml를 확인해주세요.

## 프로젝트 실행: `java/~/PeaceindexApplication.java`
* src/main/java/com.diplomacy.peaceindex/PeaceindexApplication.java를 실행시키시면 됩니다.

## 코드 및 기능
* 각 패키지 별로 각 기능에 필요한 코드들에 대한 설명을 덧붙였습니다.
* 패키지와 구현된 기능들을 다음과 같습니다.
### 패키지: 템플릿, 컨트롤러, 모델, 레포지토리, 서비스, 벨리데이터, 컨피그
#### 기능: 타임리프로 화면 작성, 타임리프로 레이아웃 만들기, JPA로 게시판 조회, Form 전송하기, 벨리데이션, JPA로 API

### 템플릿: `resources/templates/~`
* Thymeleaf는 템플릿 엔진으로, resources/templates에 html 파일을 이용해 웹페이지를 만든다.
* html파일의 html 태그에 `xmlns:th="http://www.thymeleaf.org"` 속성을 추가한다.
#### 타임리프로 화면 작성: `index.html`, `fragments/common.html`
* `index.html`
  * `"th:replace="fragments/common :: menu('home')"` 속성은 fragments.common.html 에서 `th:fragment="menu"` 속성이 있는 태그를 찾아서 대체한다.
* `fragments/common.html`
  * `th:text="${name}"` `th:text`는 태그 내부 텍스트에 대한 속성이며 모델 안의 키값에 해당하는 `${key}`으로 모델의 해당 키의 밸류값을 받는다.
  * `th:classappend="${menu} == 'home'? 'active'"` menu의 밸류가 'home'이면 class에 active 속성을 추가한다.
#### 타임리프로 레이아웃 만들기: `board/list.html`, `fragments/common.html`
* `board/list.html`
  * `index.html`을 이용하여 `list.html`를 생성한다. 생성 시 `fragments/common.html` 네임스페이스를 추가한다.
  * `"th:replace="fragments/common :: head('게시판')"`으로 헤드 태그를 대체한다.
* `fragments/common.html`
  * `th:classappend="${menu} == 'board'? 'active'"`으로 조건부로 active 속성을 추가한다.
  * `th:if="${menu}=='home'`과 `th:if="${menu}=='board'`속성으로 활성화된 메뉴일때만 current 지시자가 화면에 표시되도록 한다. 
  * `th:href="@{/}`과 `th:href="@{/board/list}`로 링크를 연결해준다.
#### JPA로 게시판 조회: `board/list.html`
* `th:text="${#lists.size(boards)}` 전달 받은 boards 변수(List)의 크기를 알려주는 size 문법을 사용한다. 
* `th:each="board : ${boards}"` boards 변수의 각 값을 boards로 변수로 선언하여 받는다.
#### Form 전송하기: `board/list.html` `board/form.html`
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
#### 밸리데이션: `board/form.html`
* `th:classappend=${#fields.hasErrors('title')} ? 'is-invalid'` title과 관련된 이름으로 발생된 에러가 fields에 존재하는지 확인하고 is-invalid 속성 추가
* `th:errors="*{title}"` title 관련 에러 관련된 태크 속성

### 컨트롤러: `java/~/controller/~`
#### 타임리프로 화면 작성: `HomeController`
* `@Controller`로 어노테이션으로 컨트롤러임을 알려준다.
* `@GetMapping("/url")` 어노테이션으로 url의 GET 요청을 받는다.
* 문자열을 리턴하면 templates의 해당 html 파일을 화면에 보여준다.
#### 타임리프로 레이아웃 만들기: `BoardController`
* `@RequestMapping("/board")`와 `@GetMapping("/list")`로 `"/board/list"` 경로 지정
* `@RequestParam` 어노테이션으로 url/?의 파라매터 값을 받을 수 있다.
* 파라매터로 Model를 정의하면 Model 안에 키-밸류값을 넣을 수 있다.
#### JPA로 게시판 조회: `BoardController`
* 게시판 호출을 위한 데이터 값을 넘겨 받기 위하여 파라매터로 `Model model`을 추가한다.
* `BoardRepository`를 `private boardRepository`으로 선언하고 `@Autowired`로 DI한다.
* 이후 list 메소드에서 `List<Boards> boards = boardRepository.findAll();` 로 데이터를 받고, `addAttribute("boards", boards);`로 "boards"를 키 값으로 `model`에 전달한다. 이제 모델에 담긴 데이터를 타임리프에서 사용할 수 있다.
#### Form 전송하기: `BoardController`
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
#### 밸리데이션: `BoardController @PostMapping("/form")`
* `@ModelAttribute`를 `@Valid`로 바꾸어 주고 BindingResult 클래스를 파라매터로 준다. 
* `BindingResult.hasErrors()`로 `Board`에서 `@NotNull` `@Size` 어노테이션으로 제한한 사항이 맞는지 체크한다.
  * 에러가 나면 리다이렉트 하지 않고 `@GetMapping("/form")`으로 연결한다.
* 밸리데이터 사용 시
  * `@AutoWired` 어노테이션을 선언하여 DI하고, boardValidator 선언한다.
  * `boardRepository.save` 전에 `boardValidator.validate` 메소드에 board와 bindingResult를 파라매터를 건내어 확인한다.
#### JPA로 API: `BoardApiController`
* CRUD: `@PostMapping` `@GetMapping`, `@PutMapping, `@DeleteMapping`

### 모델: `java/~/model/~` 
#### JPA로 게시판 조회: `Board`
* Board 클래스에서 데이터베이스에서 정의해둔 필드들을 `private`으로 클래스의 멤버 변수로 정의한다. 
* lombok의 `@Data` 어노테이션으로 게터/세터를 만들어 클래스의 멤버 변수들을 외부에서 사용할 수 있다. 
* 데이터베이스 연동을 위한 모델 클래스임을 알려주기 위하여 `@Entity` 어노테이션을 추가한다. 컨트롤러에서 Model를 파라매터에서 불러오면 그 때 속성에 넣어줄 수 있다.
* primary key인 id위에는 `Id` 어노테이션을 추가하고, 자동 증가를 위하여 `@GenerateValue(strategy=GenerationType.IDENTITY)` 를 선언한다. 
* `IDENTITY` 말고 `SEQUENCE`를 사용하면 성능이 보다 좋지만 추가 작업이 필요한다.
#### 밸리데이션: `Board`
* `@NotNull` `@Size` 어노테이션으로 form에서 들어오는 값을 확인한다.
* `@Size`에 최대 최소 길이, 오류 시 출력한 메세지를 지정할 수 있다.

### 레포지토리: `java/~/repository/~`
#### JPA로 게시판 조회: `BoardRepository`
* `BoardRepository`: `JpaRepository<Board, Long>`를 상속받아서 레포지토리를 만든다. 앞서 생성한 `Board` 모델 클래스를 불러와준다.
* 메소드들의 이름을 규칙에 따라서 필드명을 포함시켜 만들면, 인터페이스를 구현할 필요없이 데이터를 가져올 수 있다.
* 컨트롤러에서 레포지토리로 만든 Board 모델의 리스트를 속성에 넣어줄 수 있다. 
#### JPA로 API:
* 이미 만들어둔 `BoardRepository`를 사용한다.

### 서비스: `java/~/service/~`

### 밸리데이터: `java/~/validator/~`
#### 밸리데이션: `BoardValidator`
* `Board` 클래스의 어노테이션을 통한 구현 이상을 원할 때, 커스텀 클래스를 만든다.
* `Validator`를 상속 받아 구현해야할 메소드들을 구현한다. Spring Boot Validator를 참고하여 형식을 맞춰 준다.
* `validate`메소드
  * obj 인풋을 Board로 형변환을 해준 후 필요한 에러를 체크해준다. 
  * DI를 위하여 `@Component`로 등록해준다. 


[coding-god]: https://www.youtube.com/watch?v=FYkn9KOfkx0&list=PLPtc9qD1979DG675XufGs0-gBeb2mrona
