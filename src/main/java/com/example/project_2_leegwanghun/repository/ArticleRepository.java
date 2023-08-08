package com.example.project_2_leegwanghun.repository;

import com.example.project_2_leegwanghun.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Page<ArticleEntity> findByUserIdIn(List<Long> followingIds, Pageable pageable);

}
