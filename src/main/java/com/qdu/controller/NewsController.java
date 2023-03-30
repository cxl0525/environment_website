package com.qdu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qdu.mapper.TagMapper;
import com.qdu.pojo.*;
import com.qdu.service.NewsService;
import com.qdu.service.TagService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private TagService tagService;
    @RequestMapping("/news")
    public String toblog(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "6") int pageSize, Model model){
        PageHelper.startPage(pageNum,pageSize);
        //这里应该查询  检查通过并且用户积分达到多少积分的   暂定 10
        //newsIsCheck 为 1   score(成为新闻的博客)
        PageInfo<News> pageInfo = new PageInfo<>(newsService.queryNewsListByPage(1,10),8);
        model.addAttribute("pageInfo",pageInfo);
        List<Tag> tags = tagService.queryTagList();
        //去当前年当前月  为前台归档查询做准备
        //归档查询只能查询本年的数据
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        model.addAttribute("thisYear",year);
        model.addAttribute("thisMonth",month);
        model.addAttribute("tags",tags);
        model.addAttribute("pageRequest","/news");
        return "blog";
    }


    /**
     * 跳转到新闻详情页面
     */
    @RequestMapping("/toNewsDetail/{newsId}")
    public String toNewsDetail(@PathVariable int newsId, Model model){
        List<Tag> tags = tagService.queryTagList();
        //去当前年当前月  为前台归档查询做准备
        //归档查询只能查询本年的数据
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        model.addAttribute("thisYear",year);
        model.addAttribute("thisMonth",month);
        model.addAttribute("tags",tags);
        News news = newsService.queryNewsById(newsId);
        news.setNewsClicks(news.getNewsClicks()+1);
        newsService.updateNews(news);
        model.addAttribute("news",news);
        return "blog-single";
    }


    @GetMapping("/newsByTag/{tagId}")
    public String newsByTag(@PathVariable int tagId, @RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "6") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<News> pageInfo = new PageInfo<>(newsService.queryNewsListByTagId(1,10,tagId),8);
        model.addAttribute("pageInfo",pageInfo);
        List<Tag> tags = tagService.queryTagList();
        //去当前年当前月  为前台归档查询做准备
        //归档查询只能查询本年的数据
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        model.addAttribute("thisYear",year);
        model.addAttribute("thisMonth",month);
        model.addAttribute("tags",tags);
        model.addAttribute("pageRequest","/newsByTag/"+tagId);
        return "blog";
    }

    @GetMapping("/newsByYearAndMonth/{releaseYear}/{releaseMonth}")
    public String newsByYearAndMonth(@PathVariable int releaseYear,@PathVariable int releaseMonth
            , @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "6") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);

        System.out.println("**"+releaseYear+"**"+releaseMonth);
        PageInfo<News> pageInfo = new PageInfo<>(newsService.queryNewsListByReleaseYearAndMonth(1,10,releaseYear,releaseMonth),8);
        model.addAttribute("pageInfo",pageInfo);
        List<Tag> tags = tagService.queryTagList();
        //去当前年当前月  为前台归档查询做准备
        //归档查询只能查询本年的数据
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        model.addAttribute("thisYear",year);
        model.addAttribute("thisMonth",month);
        model.addAttribute("tags",tags);
        model.addAttribute("pageRequest","/newsByYearAndMonth/"+releaseYear+"/"+releaseMonth);
        return "blog";
    }
    /**
     * 跳转到发布日志界面
     */
    @RequestMapping("/toLaunchBlog")
    public String toLaunchBlog(Model model){
        List<Tag> tags = tagService.queryTagList();
        model.addAttribute("tags",tags);
        return "launch_blog";
    }

    /**
     * 待实现
     * @param news
     * @param attributes
     * @param model
     * @param session
     * @return
     */
    @PostMapping("/launchNews")
    public String launchNews(MultipartFile newsImagePath1
            , News news, RedirectAttributes attributes, Model model, HttpSession session) throws IOException {

        //获取上传用户 ID
        User loginUser = (User) session.getAttribute("loginUser");
        //获取文件原名
        String oldFileName = newsImagePath1.getOriginalFilename();
        //获取文件后缀
        String extension = "."+ FilenameUtils.getExtension(newsImagePath1.getOriginalFilename());

        //生成新的文件名称
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ UUID.randomUUID().toString().replace("-", "") + extension;

        //文件大小
        Long size = newsImagePath1.getSize();

        //文件类型
        String type = newsImagePath1.getContentType();

        //处理根据日期生成目录
        String realPath = ResourceUtils.getURL("classpath:").getPath()+"/static/files-blog-pic";
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = realPath + "/" +dateFormat;
        File dateDir = new File(dateDirPath);
        if(!dateDir.exists())  dateDir.mkdirs();
        //处理上传文件
        newsImagePath1.transferTo(new File(dateDir,newFileName));
        //将文件信息放入数据库保存
        String isImg = type.startsWith("image") ? "是":"否";


        if(isImg=="是")  news.setNewsImagePath("/files-blog-pic/"+dateFormat+"/"+newFileName);
        else ;

        news.setNewsReleaseDate(new Date());
        news.setNewsPublisher(loginUser);
        news.setNewsClicks(0);
        news.setNewsIsCheck(0);
        newsService.addNews(news);
        return "redirect:/news";

        }

    @RequestMapping("/toBlogLaunched")
    public String toBlogLaunched(HttpSession session,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "6") int pageSize, Model model){
        User loginUser = (User) session.getAttribute("loginUser");
        PageHelper.startPage(pageNum,pageSize);
        //这里应该查询  检查通过并且用户积分达到多少积分的   暂定 10
        //newsIsCheck 为 1   score(成为新闻的博客)
        PageInfo<News> pageInfo = new PageInfo<>(newsService.queryNewsListByLaunchedUser(loginUser),8);
        model.addAttribute("pageInfo",pageInfo);
        List<Tag> tags = tagService.queryTagList();
        //去当前年当前月  为前台归档查询做准备
        //归档查询只能查询本年的数据
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        model.addAttribute("thisYear",year);
        model.addAttribute("thisMonth",month);
        model.addAttribute("tags",tags);
        model.addAttribute("pageRequest","/toBlogLaunched");
        return "blog_launched";
    }

    @GetMapping("/newsByTagAndLaunchedUser/{tagId}")
    public String newsByTagAndLaunchedUser(HttpSession session,@PathVariable int tagId, @RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "6") int pageSize, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");

        PageHelper.startPage(pageNum,pageSize);
        PageInfo<News> pageInfo = new PageInfo<>(newsService.queryNewsListByTagIdAndLaunchedUser(1,0,tagId,loginUser),8);
        model.addAttribute("pageInfo",pageInfo);
        List<Tag> tags = tagService.queryTagList();
        //去当前年当前月  为前台归档查询做准备
        //归档查询只能查询本年的数据
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        model.addAttribute("thisYear",year);
        model.addAttribute("thisMonth",month);
        model.addAttribute("tags",tags);
        model.addAttribute("pageRequest","/newsByTagAndLaunchedUser/"+tagId);
        return "blog_launched";
    }

    @GetMapping("/newsByYearAndMonthAndLaunchedUser/{releaseYear}/{releaseMonth}")
    public String newsByYearAndMonthAndLaunchedUser(HttpSession session,@PathVariable int releaseYear,@PathVariable int releaseMonth
            , @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "6") int pageSize, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");

        PageHelper.startPage(pageNum,pageSize);

        System.out.println("**"+releaseYear+"**"+releaseMonth);
        PageInfo<News> pageInfo = new PageInfo<>(newsService.queryNewsListByReleaseYearAndMonthAndLaunchedUser(1,0,releaseYear,releaseMonth,loginUser),8);
        model.addAttribute("pageInfo",pageInfo);
        List<Tag> tags = tagService.queryTagList();
        //去当前年当前月  为前台归档查询做准备
        //归档查询只能查询本年的数据
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        model.addAttribute("thisYear",year);
        model.addAttribute("thisMonth",month);
        model.addAttribute("tags",tags);
        model.addAttribute("pageRequest","/newsByYearAndMonthAndLaunchedUser/"+releaseYear+"/"+releaseMonth);
        return "blog_launched";
    }
}
