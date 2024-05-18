package com.example.JSnote.services;

import com.example.JSnote.dtos.CommentDto;
import com.example.JSnote.entites.Comment;

import java.util.List;
import java.util.Optional;

import com.example.JSnote.repositories.ArticleRepository;
import com.example.JSnote.repositories.CommentRepository;
import com.example.JSnote.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public CommentDto saveComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setUser(userRepository.findById(commentDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));

        Comment savedComment = commentRepository.save(comment);
        return toDto(savedComment);
    }

    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<CommentDto> getCommentById(Long id) {
        return commentRepository.findById(id).map(this::toDto);
    }

    public CommentDto updateComment(Long id, CommentDto commentDto) {
        if (id == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(commentDto.getContent());


        Comment updatedComment = commentRepository.save(comment);
        return toDto(updatedComment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    private CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUserId(comment.getUser().getId());
        return dto;
    }
}