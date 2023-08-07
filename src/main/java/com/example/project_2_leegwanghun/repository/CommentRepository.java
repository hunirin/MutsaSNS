package com.example.project_2_leegwanghun.repository;

import com.example.project_2_leegwanghun.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
