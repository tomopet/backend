package com.nap.tomopet.domain.community.repository;

import com.nap.tomopet.domain.community.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByPost_IdAndUserId_Id(Long postId, Long userId);
}
