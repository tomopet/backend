package com.nap.tomopet.domain.community.repository;

import com.nap.tomopet.domain.community.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost_Id(Long postId);
}
