package net.datasa.test.repository;

import net.datasa.test.domain.entity.BoardEntity;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시판 관련 repository
 */

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

	List<BoardEntity> findByCategory(String category, Sort sort);

	List<BoardEntity> findByTitleContaining(String keyword, Sort sort);

	List<BoardEntity> findByCategoryAndTitleContaining(String category, String keyword, Sort sort);




}
