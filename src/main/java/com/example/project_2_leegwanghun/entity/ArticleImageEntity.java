package com.example.project_2_leegwanghun.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
@Entity
@Table(name = "articleImg")
public class ArticleImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "article")
    private ArticleEntity articles;


}
