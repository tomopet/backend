package com.nap.tomopet.domain.community.repository;

import com.nap.tomopet.domain.community.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
