package com.exam.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "comment")
public class Comment {
    @ApiModelProperty(value = "评论主键")
    private  Integer commentid;
    @ApiModelProperty(value = "评论内容")
    private  String  context;
    @ApiModelProperty(value = "头像")
    private  String  image;
    @ApiModelProperty(value = "待评论文章主键")
    private  Integer blogid;
    @ApiModelProperty(value = "用户名")
    private  String  name;
    @ApiModelProperty(value = "评论日期")
    private Date date;
    @ApiModelProperty(value = "用户id")
    private  Integer id;

    public Comment(String context, String image, Integer blogid, String name, Date date, Integer id) {
        this.context = context;
        this.image = image;
        this.blogid = blogid;
        this.name = name;
        this.date = date;
        this.id = id;
    }

    public Comment() {
    }
}
