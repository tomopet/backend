package com.nap.tomopet.domain.community.controller;

import com.nap.tomopet.domain.community.dto.CommentCreateRequestDto;
import com.nap.tomopet.domain.community.dto.CommentResponseDto;
import com.nap.tomopet.domain.community.service.CommentService;
import com.nap.tomopet.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // TODO: 인증 연동 전 임시 구조 - PostController와 동일한 이유로 userId를 요청 파라미터로 받는다.
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createComment(@PathVariable Long postId,
                                                            @RequestParam Long userId,
                                                            @RequestBody CommentCreateRequestDto requestDto) {
        Long commentId = commentService.createComment(postId, userId, requestDto);
        return ResponseEntity.ok(ApiResponse.success(commentId));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getComments(@PathVariable Long postId) {
        List<CommentResponseDto> response = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // TODO: 인증 연동 전 임시 구조 - PostController와 동일한 이유로 userId를 요청 파라미터로 받는다.
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long postId,
                                                            @PathVariable Long commentId,
                                                            @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
