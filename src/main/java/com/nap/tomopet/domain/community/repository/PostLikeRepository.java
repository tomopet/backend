package com.nap.tomopet.domain.community.repository;

import com.nap.tomopet.domain.community.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
