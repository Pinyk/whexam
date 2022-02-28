package com.exam.demo.service;

import com.exam.demo.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findByBlogId(Integer blogid);
    Integer deleteComment(Integer commentid);
    Comment saveComment(Comment comment);
}
