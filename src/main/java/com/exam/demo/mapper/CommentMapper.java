package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> findByBlogId(Integer blogid);
    int saveComment(Comment comment);
    int deleteComment(Integer commentid);
}
