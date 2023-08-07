package com.example.project_2_leegwanghun.repository;

import com.example.project_2_leegwanghun.entity.ArticleEntity;
import com.example.project_2_leegwanghun.entity.HeartEntity;
import com.example.project_2_leegwanghun.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<HeartEntity, Long> {
    HeartEntity findByUserAndArticles(UserEntity user, ArticleEntity articles);
}
