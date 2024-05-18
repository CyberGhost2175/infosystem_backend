package com.example.JSnote.dtos;
import lombok.Data;
@Data
public class CommentDto {
    private Long id;
    private String content;
    private Long userId;
}
