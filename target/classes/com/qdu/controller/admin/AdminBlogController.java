package com.qdu.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qdu.pojo.Activity;
import com.qdu.pojo.News;
import com.qdu.pojo.Tag;
import com.qdu.service.NewsService;
import com.qdu.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/admin/blog")
public class AdminBlogController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private TagService tagService;


    @RequestMapping("/list")
    public String toblog(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "6") int pageSize, Model model){
        PageHelper.startPage(pageNum,pageSize);

        PageInfo<News> pageInfo = new PageInfo<>(newsService.queryBlogListByPage(),8);
        model.addAttribute("pageInfo",pageInfo);

        model.addAttribute("pageRequest","/admin/blog/list");
        return "admin/blog-list";
    }
    /**
     * 跳转到发起活动详情页面
     */
    @RequestMapping("/show/{newsId}")
    public String toBlogDetail(@PathVariable int newsId, Model model){
        News news = newsService.queryNewsById(newsId);



        model.addAttribute("news",news);
        return "admin/blog-show";
    }

    @ResponseBody
    @PostMapping("/delete")
    public String blogdelete(@RequestBody News news){
        System.out.println("news"+news);
        int rows = newsService.deteleNews(news);
        if (rows>0){
            return "success";
        }
        return "fail";
    }


    @RequestMapping("/list/search")
    public String toblogsearch(
            @RequestParam("newsReleaseDate1")String newsReleaseDate1
           , @RequestParam("newsReleaseDate2")String newsReleaseDate2
           , @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "6") int pageSize, Model model) throws ParseException {
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH-mm-ss" );
        News news = new News();
        news.setNewsReleaseDate1(sdf.parse(newsReleaseDate1+" 00-00-00"));
        news.setNewsReleaseDate2(sdf.parse(newsReleaseDate2+" 23-59-59"));
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<News> pageInfo = new PageInfo<>(newsService.queryBlogListBySearch(news),8);
        model.addAttribute("pageInfo",pageInfo);

        model.addAttribute("pageRequest","/admin/blog/list/search?newsReleaseDate1="+newsReleaseDate1+"&newsReleaseDate2="+newsReleaseDate2);

        return "admin/blog-list";
    }

}
