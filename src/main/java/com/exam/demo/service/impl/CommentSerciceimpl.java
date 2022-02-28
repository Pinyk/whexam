package com.exam.demo.service.impl;

import com.exam.demo.entity.Comment;
import com.exam.demo.mapper.CommentMapper;
import com.exam.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class CommentSerciceimpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public List<Comment> findByBlogId(Integer blogid) {
        List<Comment> byBlogId = commentMapper.findByBlogId(blogid);
        Collections.sort(byBlogId, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                if(o1.getDate().getTime()-o1.getDate().getTime()==0)
                    return 0;
                return o1.getDate().getTime()-o1.getDate().getTime()>0?1:-1;
            }
        });
        return byBlogId;
    }

    @Override
    public Integer deleteComment(Integer commentid) {
        return commentMapper.deleteComment(commentid);

    }

    @Override
    public Comment saveComment(Comment comment) {
        comment.setDate(new Date());
        commentMapper.saveComment(comment);
        return  comment;
    }
}
