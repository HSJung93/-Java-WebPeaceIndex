package com.diplomacy.peaceindex.repository;

import com.diplomacy.peaceindex.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);
    //Containing키워드를 이용하면 문자열이 포함된 데이터를 가져온다.
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}
