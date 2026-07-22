package com.nap.tomopet.domain.community.service;

import com.nap.tomopet.domain.community.dto.PostCreateRequestDto;
import com.nap.tomopet.domain.community.dto.PostResponseDto;
import com.nap.tomopet.domain.community.dto.PostUpdateRequestDto;
import com.nap.tomopet.domain.community.entity.Post;
import com.nap.tomopet.domain.community.repository.PostRepository;
import com.nap.tomopet.domain.user.entity.User;
import com.nap.tomopet.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createPost(Long userId, PostCreateRequestDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Post post = Post.builder()
                .userId(user)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .imageUrl(requestDto.getImageUrl())
                .build();

        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public PostResponseDto getPost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        post.increaseViewCount();

        return PostResponseDto.from(post);
    }

    public Page<PostResponseDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResponseDto::from);
    }

    @Transactional
    public void updatePost(Long postId, Long userId, PostUpdateRequestDto requestDto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (!post.getUserId().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.");
        }

        post.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getImageUrl());
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (!post.getUserId().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }
}
