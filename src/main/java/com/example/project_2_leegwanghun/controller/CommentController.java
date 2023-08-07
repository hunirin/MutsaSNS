package com.example.project_2_leegwanghun.controller;

import com.example.project_2_leegwanghun.dto.CommentDto;
import com.example.project_2_leegwanghun.dto.ResponseDto;
import com.example.project_2_leegwanghun.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/article/{articleId}/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ResponseDto response = new ResponseDto();

    // 댓글 생성
    @PostMapping
    public ResponseDto create(
            @PathVariable("articleId") Long articleId,
            @RequestBody CommentDto comment,
            Authentication authentication
    ) {
        commentService.createComment(articleId, comment, authentication);
        response.setMessage("댓글이 등록되었습니다.");
        return response;
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseDto delete(
            @PathVariable Long articleId,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(articleId, commentId);
        response.setMessage("피드가 삭제되었습니다.");
        return response;
    }
}
