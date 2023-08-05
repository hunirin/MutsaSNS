package com.example.project_2_leegwanghun.controller;


import com.example.project_2_leegwanghun.dto.ArticleDto;
import com.example.project_2_leegwanghun.dto.ResponseDto;
import com.example.project_2_leegwanghun.entity.UserEntity;
import com.example.project_2_leegwanghun.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final ResponseDto response = new ResponseDto();

    // 피드 생성
    @PostMapping
    public ResponseDto create(
            @RequestBody ArticleDto article,
            Authentication authentication
    ) {
        articleService.creatArticle(article, authentication);
        response.setMessage("피드가 등록되었습니다.");
        return response;
    }

    // 이미지 업로드
    @PostMapping("/{articleId}")
    public ResponseDto uploadImage(
            @PathVariable Long articleId,
            @RequestPart(value = "image") MultipartFile articleImage,
            Authentication authentication
    ) {
        articleService.uploadArticleImage(articleId, articleImage, authentication);
        response.setMessage("이미지가 등록되었습니다.");
        return response;
    }

    // 피드 목록 조회
    @GetMapping("/articleList")
    public Page<ArticleDto> readAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit
    ) {

        return articleService.readArticlePaged(page, limit);
    }



    // 피드 단독 조회
}
