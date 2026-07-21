package com.nap.tomopet.domain.community.controller;

import com.nap.tomopet.domain.community.dto.PostLikeResponseDto;
import com.nap.tomopet.domain.community.service.PostLikeService;
import com.nap.tomopet.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/like")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    // TODO: 인증 연동 전 임시 구조 - PostController와 동일한 이유로 userId를 요청 파라미터로 받는다.
    @PostMapping
    public ResponseEntity<ApiResponse<PostLikeResponseDto>> toggleLike(@PathVariable Long postId,
                                                                        @RequestParam Long userId) {
        PostLikeResponseDto response = postLikeService.toggleLike(postId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
