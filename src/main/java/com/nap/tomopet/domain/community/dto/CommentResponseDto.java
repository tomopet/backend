package com.nap.tomopet.domain.community.dto;

import com.nap.tomopet.domain.community.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final Long postId;
    private final String userId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private CommentResponseDto(Long id, Long postId, String userId, String content,
                                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getPost().getId(),
                comment.getUserId().getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
