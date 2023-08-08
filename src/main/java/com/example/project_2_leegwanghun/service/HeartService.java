package com.example.project_2_leegwanghun.service;


import com.example.project_2_leegwanghun.entity.ArticleEntity;
import com.example.project_2_leegwanghun.entity.HeartEntity;
import com.example.project_2_leegwanghun.entity.UserEntity;
import com.example.project_2_leegwanghun.repository.ArticleRepository;
import com.example.project_2_leegwanghun.repository.HeartRepository;
import com.example.project_2_leegwanghun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public void insertHeart(Long articleId) {
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        // 피드 작성자와 로그인 유저가 다르면 좋아요 가능
        if (article.getUser() != user) {
            if (heartRepository.findByUserAndArticles(user, article) == null) {
                HeartEntity heartEntity = new HeartEntity(user, article);
                heartRepository.save(heartEntity);
            } else {
                // 이미 좋아요 되어있으면 좋아요 취소
                HeartEntity heartEntity = heartRepository.findByUserAndArticles(user, article);
                heartEntity.unHeartEntity();
                heartRepository.delete(heartEntity);
            }
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }
}

