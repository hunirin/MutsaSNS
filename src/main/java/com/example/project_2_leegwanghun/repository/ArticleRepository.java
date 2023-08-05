package com.example.project_2_leegwanghun.repository;

import com.example.project_2_leegwanghun.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
}
