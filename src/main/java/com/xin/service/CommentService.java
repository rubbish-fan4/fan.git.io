package com.xin.service;

import com.xin.pojo.Comment;

import java.util.List;

public interface CommentService {

    //通过博客id查询评论
    List<Comment> listCommentByBlogId(Long blogId);
    //保存留言
    Comment saveComment(Comment comment);
}
