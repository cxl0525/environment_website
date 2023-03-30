package com.qdu.service;

import com.github.pagehelper.Page;
import com.qdu.mapper.CommentMapper;
import com.qdu.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> queryCommentListByActivityId(int actId) {
        return commentMapper.queryCommentListByActivityId(actId);
    }

    @Override
    public int addCommentNotOne(Comment comment) {
        return commentMapper.addCommentNotOne(comment);
    }


    @Override
    public int addCommentActivity(Comment comment) {
        return commentMapper.addCommentActivity(comment);
    }

    @Override
    public int addCommentOne(Comment comment) {
        return commentMapper.addCommentOne(comment);
    }

    @Override
    public Page<Comment> queryCommentListOne() {
        //这个返回的记录有点问题   比如：一个话题有两个评论   此时对分页记录来说是增加三条
        //return commentMapper.queryCommentListOne();
        Page<Comment> comments = commentMapper.queryCommentListOneNoChild();
        for (int i = 0; i <comments.size() ; i++) {
            Page<Comment> children = queryCommentListByParentId(comments.get(i).getComId());
            comments.get(i).setChildComments(children);
        }
        return comments;

    }

    @Override
    public Comment queryCommentById(int comId) {
        return commentMapper.queryCommentById(comId);
    }

    @Override
    public Page<Comment> queryCommentListByParentId(int comParentComment) {
        Page<Comment> comments = commentMapper.queryCommentListByParentId(comParentComment);
        return comments;
    }

    @Override
    public int addUserCommentLike(int userId, int comId) {
        return commentMapper.addUserCommentLike(userId,comId);
    }

    @Override
    public int deleteUserCommentLike(int userId, int comId) {
        return commentMapper.deleteUserCommentLike(userId,comId);
    }

    @Override
    public List<Comment> queryCommentListByUserLikeAndParentId(int userId, int comParentComment) {
        return commentMapper.queryCommentListByUserLikeAndParentId(userId,comParentComment);
    }

    @Override
    public Page<Comment> queryCommentListOneNoChildByKeyWord(List<String> keyWords) {
        //这个返回的记录有点问题   比如：一个话题有两个评论   此时对分页记录来说是增加三条
        //return commentMapper.queryCommentListOne();
        Page<Comment> comments = commentMapper.queryCommentListOneNoChildByKeyWord(keyWords);
        for (int i = 0; i <comments.size() ; i++) {
            Page<Comment> children = queryCommentListByParentId(comments.get(i).getComId());
            comments.get(i).setChildComments(children);
        }
        return comments;
        //return commentMapper.queryCommentListOneNoChildByKeyWord(keyWords);
    }

    @Override
    public int deteleComment(Comment comment) {
        //先删除usercommentlike表中comId为改评论的
        int row1 = commentMapper.deteleUserCommentLisk(comment);

        int row2 = commentMapper.deteleComment(comment);
        return row1+row2;
    }

    @Override
    public Page<Comment> queryCommentListTwo() {
        return commentMapper.queryCommentListTwo();
    }

    @Override
    public List<Comment> queryCommentListTwoSearch(Comment comment) {
        return commentMapper.queryCommentListTwoSearch(comment);
    }

    @Override
    public List<Comment> queryCommentListOneSearch(Comment comment) {
        //这个返回的记录有点问题   比如：一个话题有两个评论   此时对分页记录来说是增加三条
        //return commentMapper.queryCommentListOne();
        List<Comment> comments = commentMapper.queryCommentListOneSearch(comment);
        for (int i = 0; i <comments.size() ; i++) {
            List<Comment> children = queryCommentListByParentId(comments.get(i).getComId());
            comments.get(i).setChildComments(children);
        }
        return comments;
    }
}
