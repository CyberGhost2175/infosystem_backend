package com.example.JSnote.services;

import com.example.JSnote.entities.Article;
import com.example.JSnote.repositories.ArticleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        for (Article article : articles) {
            Hibernate.initialize(article.getUser()); // Инициализация пользователя
        }
        return articles;
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article updateArticle(Long id, Article article) {
        if (articleRepository.existsById(id)) {
            article.setId(id);
            return articleRepository.save(article);
        } else {
            throw new RuntimeException("Article not found");
        }
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}