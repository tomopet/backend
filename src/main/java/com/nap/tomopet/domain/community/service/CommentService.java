package com.nap.tomopet.domain.community.service;

import com.nap.tomopet.domain.community.dto.CommentCreateRequestDto;
import com.nap.tomopet.domain.community.dto.CommentResponseDto;
import com.nap.tomopet.domain.community.entity.Comment;
import com.nap.tomopet.domain.community.entity.Post;
import com.nap.tomopet.domain.community.repository.CommentRepository;
import com.nap.tomopet.domain.community.repository.PostRepository;
import com.nap.tomopet.domain.user.entity.User;
import com.nap.tomopet.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createComment(Long postId, Long userId, CommentCreateRequestDto requestDto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Comment comment = Comment.builder()
                .post(post)
                .userId(user)
                .content(requestDto.getContent())
                .build();

        commentRepository.save(comment);
        return comment.getId();
    }

    public List<CommentResponseDto> getCommentsByPost(Long postId) {

        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }

        return commentRepository.findAllByPost_Id(postId).stream()
                .map(CommentResponseDto::from)
                .toList();
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if (!comment.getUserId().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}
