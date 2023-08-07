package com.example.project_2_leegwanghun.entity;


import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "articleImg")
public class ArticleImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity articles;


}
