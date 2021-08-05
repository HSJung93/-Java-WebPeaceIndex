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

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 5) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        // 하드코딩: page request 객체의 of 메소드를 사용해서 pagable 객체를 가져온다. return 타입이 list에서 page로 jpa에서는 0부터 시작
        // pageable이라는 변수를 param으로 전달하고고 request파람으로 전달하는 방식으로 변화. 쿼리의 형태로 확인해볼 수 있음.
//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null){
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }



        return "board/form";
    }

    @PostMapping("/form")
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        String username = authentication.getName();
//        Authentication a = SecurityContextHolder.getContext().getAuthentication() 컨트롤러 외에 서비스 등 스프링에서 관리 해주는 클래스에서 인증 정보를 가져올 때 사용하는 전역 변수
        boardService.save(username, board); // 서비스에서 저장하도록
//        boardRepository.save(board);

        return "redirect:/board/list";
    }






}