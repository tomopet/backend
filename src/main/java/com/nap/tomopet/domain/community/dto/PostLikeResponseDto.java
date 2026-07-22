package com.nap.tomopet.domain.community.dto;

import lombok.Getter;

@Getter
public class PostLikeResponseDto {

    private final boolean liked;
    private final int likeCount;

    private PostLikeResponseDto(boolean liked, int likeCount) {
        this.liked = liked;
        this.likeCount = likeCount;
    }

    public static PostLikeResponseDto of(boolean liked, int likeCount) {
        return new PostLikeResponseDto(liked, likeCount);
    }
}
