package com.example.JSnote.controllers;

import com.example.JSnote.dtos.CommentDto;
import com.example.JSnote.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create-comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        System.out.println("Received create-comment request: " + commentDto); // Debug: вывод входных данных
        if (commentDto.getUserId() == null ) {
            System.out.println("Invalid request: userId or articleId is null"); // Debug: сообщение об ошибке
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(commentService.saveComment(commentDto));
    }

    @GetMapping("/comments")
    public List<CommentDto> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        Optional<CommentDto> comment = commentService.getCommentById(id);
        return comment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(id, commentDto));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
