package com.example.JSnote.controllers;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.JSnote.dtos.ArticleDto;
import com.example.JSnote.entities.Article;
import com.example.JSnote.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping("/create-article")
    public ArticleDto  createArticle(@RequestBody ArticleDto articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        Article savedArticle = articleService.saveArticle(article);
        articleDto.setId(savedArticle.getId());
        return articleDto;
    }

    @GetMapping("/articles")
    public List<ArticleDto> getAllArticles() {
        return articleService.getAllArticles().stream()
                .map(article -> {
                    ArticleDto dto = new ArticleDto();
                    dto.setId(article.getId());
                    dto.setTitle(article.getTitle());
                    dto.setContent(article.getContent());
                    return dto;
                }).collect(Collectors.toList());
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleService.getArticleById(id);
        return article.map(value -> {
            ArticleDto dto = new ArticleDto();
            dto.setId(value.getId());
            dto.setTitle(value.getTitle());
            dto.setContent(value.getContent());
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/article/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable Long id, @RequestBody ArticleDto articleDTO) {
        Article article = new Article();
        article.setId(id);
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        Article updatedArticle = articleService.updateArticle(id, article);
        articleDTO.setId(updatedArticle.getId());
        return ResponseEntity.ok(articleDTO);
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}