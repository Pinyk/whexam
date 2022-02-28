package com.exam.demo.controller;

import com.exam.demo.entity.Comment;
import com.exam.demo.service.CommentService;
import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;

@RestController
@RequestMapping("comment")
@Api(value="/comment",tags={"论坛评论接口"})
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping("save")
    @ApiOperation(notes = "liu",value = "发布评论接口")
    public WebResult<Comment> saveComment(@RequestBody @ApiParam(name="comment",required=true) Comment comment){
        return  WebResult.<Comment>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(commentService.saveComment(comment))
                .build();
    }
    @PostMapping("find")
    @ApiOperation(notes = "liu",value = "根据带评论文章查询已有评论接口")
    public WebResult<List<Comment>> findAll(@ApiParam(name="blogid",required = true) Integer blogid, Model model) {
        List<Comment> comments=commentService.findByBlogId(blogid);
        model.addAttribute("comments",comments);
        return WebResult.<List<Comment>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(commentService.findByBlogId(blogid))
                .build();
    }
    @PostMapping("delete")
    @ApiOperation(notes = "liu",value = "根据评论主键删除评论接口")
    public WebResult<Integer> saveComment(@ApiParam(name="commentid",required = true) Integer commentid){
        return  WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(commentService.deleteComment(commentid))
                .build();
    }


}
