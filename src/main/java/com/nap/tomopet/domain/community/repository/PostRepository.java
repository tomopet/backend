package com.nap.tomopet.domain.community.repository;

import com.nap.tomopet.domain.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
