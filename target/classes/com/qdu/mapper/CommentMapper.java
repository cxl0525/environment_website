package com.qdu.mapper;

import com.github.pagehelper.Page;
import com.qdu.pojo.Comment;
import com.qdu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CommentMapper {
    List<Comment> queryCommentListByActivityId(int actId);
    int addCommentNotOne(Comment comment);//添加非一级评论
    int addCommentActivity(Comment comment);//同时更新活动评论表
    int addCommentOne(Comment comment);//添加一级评论
    Page<Comment> queryCommentListOne();//查询互动界面中的以及评论（充当话题）  注：不在活动评论表里面的评论
    Comment queryCommentById(int comId);
    Page<Comment> queryCommentListByParentId(int comParentComment);
    int addUserCommentLike(int userId, int comId);
    int deleteUserCommentLike(int userId, int comId);
    List<Comment> queryCommentListByUserLikeAndParentId(int userId, int comParentComment);
    Page<Comment> queryCommentListOneNoChild();
    Page<Comment> queryCommentListOneNoChildByKeyWord(@Param("keyWords") List<String> keyWords);
    int deteleComment(Comment comment);

    Page<Comment> queryCommentListTwo();


    int deteleUserCommentLisk(Comment comment);

    List<Comment>  queryCommentListTwoSearch(Comment comment);

    List<Comment>  queryCommentListOneSearch(Comment comment);
}
