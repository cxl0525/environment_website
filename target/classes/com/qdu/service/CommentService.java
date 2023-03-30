package com.qdu.service;

import com.github.pagehelper.Page;
import com.qdu.pojo.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> queryCommentListByActivityId(int actId);
    int addCommentNotOne(Comment comment);//添加非一级评论
    int addCommentActivity(Comment comment);//同时更新活动评论表
    int addCommentOne(Comment comment);//添加一级评论

    Page<Comment> queryCommentListOne();

    Comment queryCommentById(int comId);

    Page<Comment> queryCommentListByParentId(int comParentComment);

    int addUserCommentLike(int userId, int comId);

    int deleteUserCommentLike(int userId, int comId);
    List<Comment> queryCommentListByUserLikeAndParentId(int userId, int comParentComment);
    Page<Comment> queryCommentListOneNoChildByKeyWord(List<String> keyWords);


    int deteleComment(Comment comment);

    Page<Comment> queryCommentListTwo();

    List<Comment>  queryCommentListTwoSearch(Comment comment);

    List<Comment>  queryCommentListOneSearch(Comment comment);
}
