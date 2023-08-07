package com.example.project_2_leegwanghun.dto;

import com.example.project_2_leegwanghun.entity.ArticleEntity;
import com.example.project_2_leegwanghun.entity.CommentEntity;
import com.example.project_2_leegwanghun.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CommentDto {
    private Long commentId;

    private String content;

    @JsonIgnore
    private boolean deletedAt;

    // 댓글 작성자 저장
    private String username;

    // 댓글 생성용
    public CommentEntity newEntity(UserEntity user, ArticleEntity article) {
        CommentEntity entity = new CommentEntity();
//        entity.setArticle(article);
        entity.setContent(content);
        entity.setUser(user);
        entity.setArticles(article);
        return entity;
    }

    public static CommentDto fromEntity(CommentEntity entity) {
        CommentDto dto = new CommentDto();

        dto.setCommentId(entity.getCommentId());
        dto.setContent(entity.getContent());
        dto.setUsername(entity.getUser().getUsername());
        return dto;
    }
}
