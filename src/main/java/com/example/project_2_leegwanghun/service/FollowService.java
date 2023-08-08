package com.example.project_2_leegwanghun.service;

import com.example.project_2_leegwanghun.dto.ArticleDto;
import com.example.project_2_leegwanghun.entity.ArticleEntity;
import com.example.project_2_leegwanghun.entity.FollowEntity;
import com.example.project_2_leegwanghun.entity.UserEntity;
import com.example.project_2_leegwanghun.repository.ArticleRepository;
import com.example.project_2_leegwanghun.repository.FollowRepository;
import com.example.project_2_leegwanghun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;


    // 팔로우
    public void followUser(Long followingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        FollowEntity follow = new FollowEntity(followingId, user);
        followRepository.save(follow);
    }

    // 언팔로우
    public void unFollowUser(Long followingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        FollowEntity follow = followRepository.findByFollowingIdAndFollower(followingId, user);
        followRepository.delete(follow);
    }

    // 팔로우한 사용자 피드 조회
    public Page<ArticleDto> readFollowingArticle(Integer pageNum, Integer pageSize) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // followingId 불러오기
        List<Long> followingIds = followRepository.findFollowingIdsByFollower(user);

        Pageable pageable = PageRequest.of(
                pageNum, pageSize, Sort.by("id").ascending());

        Page<ArticleEntity> articleEntityPage = articleRepository.findByUserIdIn(followingIds, pageable);

        return articleEntityPage.map(ArticleDto::fromEntity);
    }
}
