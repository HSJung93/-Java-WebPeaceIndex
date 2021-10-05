package com.diplomacy.peaceindex.controller;

import com.diplomacy.peaceindex.model.Board;
import com.diplomacy.peaceindex.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

// @Controller vs @RestController: HTTP Response Body가 생성되는 방식이 다르다. json 객체 변환
@RestController
@RequestMapping("/api")
class BoardApiController {


    @Autowired
    private BoardRepository repository;

    //  READ: `@GetMapping()`어노테이션과 `findAll();`  메소드를 사용한다.
    @GetMapping("/boards")
    // 제목(컨텐트)으로 검색하기 위해서는 `@RequestParam()`으로 title 파라매터를 추가한다.
    // 검색을 위한 메소드는 `@Autowired private`으로 선언해 둔 `BoardRepository`에서 만든다.
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                    @RequestParam(required = false, defaultValue = "") String content) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            return repository.findAll();
        } else {
            return repository.findByTitleOrContent(title, content);
        }
    }

    // CREATE: `@PostMapping`어노테이션과 `save();` 메소드를 사용한다.
    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    //  READ: `@GetMapping()`어노테이션과 single item의 경우 `findById();` 메소드를 사용한다.
    //  값이 없으면 에러가 발생하게 할 수도 있지만, `orElse(null)`로 null 값을 리턴하게 한다.
    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // UPDATE: `@PutMapping()`어노테이션을 사용하고 `findById();`로 확인한다.
    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                // 데이터베이스에 값이 존재하면 `board.setTitle()`, `board.setContent()`로 데이터베이스 저장한다.
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                // 존재하지 않으면 `orElseGet(()->{ newBoard.setId(id) })`로 id 지정 후 `save(newBoard)`로 저장한다.
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    // DELETE: `@DeleteMapping`어노테이션
    // `MethodSecurityConfig`에서 호출한 `@Secured("ROLE_ADMIN")`으로 ROLE_ADMIN 사용자만 delete 매핑을 호출할 수 있도록 한다.
    // ADMIN이 아닌 사용자가 버튼을 누르면 403 에러가 나타난다.
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}