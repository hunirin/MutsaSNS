package com.example.project_2_leegwanghun.controller;

import com.example.project_2_leegwanghun.dto.ArticleDto;
import com.example.project_2_leegwanghun.dto.ResponseDto;
import com.example.project_2_leegwanghun.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final ResponseDto response = new ResponseDto();

    // 팔로우
    @PostMapping("/follow/{followingId}")
    public ResponseDto follow(
            @PathVariable Long followingId
    ) {
        followService.followUser(followingId);
        response.setMessage("팔로우 하였습니다.");
        return response;
    }

    // 언팔로우
    @PostMapping("/unfollow/{followingId}")
    public ResponseDto unFollow(
            @PathVariable Long followingId
    ) {
        followService.unFollowUser(followingId);
        response.setMessage("언팔로우 하였습니다.");
        return response;
    }

    // 팔로잉한 유저 피드 목록 조회
    @GetMapping("/follow")
    public Page<ArticleDto> readFollowing(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit
    ) {
        return followService.readFollowingArticle(page, limit);
    }
}
