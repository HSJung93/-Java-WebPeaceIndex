package com.diplomacy.peaceindex.controller;

import com.diplomacy.peaceindex.model.Board;
import com.diplomacy.peaceindex.repository.BoardRepository;
import com.diplomacy.peaceindex.service.BoardService;
import com.diplomacy.peaceindex.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
public class BoardController {

    // @Autowired 어노테이션으로 Bean을 주입 받을 수 있다. : 필드 인젝션
    // vs @RequiredArgsConstructor 는 final이 선언된 필드를 인자값으로 하는 생성자를 만든다.: 컨스트럭터 인젝션
    // 레포지토리: Entity에 의해 생성된 DB에 접근하는 메서드(ex) findAll()) 들을 사용하기 위한 인터페이스
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    // 밸리데이터 사용 시 `@AutoWired` 어노테이션을 선언하여 DI하고, boardValidator 선언한다.
    @Autowired
    private BoardValidator boardValidator;

    // `@RequestParam` 어노테이션으로 url/?의 파라매터 값을 받을 수 있다.
    // 파라매터로 Model를 정의하면 Model 안에 키-밸류값을 넣을 수 있다.
    @GetMapping("/list")
    // `@PageableDefault(size=2)`로 size에 대한 기본값을 지정해줄 수 있다.
    // `@GetMapping("/list")`에 `pageable` 파라매터를 추가 했으므로 `?page=1&size2` 등으로 url을 통하여 page 변수(와 파라매터)를 전달할 수 있다.
    // `@GetMapping("/list")`에 list.html로부터 받아온 `String searchText`를 파라매터로 준다. 이 파라매터로 검색하는 기능을 `BoardRepository`에서 구현한다.
    // `@RequestParam(required=false, defaultValue="")`로 값이 입력되지 않았을 때에도 작동하도록 한다.
    public String list(Model model, @PageableDefault(size = 5) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        // 페이지 기능 구현 방법
        // 1. `List<Boards> boards = boardRepository.findAll();` 로 데이터를 받는다.
        // 2. Page<Boards>로 받고 페이지 숫자를 하드코딩한다.
        // page request 객체의 of 메소드를 사용해서 pagable 객체를 가져오면 된다.
        // return 타입은 list에서 page로 바뀌며, JPA에서 page는 0부터 시작한다. 쿼리 형태로 확인 가능하다.
//        Page<Board> boards = boardRepository.findAll(pageable);

        // 3. Pageable을 이용하여 범위를 정한다. `Pageable pageable`(springframework 패키지)을 파라매터로 받고, `findAll(pageable)`로 값을 지정한다.
        // 구현한 `findByTitleContainingOrContentContaining();`를 바탕으로 값을 boards에 넣는다.
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);

        // `boards.getPageable().getPageNumber()`로 현재 페이지 값을 가져온다.
        // `Math.max`과 `Math.min`을 사용하여, 1과 `boards.getTotalPages()` 사이의 startPage와 endPage를 구한다.
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);

        // `addAttribute("boards", boards);`로 "boards"를 키 값으로 `model`에 전달하면, 모델에 담긴 데이터를 타임리프에서 사용할 수 있다.
        // `model.addAttribute("startPage", startPage)`로 startPage변수를 넣고 같은 방법으로 endPage 변수도 넣는다.
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    // `@RequestParam(required=false) Long id` 어노테이션으로 `url/id`쿼리의 형태로 전달된 필수가 아닌 데이터를 받는다.
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null){
            // JPA로 게시판을 조회할 때처럼 모델 클래스를 받고 속성을 추가하는데, `new Board()`로 새로운 board 모델 클래스를 속성에 넣어준다.
            // id가 없으면, 기존대로 `new Board()`를 모델에 속성으로 전달한다.
            model.addAttribute("board", new Board());
        } else {
            //  id가 있으면, `boardRepository.findById(id);`로 데이터베이스를 조회하고 없으면 `.orElse(null)`로 null 값을 board에 넣어서 모델에 속성으로 전달한다.
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }

        return "board/form";
    }

    @PostMapping("/form")
    // `@ModelAttribute Board board`로 인풋 값으로 생성해둔 board 모델 클래스를 전달받는다.
    // -> 밸리데이션 기능 수정시 @Valid로 수정하고 BindingResult 클래스를 파라매터로 준다.
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        // `boardRepository.save` 전에 `boardValidator.validate` 메소드에 board와 bindingResult를 파라매터를 건내어 확인한다.
        boardValidator.validate(board, bindingResult);

        // `BindingResult.hasErrors()`로 `Board`에서 `@NotNull` `@Size` 어노테이션으로 제한한 사항이 맞는지 체크한다.
        if(bindingResult.hasErrors()){
            // 에러가 나면 리다이렉트 하지 않고 `@GetMapping("/form")`으로 연결한다.
            return "board/form";
        }

        // `board.setUser(user);`로 board에 유저값을 담으면 유저의 키값을 참조해서 user id값이 담길 것이지만 보안이 문제가 된다.
        // 스프링 시큐리티에서 제공하는 `Authentication`을 파라미터로 받고 `.getName()`을 통하여 username 값을 받아서 처리한다.
        String username = authentication.getName();

//        Authentication a = SecurityContextHolder.getContext().getAuthentication() 컨트롤러 외에 서비스 등 스프링에서 관리 해주는 클래스에서 인증 정보를 가져올 때 사용하는 전역 변수

        // `save(board)`로 전달받은 board 모델 클래스를 저장한다. 이때 id 값이 있느냐에 따라서 자동으로 INSERT 혹은 UPDATE를 해준다.
//        boardRepository.save(board);
        // `BoardService`에 board와 username을 저장해 서비스에서 저장할 수 있도록 수정한다.
        boardService.save(username, board);



        // 완료 시 이동할 페이지는 list인데, `return "redirect:/board/list";`로 redirect 키워드를 주면 list로 리다이렉트가 되면서 화면이 이동된다.
        // 원래 `@GetMapping("/list")`컨트롤러에서는 모델에 전달할 키 값 `"boards"`가 있고 값을 뿌려줘야 했다. 하지만 `@PostMapping("/form")`에서는 값을 뿌려주지 않고 `"board/list"`로 바로 이동하면 된다.
        // 그런데 시스템에 변화가 생기는 요청(로그인, 회원가입, 글쓰기)의 경우 포워드가 아닌, 리다이렉트를 사용한다.
        return "redirect:/board/list";
    }






}