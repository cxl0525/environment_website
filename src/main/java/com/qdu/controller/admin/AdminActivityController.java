package com.qdu.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qdu.pojo.Activity;
import com.qdu.pojo.Status;
import com.qdu.pojo.Tag;
import com.qdu.pojo.User;
import com.qdu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminActivityController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TagService tagService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSenderImpl mailSender;

    @GetMapping("/activity/list")
    public String activity(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        //这里应该查询  检查通过并且没有被取消的
        //actIsCheck=1,actIsCancel=0
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByPage(1,0),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
//        List<Integer> years = activityService.queryActivityStartYear();
     //   model.addAttribute("activity",new Activity());
        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
//        model.addAttribute("years",years);
        model.addAttribute("pageRequest","/admin/activity/list");
        return "admin/activity-list";
    }

    @GetMapping("/activity/notcheck")
    public String activityNotCheck(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        //这里应该查询  检查通过并且没有被取消的
        //actIsCheck=1,actIsCancel=0
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByPage(0,0),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
//        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
//        model.addAttribute("years",years);
        model.addAttribute("pageRequest","/admin/activity/notcheck");
        return "admin/activity-readyPass";
    }

    /**
     * 跳转到发起活动详情页面
     */
    @RequestMapping("/activity/show/{actId}")
    public String toActivityDetail(@PathVariable int actId, Model model){
        Activity activity = activityService.queryActivityById(actId);



        model.addAttribute("activity",activity);
        model.addAttribute("comments",activity.getComments());
        return "admin/activity-show";
    }

    @ResponseBody
    @PostMapping("/activity/cancel")
    public String activitycancel(@RequestBody Activity activity){
        System.out.println("activity"+activity);
        int rows = activityService.updateActivityIsCancel(activity);

        int actId = activity.getActId();
        List<User> users = userService.queryUserListByActivityId(actId);

        for (User user : users) {

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("取消活动");
            Activity activity1 = activityService.queryActivityById(actId);
            mailMessage.setText("尊敬的"+user.getUserName()+"您好,"+"您参与报名的活动："+activity1.getActName()+",已被取消！非常抱歉给你带来的不便~");
            mailMessage.setTo(user.getUserEmail());
            mailMessage.setFrom("1530464203@qq.com");

            mailSender.send(mailMessage);
        }



        if (rows>0){
            return "success";
        }
        return "fail";
    }
    @ResponseBody
    @PostMapping("/activity/delete")
    public String activitydelete(@RequestBody Activity activity){
        System.out.println("activity"+activity);
        int rows = activityService.deteleActivity(activity);
        if (rows>0){
            return "success";
        }
        return "fail";
    }

    @ResponseBody
    @PostMapping("/activity/check")
    public String activitycheck(@RequestBody Activity activity){
        System.out.println("activity"+activity);
        int rows = activityService.updateActivityIsCheck(activity);
        if (rows>0){
            return "success";
        }
        return "fail";
    }
    @GetMapping("/activity/notcheck/search")
    public String notchecksearch(@RequestParam("actName")String actName
            , @RequestParam("actStartDate1")String actStartDate1
            , @RequestParam("actStartDate2")String actStartDate2
            ,Model model
            ,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize) throws ParseException {
        System.out.println("search2"+actName+actStartDate1+"**"+actStartDate2);
        Activity activity = new Activity();
        activity.setActName(actName);
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH-mm-ss" );
        //Date date = sdf.parse( " 2008-07-10 19:20:00 " );
        activity.setActStartDate1(sdf.parse( actStartDate1+" 00-00-00"));
        activity.setActStartDate2(sdf.parse( actStartDate2+" 23-59-59"));
        PageHelper.startPage(pageNum,pageSize);
        //这里应该查询  检查通过并且没有被取消的
        //actIsCheck=1,actIsCancel=0
        System.out.println(activity);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListBySearch(0,0,activity),8);
        model.addAttribute("pageInfo",pageInfo);

        model.addAttribute("searchActivity",activity);
        System.out.println();
        model.addAttribute("pageRequest","/admin/activity/notcheck/search?actStartDate1="+actStartDate1+"&actStartDate2="+actStartDate2+"&actName="+actName);
        return "admin/activity-readyPass";
    }



    @GetMapping("/activity/list/search")
    public String listsearch(@RequestParam("actName")String actName
            , @RequestParam("actStartDate1")String actStartDate1
            , @RequestParam("actStartDate2")String actStartDate2
            , Model model
            , @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize
                             , HttpServletRequest request
                             ) throws ParseException {
        System.out.println("request"+request);
        System.out.println("requesturl"+request.getRequestURI());
        System.out.println("search1"+actName+actStartDate1+"**"+actStartDate2);
        Activity activity = new Activity();
        activity.setActName(actName);
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH-mm-ss" );
        //Date date = sdf.parse( " 2008-07-10 19:20:00 " );
        activity.setActStartDate1(sdf.parse( actStartDate1+" 00-00-00"));
        activity.setActStartDate2(sdf.parse( actStartDate2+" 23-59-59"));
        PageHelper.startPage(pageNum,pageSize);
        //这里应该查询  检查通过并且没有被取消的
        //actIsCheck=1,actIsCancel=0
        System.out.println(activity);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListBySearch(1,0,activity),8);
        model.addAttribute("pageInfo",pageInfo);

        model.addAttribute("searchActivity",activity);
        model.addAttribute("pageRequest","/admin/activity/list/search?actStartDate1="+actStartDate1+"&actStartDate2="+actStartDate2+"&actName="+actName);
        return "admin/activity-list";
    }
}
