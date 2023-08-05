package com.example.project_2_leegwanghun.repository;

import com.example.project_2_leegwanghun.entity.ArticleEntity;
import com.example.project_2_leegwanghun.entity.ArticleImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleImageRepository extends JpaRepository<ArticleImageEntity, Long> {
}
