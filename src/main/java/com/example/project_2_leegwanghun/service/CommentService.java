package com.example.project_2_leegwanghun.service;

import com.example.project_2_leegwanghun.dto.CommentDto;
import com.example.project_2_leegwanghun.entity.ArticleEntity;
import com.example.project_2_leegwanghun.entity.CommentEntity;
import com.example.project_2_leegwanghun.entity.UserEntity;
import com.example.project_2_leegwanghun.repository.ArticleRepository;
import com.example.project_2_leegwanghun.repository.CommentRepository;
import com.example.project_2_leegwanghun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    // POST
    // 댓글 생성
    public void createComment(Long id, CommentDto commentDto, Authentication authentication) {
        Optional<ArticleEntity> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ArticleEntity articleEntity = optionalArticle.get();
        UserEntity userEntity = getUserEntity(authentication);
        commentRepository.save(commentDto.newEntity(userEntity, articleEntity));
    }

    // Delete
    // 댓글 삭제
    public void deleteComment(Long id, Long commentId) {
        Optional<ArticleEntity> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Optional<CommentEntity> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        commentRepository.deleteById(commentId);
    }

    // 유저 찾는 메소드
    private UserEntity getUserEntity(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
