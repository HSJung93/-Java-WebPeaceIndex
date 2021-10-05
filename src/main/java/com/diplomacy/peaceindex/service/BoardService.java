package com.diplomacy.peaceindex.service;

import com.diplomacy.peaceindex.model.Board;
import com.diplomacy.peaceindex.model.User;
import com.diplomacy.peaceindex.repository.BoardRepository;
import com.diplomacy.peaceindex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// BoardService는 BoardController의 `@PostMapping`에서 사용한다.
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    // 컨트롤러에서 보내준 username과 board를 바탕으로, 레포지토리에서 정의한 `findByUsername` 메소드를 사용하여 user 클래스를 받는다.
    public Board save(String username, Board board){
        User user = userRepository.findByUsername(username);
        // board 안에 user에 세팅한 후 `boardRepository`에 저장한 값을 리턴한다.
        board.setUser(user);
        return boardRepository.save(board);
    }
}
