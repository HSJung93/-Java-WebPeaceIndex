package com.diplomacy.peaceindex.repository;

import com.diplomacy.peaceindex.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
