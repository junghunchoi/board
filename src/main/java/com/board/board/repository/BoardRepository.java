package com.board.board.repository;

import com.board.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Integer> {
    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
}
