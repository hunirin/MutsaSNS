package com.example.project_2_leegwanghun.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "comment")
@SQLDelete(sql = "UPDATE comment SET deleted_at = true WHERE comment_id = ?")
@Where(clause = "deleted_at=false")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    // 작성자 정보 저장용
    private String username;

    private boolean deletedAt= Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity articles;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
