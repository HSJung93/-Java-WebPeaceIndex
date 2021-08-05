package com.diplomacy.peaceindex.service;

import com.diplomacy.peaceindex.model.Board;
import com.diplomacy.peaceindex.model.User;
import com.diplomacy.peaceindex.repository.BoardRepository;
import com.diplomacy.peaceindex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board){
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
