package com.qdu.controller.admin;

import com.qdu.pojo.Activity;
import com.qdu.pojo.News;
import com.qdu.pojo.Picture;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminIndexController {

    //跳转到首页界面
    @RequestMapping({"/admin/index","/admin/"})
    public String register(Model model){
//        //查图片
//        List<Picture> pictureList = pictureService.queryPictureListByTop(45);
//
//        //查讨论
//
//        //查活动 检查通过的   没被取消  前两个
//        List<Activity> activityList = activityService.queryActivityListByTop(1,0,2);
//        //查新闻  检查通过的  积分数至少为10
//
//        List<News> newsList = newsService.queryNewsListByTop(1,10,2);
//
//        model.addAttribute("pictureList",pictureList);
//        model.addAttribute("activityList",activityList);
//        model.addAttribute("newsList",newsList);
        return "admin/admin_index";
    }
}
