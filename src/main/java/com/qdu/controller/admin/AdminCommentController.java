package com.qdu.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qdu.pojo.Activity;
import com.qdu.pojo.Comment;
import com.qdu.pojo.News;
import com.qdu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/admin/comment")
public class AdminCommentController {
    @Autowired
    private CommentService commentService;
    /*
 跳转到父级界面（不包含活动评论）
  */
    @RequestMapping("/list")
    public String comment(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentService.queryCommentListOne());
        System.out.println("**"+pageInfo);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pageRequest","/admin/comment/list");
        return "admin/comment-list";
    }

    /**
     * 跳转到发起活动详情页面
     */
    @RequestMapping("/show/{comId}")
    public String toCommentDetail(@PathVariable int comId, Model model){
        Comment comment = commentService.queryCommentById(comId);
        model.addAttribute("comment",comment);
        return "admin/comment-show";
    }
    @ResponseBody
    @PostMapping("/delete")
    public String commentdelete(@RequestBody Comment comment){
        System.out.println("comment"+comment);
        comment = commentService.queryCommentById(comment.getComId());
        if(comment.getChildComments().size()>0){
            return "haveChild";
        }
        int rows = commentService.deteleComment(comment);
        if (rows>0){
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/child/list")
    public String childcommentlist(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentService.queryCommentListTwo());
        System.out.println("**"+pageInfo);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pageRequest","/admin/comment/child/list");
        return "admin/comment-child-list";
    }

    @RequestMapping("/list/search")
    public String commentsearch(
            @RequestParam("comCreateTime1")String comCreateTime1
            ,@RequestParam("comCreateTime2")String comCreateTime2
            ,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) throws ParseException {
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH-mm-ss" );
        Comment comment = new Comment();
        comment.setComCreateTime1(sdf.parse(comCreateTime1+" 00-00-00"));
        comment.setComCreateTime2(sdf.parse(comCreateTime2+" 23-59-59"));
        System.out.println("search1"+comment);

        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentService.queryCommentListOneSearch(comment));
        System.out.println("**"+pageInfo);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pageRequest","/admin/comment/list/search?comCreateTime1="+comCreateTime1+"&comCreateTime2="+comCreateTime2);

        return "admin/comment-list";
    }

    @RequestMapping("/child/search")
    public String childcommentsearch(
            @RequestParam("comCreateTime1")String comCreateTime1
            ,@RequestParam("comCreateTime2")String comCreateTime2
            ,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) throws ParseException {
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH-mm-ss" );
        Comment comment = new Comment();
        comment.setComCreateTime1(sdf.parse(comCreateTime1+" 00-00-00"));
        comment.setComCreateTime2(sdf.parse(comCreateTime2+" 23-59-59"));
        System.out.println("search2"+comment);
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentService.queryCommentListTwoSearch(comment));
        System.out.println("**"+pageInfo);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pageRequest","/admin/comment/child/search?comCreateTime1="+comCreateTime1+"&comCreateTime2="+comCreateTime2);

        return "admin/comment-child-list";
    }

}
