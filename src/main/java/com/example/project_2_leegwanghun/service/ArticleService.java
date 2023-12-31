package com.example.project_2_leegwanghun.service;

import com.example.project_2_leegwanghun.dto.ArticleDto;
import com.example.project_2_leegwanghun.entity.ArticleEntity;
import com.example.project_2_leegwanghun.entity.ArticleImageEntity;
import com.example.project_2_leegwanghun.entity.UserEntity;
import com.example.project_2_leegwanghun.repository.ArticleImageRepository;
import com.example.project_2_leegwanghun.repository.ArticleRepository;
import com.example.project_2_leegwanghun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleImageRepository articleImageRepository;
    private final UserRepository userRepository;

    // POST
    // 피드 생성
    public void creatArticle(ArticleDto article, Authentication authentication) {
        UserEntity userEntity = getUserEntity(authentication);
        articleRepository.save(article.newEntity(userEntity));
    }

    // POST
    // 피드 이미지 등록
    public void uploadArticleImage(Long id, MultipartFile articleImage, Authentication authentication
    ) {
        UserEntity userEntity = getUserEntity(authentication);
        ArticleEntity articleEntity = getArticleById(id);

        // 폴더명에 articleId를 넣기 위해
        String articleId = String.valueOf(articleEntity.getId());

        // 저장되는 시간을 파일명으로
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String currentTimeStr = currentTime.format(formatter);


        // 업로드위치
        // media/{userId}/image
        String articleImageDir = String.format("media/article_%s/", articleId); // 폴더명
        try { // 읽고 쓰는데서 발생할 수 있는 예외 처리
            Files.createDirectories(Path.of(articleImageDir));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 확장자를 포함한 이미지 이름
        String originalFilename = articleImage.getOriginalFilename();
        String[] fileNameSplit = originalFilename.split("\\.");
        String extension = fileNameSplit[fileNameSplit.length - 1];
        String articleImageFilename = currentTimeStr + "." + extension;
        log.info(articleImageFilename);

        // 폴더와 이미지 이름을 포함한 파일 경로
        String articleImagePath = articleImageDir + articleImageFilename; // 파일 경로
        log.info(articleImagePath);

        // MultipartFile을 저장
        try {
            articleImage.transferTo(Path.of(articleImagePath));

            // 파일이 있는 위치를 imageUrl에 저장
            ArticleImageEntity articleImageEntity = new ArticleImageEntity();
            articleImageEntity.setImageUrl(articleImagePath);

            articleImageEntity.setArticles(articleEntity);

            articleImageRepository.save(articleImageEntity);

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    // GET
    // 목록 형태 조회
    // 20개씩 나누어 0번 페이지부터 요청
    public Page<ArticleDto> readArticlePaged(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(
                pageNum, pageSize, Sort.by("id").ascending());
        Page<ArticleEntity> articleEntityPage = articleRepository.findAll(pageable);

        return articleEntityPage.map(ArticleDto::fromEntity);
    }

    // GET
    // 피드 단독 조회
    public ArticleDto readArticleOne(Long id) {
        Optional<ArticleEntity> optionalArticle = articleRepository.findById(id);

        if (optionalArticle.isPresent()) {
            return ArticleDto.fromEntity2(optionalArticle.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // PUT
    // 피드 수정
    public ArticleDto updateArticle(Long id, ArticleDto articleDto) {
        Optional<ArticleEntity> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ArticleEntity articleEntity = optionalArticle.get();
        articleEntity.setTitle(articleDto.getTitle());
        articleEntity.setContent(articleDto.getContent());

        articleEntity = articleRepository.save(articleEntity);

        return ArticleDto.fromEntity2(articleEntity);
    }

    // 피드 이미지 추가/삭제
    public void updateArticleImage(Long id, MultipartFile articleImage, Long articleImageId) {

        Optional<ArticleEntity> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ArticleEntity articleEntity = optionalArticle.get();

        if (!(articleImage == null)) {
            // 이미지 추가
            // 폴더명에 articleId를 넣기 위해
            String articleId = String.valueOf(articleEntity.getId());

            // 저장되는 시간을 파일명으로
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String currentTimeStr = currentTime.format(formatter);


            // 업로드위치
            // media/{userId}/image
            String articleImageDir = String.format("media/article_%s/", articleId); // 폴더명
            try { // 읽고 쓰는데서 발생할 수 있는 예외 처리
                Files.createDirectories(Path.of(articleImageDir));
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // 확장자를 포함한 이미지 이름
            String originalFilename = articleImage.getOriginalFilename();
            String[] fileNameSplit = originalFilename.split("\\.");
            String extension = fileNameSplit[fileNameSplit.length - 1];
            String articleImageFilename = currentTimeStr + "." + extension;
            log.info(articleImageFilename);

            // 폴더와 이미지 이름을 포함한 파일 경로
            String articleImagePath = articleImageDir + articleImageFilename; // 파일 경로
            log.info(articleImagePath);

            // MultipartFile을 저장
            try {
                articleImage.transferTo(Path.of(articleImagePath));

                // 파일이 있는 위치를 imageUrl에 저장
                ArticleImageEntity articleImageEntity = new ArticleImageEntity();
                articleImageEntity.setImageUrl(articleImagePath);
                articleImageEntity.setArticles(articleEntity);

                articleImageRepository.save(articleImageEntity);

            } catch (IOException e) {
                log.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // 이미지 삭제
        if (!(articleImageId == null)) {
            Optional<ArticleImageEntity> optionalArticleImage = articleImageRepository.findById(articleImageId);
            if (optionalArticleImage.isPresent()) {
                ArticleImageEntity articleImageEntity = optionalArticleImage.get();
                articleImageRepository.delete(articleImageEntity);

                Path imagePath = Path.of(articleImageEntity.getImageUrl());
                try {
                    Files.delete(imagePath);
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    // 피드 삭제 (soft delete)
    // 실제로 삭제하지 않고 삭제 되었다는 표시만 남김
    public void deleteArticle(Long id) {
        Optional<ArticleEntity> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        articleRepository.deleteById(id);
    }




    // 유저 찾는 메소드
    private UserEntity getUserEntity(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // 피드 찾는 메소드
    public ArticleEntity getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
