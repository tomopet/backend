package com.nap.tomopet.domain.community.dto;

import com.nap.tomopet.domain.community.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String userId;
    private final String title;
    private final String content;
    private final String imageUrl;
    private final int viewCount;
    private final int likeCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private PostResponseDto(Long id, String userId, String title, String content, String imageUrl,
                             int viewCount, int likeCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getUserId().getNickname(),
                post.getTitle(),
                post.getContent(),
                post.getImageUrl(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
