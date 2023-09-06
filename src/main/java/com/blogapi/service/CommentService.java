package com.blogapi.service;

import com.blogapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(long postId, long commentId);

    void deleteCommentById(long postId, long commentId);

    CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto);
}
