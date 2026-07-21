package com.nap.tomopet.domain.community.controller;

import com.nap.tomopet.domain.community.dto.PostCreateRequestDto;
import com.nap.tomopet.domain.community.dto.PostResponseDto;
import com.nap.tomopet.domain.community.dto.PostUpdateRequestDto;
import com.nap.tomopet.domain.community.service.PostService;
import com.nap.tomopet.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // TODO: 인증 연동 전 임시 구조 - SecurityContext에 인증된 유저 정보를 담는 방식(예: CustomUserDetails)이
    // 아직 없어 userId를 요청 파라미터로 받는다. JwtAuthenticationFilter가 SecurityConfig에 등록되고
    // CustomUserDetailsService가 User의 id를 노출하도록 정비되면 이 부분을 인증 정보 기반으로 교체해야 한다.
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createPost(@RequestParam Long userId,
                                                         @RequestBody PostCreateRequestDto requestDto) {
        Long postId = postService.createPost(userId, requestDto);
        return ResponseEntity.ok(ApiResponse.success(postId));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPost(@PathVariable Long postId) {
        PostResponseDto response = postService.getPost(postId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PostResponseDto>>> getAllPosts(Pageable pageable) {
        Page<PostResponseDto> response = postService.getAllPosts(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // TODO: 인증 연동 전 임시 구조 - 위 createPost와 동일한 이유로 userId를 요청 파라미터로 받는다.
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(@PathVariable Long postId,
                                                         @RequestParam Long userId,
                                                         @RequestBody PostUpdateRequestDto requestDto) {
        postService.updatePost(postId, userId, requestDto);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // TODO: 인증 연동 전 임시 구조 - 위 createPost와 동일한 이유로 userId를 요청 파라미터로 받는다.
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId,
                                                         @RequestParam Long userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
