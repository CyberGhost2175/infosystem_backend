package com.example.JSnote.dtos;
import lombok.Data;

@Data
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
}