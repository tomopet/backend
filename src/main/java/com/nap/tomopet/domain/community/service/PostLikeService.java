package com.nap.tomopet.domain.community.service;

import com.nap.tomopet.domain.community.dto.PostLikeResponseDto;
import com.nap.tomopet.domain.community.entity.Post;
import com.nap.tomopet.domain.community.entity.PostLike;
import com.nap.tomopet.domain.community.repository.PostLikeRepository;
import com.nap.tomopet.domain.community.repository.PostRepository;
import com.nap.tomopet.domain.user.entity.User;
import com.nap.tomopet.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostLikeResponseDto toggleLike(Long postId, Long userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return postLikeRepository.findByPost_IdAndUserId_Id(postId, userId)
                .map(postLike -> {
                    postLikeRepository.delete(postLike);
                    post.decreaseLikeCount();
                    return PostLikeResponseDto.of(false, post.getLikeCount());
                })
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

                    PostLike postLike = PostLike.builder()
                            .post(post)
                            .userId(user)
                            .build();

                    postLikeRepository.save(postLike);
                    post.increaseLikeCount();
                    return PostLikeResponseDto.of(true, post.getLikeCount());
                });
    }
}
