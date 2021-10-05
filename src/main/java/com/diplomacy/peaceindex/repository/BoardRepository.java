package com.diplomacy.peaceindex.repository;

import com.diplomacy.peaceindex.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 메소드들의 이름을 규칙에 따라서 필드명을 포함시켜 만들면, 인터페이스를 구현할 필요없이 데이터를 가져올 수 있다.
    // 컨트롤러에서 레포지토리로 만든 Board 모델의 리스트를 속성에 넣어줄 수 있다.
    List<Board> findByTitle(String title);
    // 여러 조건으로 검색하는 메소드를 구현할 수 있다. Spring Data JPA의 Query Creation 참고.
    List<Board> findByTitleOrContent(String title, String content);
    // Containing키워드를 이용하면 문자열이 포함된 데이터를 가져온다.
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}
