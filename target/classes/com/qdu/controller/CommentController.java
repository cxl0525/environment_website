package com.qdu.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qdu.pojo.Activity;
import com.qdu.pojo.Comment;
import com.qdu.pojo.User;
import com.qdu.service.CommentService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    /*
    跳转到互动界面
     */
    @RequestMapping("/comment")
    public String comment(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentService.queryCommentListOne());
        System.out.println("**"+pageInfo);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pageRequest","/comment");
        return "interaction";
    }
/*
跳转到二级评论页面
 */
@RequestMapping("/toCommentDetail/{comId}")
public String toCommentDetail(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, @PathVariable int comId, Model model,HttpSession session){
    User loginUser = (User) session.getAttribute("loginUser");
    if(loginUser == null){

    }
    Comment comment = commentService.queryCommentById(comId);
    PageHelper.startPage(pageNum,pageSize);
    Page<Comment> comments = commentService.queryCommentListByParentId(comId);
    List<Comment> commentslike = commentService.queryCommentListByUserLikeAndParentId(loginUser.getUserId(), comId);
    for (int i = 0; i < commentslike.size(); i++) {
        for (int j = 0; j <comments.size() ; j++) {
            if(commentslike.get(i).getComId()==comments.get(j).getComId()){
                comments.get(j).setIsLike(1);
            }
        }

    }

    PageInfo<Comment> pageInfo = new PageInfo<>(comments);
    model.addAttribute("comment",comment);
    model.addAttribute("pageInfo",pageInfo);
    model.addAttribute("pageRequest","/toCommentDetail/"+comId);
    return "interaction_detail";
}


    @ResponseBody
    @PostMapping("/commentuserlike")
    public String commentuserlike(@RequestBody Comment comment, HttpSession session){
        System.out.println(comment+"***");
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null){

        }
        String message="";
        if(comment.getIsLike()==1){
            //当前用户对此评论点赞
            commentService.addUserCommentLike(loginUser.getUserId(),comment.getComId());
        }else{
            //当前用户取消对此评论点赞
            commentService.deleteUserCommentLike(loginUser.getUserId(),comment.getComId());
        }
        return  message;
    }

    @PostMapping("/commentAndTopic")
    public String commentTopic ( Comment comment, HttpSession session,Model model){
        // public String activityComment (@RequestBody Comment comment, HttpSession session,Model model){

        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null){

        }
        comment.setComUser(loginUser);
        comment.setComCreateTime(new Date());
        comment.setComLikeNum(0);
        System.out.println("---"+comment);
        if(comment.getComParentComment().getComId()==-1){//提交的是一个话题
            commentService.addCommentOne(comment);
            return "redirect:/addTopic";

        }else{//提交的是一个话题下的评论
            commentService.addCommentNotOne(comment);
            return "redirect:/addTopicComment/" + comment.getComParentComment().getComId();
        }
    }


    @GetMapping("/addTopic")
    public String addTopic(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentService.queryCommentListOne());
        System.out.println("**"+pageInfo);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pageRequest","/comment");


        return "interaction :: interactionList";
    }

    @GetMapping("/addTopicComment/{comParentComment}")
    public String addTopicComment(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, @PathVariable int comParentComment, Model model,HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null){

        }
        Comment comment = commentService.queryCommentById(comParentComment);
        PageHelper.startPage(pageNum,pageSize);
        Page<Comment> comments = commentService.queryCommentListByParentId(comParentComment);
        List<Comment> commentslike = commentService.queryCommentListByUserLikeAndParentId(loginUser.getUserId(), comParentComment);
        for (int i = 0; i < commentslike.size(); i++) {
            for (int j = 0; j <comments.size() ; j++) {
                if(commentslike.get(i).getComId()==comments.get(j).getComId()){
                    comments.get(j).setIsLike(1);
                }
            }

        }

        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        model.addAttribute("comment",comment);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pageRequest","/toCommentDetail/"+comParentComment);
        return "interaction_detail :: interactionList";
    }

    /**
     * 模糊查询
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @PostMapping("/commentKeyWords")
    public String commentKeyWords(@RequestParam("searchContent")String searchContent,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {

        String[] s = searchContent.split(" ");
        System.out.println("+-*/"+s.toString());
        List<String> keyWordList1=new ArrayList<>();
        for (int i = 0; i <s.length ; i++) {
            if (s[i].equals(" ")||s[i].equals("")){

            }else {
                keyWordList1.add(s[i]);
            }
        }
        System.out.println(keyWordList1);
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentService.queryCommentListOneNoChildByKeyWord(keyWordList1));
        System.out.println("**"+pageInfo);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pageRequest","/commentKeyWords");
        return "interaction";
    }

}
