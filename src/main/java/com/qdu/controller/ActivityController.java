package com.qdu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qdu.pojo.*;
import com.qdu.service.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class ActivityController {
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

    /**
     * 活动列表
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/activity")
    public String activity(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        //这里应该查询  检查通过并且没有被取消的
        //actIsCheck=1,actIsCancel=0
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByPage(1,0),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);
        model.addAttribute("pageRequest","/activity");
        return "activity";
    }

    /**
     * 通过标签查活动列表
     * @param tagId
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/activityByTag/{tagId}")
    public String activityByTag(@PathVariable int tagId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByTagId(1,0,tagId),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);

        model.addAttribute("pageRequest","/activityByTag/"+tagId);
        return "activity";
    }

    /**
     * 通过状态
     * @param statusId
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/activityByStatus/{statusId}")
    public String activityByStatus(@PathVariable int statusId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByStatusId(1,0,statusId),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);
        model.addAttribute("pageRequest","/activityByStatus/"+statusId);
        return "activity";
    }

    /**
     * 通过时间
     * @param startYear
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/activityByYear/{startYear}")
    public String activityByYear(@PathVariable int startYear, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByStartYear(1,0,startYear),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);
        model.addAttribute("pageRequest","/activityByYear/"+startYear);
        return "activity";
    }

    /**
     * 跳转到发起活动界面
     */
    @RequestMapping("/toLaunchActivity")
    public String toLaunchActivity(Model model){
        List<Tag> tags = tagService.queryTagList();
        model.addAttribute("tags",tags);
        return "launch_activity";
    }

    /**
     * 发起活动
     * @param actImg
     * @param actName
     * @param actTag
     * @param province
     * @param city
     * @param actNumber
     * @param actSlogan
     * @param actProfile
     * @param actStartDate
     * @param actStartTime
     * @param actDeadLine
     * @param actDeadLineTime
     * @param actRegDate
     * @param actRegTime
     * @param actRegDeadline
     * @param actRegDeadlineTime
     * @param session
     * @return
     * @throws ParseException
     * @throws IOException
     */
    @PostMapping("/launchActivity")
    public String launchActivity(MultipartFile actImg
            , @RequestParam("actName")String actName
            , @RequestParam("actTag")String actTag
            , @RequestParam("province")String province
            , @RequestParam("city")String city
            , @RequestParam("actNumber")String actNumber
            , @RequestParam("actSlogan")String actSlogan
            , @RequestParam("actProfile")String actProfile
            , @RequestParam("actStartDate")String actStartDate
            , @RequestParam("actStartTime")String actStartTime
            , @RequestParam("actDeadLine")String actDeadLine
            , @RequestParam("actDeadLineTime")String actDeadLineTime
            , @RequestParam("actRegDate")String actRegDate
            , @RequestParam("actRegTime")String actRegTime
            , @RequestParam("actRegDeadline")String actRegDeadline
            , @RequestParam("actRegDeadlineTime")String actRegDeadlineTime
            , HttpSession session) throws ParseException, IOException {
        Activity activity = new Activity();
        //获取上传用户 ID
        User loginUser = (User) session.getAttribute("loginUser");
        //获取文件原名
        String oldFileName = actImg.getOriginalFilename();
        //获取文件后缀
        String extension = "."+ FilenameUtils.getExtension(actImg.getOriginalFilename());

        //生成新的文件名称
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ UUID.randomUUID().toString().replace("-", "") + extension;

        //文件大小
        Long size = actImg.getSize();

        //文件类型
        String type = actImg.getContentType();

        //处理根据日期生成目录
        String realPath = ResourceUtils.getURL("classpath:").getPath()+"/static/files";
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = realPath + "/" +dateFormat;
        File dateDir = new File(dateDirPath);
        if(!dateDir.exists())  dateDir.mkdirs();

        //处理上传文件
        actImg.transferTo(new File(dateDir,newFileName));

        //将文件信息放入数据库保存


        String isImg = type.startsWith("image") ? "是":"否";
        if(isImg=="是")  activity.setActImg("/files/"+dateFormat+"/"+newFileName);// activity.setActImg("/files/"+dateFormat);
        else ;

        activity.setActName(actName);
        activity.setActPromoter(loginUser);
        Tag tag = new Tag();
        tag.setTagId(Integer.parseInt(actTag));
        activity.setActTag(tag);
        activity.setActPlace(province+"-"+city);
        activity.setActNumber(Integer.parseInt(actNumber));
        activity.setActSlogan(actSlogan);
        activity.setActProfile(actProfile);

        //设置状态   一开始都为  准备招募   1
        //应该数据库有个触发器还是什么的  就是  根据时间  在设置状态

        Status status = new Status();
        status.setStatusId(1);
        activity.setActStatus(status);
        //设置是否检验通过（此过程需要管理员检验 0 没通过）
        activity.setActIsCheck(0);
        //设置活动是否取消   （刚发起的活动默认不取消   0 不取消）
        activity.setActIsCancel(0);
        //设置活动积分    此功能未开通  所以默认是1
        activity.setActScore(1);
        activity.setActRequire("无");//活动要求
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        //Date date = sdf.parse( " 2008-07-10 19:20:00 " );
        activity.setActStartDate(sdf.parse( actStartDate+" "+actStartTime+":00" ));
        activity.setActDeadLine(sdf.parse( actDeadLine+" "+actDeadLineTime+":00"  ));
        activity.setActRegDate(sdf.parse( actRegDate+" "+actRegTime+":00"  ));
        activity.setActRegDeadline(sdf.parse( actRegDeadline+" "+actRegDeadlineTime+":00"  ));
//        System.out.println(activity+ "****"+actImg);

        activityService.addActivity(activity);

        return "redirect:/activity";
    }
    /**
     * 跳转到发起活动详情页面
     */
    @RequestMapping("/toActivityDetail/{actId}")
    public String toActivityDetail(@PathVariable int actId,Model model, HttpSession session){
        Activity activity = activityService.queryActivityById(actId);
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("activity",activity);
        model.addAttribute("comments",activity.getComments());

        if(activity.getActPromoter().getUserId()==loginUser.getUserId()){//发起人
            return "activity_detail_launcher";

        }else {//非发起人
            return "activity_detail";
        }
    }

    /**
     * 活动评论
     * @param comment
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/activityComment")
    public String activityComment ( Comment comment, HttpSession session,Model model){
   // public String activityComment (@RequestBody Comment comment, HttpSession session,Model model){

        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null){

        }
        comment.setComUser(loginUser);
        comment.setComCreateTime(new Date());
        comment.setComLikeNum(0);
        //插入  此时parentcom 为null 所以他的属性 id取不到会报错  此时为了防止报错  new一个parentcom
        commentService.addCommentOne(comment);
        commentService.addCommentActivity(comment);
        System.out.println("comment"+comment);
        return "redirect:/activitycomments/" + comment.getComActivity().getActId();
    }

    /**
     * 更新评论
     * @param actId
     * @param model
     * @return
     */
    @GetMapping("/activitycomments/{actId}")
    public String comments(@PathVariable int actId, Model model) {
        List<Comment> comments = commentService.queryCommentListByActivityId(actId);
        model.addAttribute("comments", comments);
        System.out.println("commentsList"+comments);
        return "activity_detail :: commentList";
    }

    /**
     * 活动报名
     * @param activity
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping("/activityReg")
    public String activityReg(@RequestBody Activity activity, HttpSession session){
        System.out.println(activity+"***");
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null){

        }
        String message="";
        User regUser = userService.queryUserActivityByUserIdAndActId(loginUser.getUserId(),activity.getActId());
        if (regUser!=null){//
            //  已经参加了  拒绝

            message = "你已注册";
        }else{
            activityService.addActivityUser(activity.getActId(),loginUser.getUserId());

            message = "注册成功";
        }
        return  message;
    }

    @ResponseBody
    @PostMapping("/activityTeamReg")
    public String activityTeamReg(@RequestBody Team team, HttpSession session){
        System.out.println(team+"***");
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null){

        }
        String message="";


        Activity activity = activityService.queryActivityById(team.getTeamAct().getActId());
        team.setTeamLeader(loginUser);
        team.setTeamName(activity.getActName()+team.getTeamNumber()+"人小分队");
        team.setTeamPlayerInfo(team.toString());
        int rows = activityService.addActivityTeam(team);
        //User regUser = userService.queryUserActivityByUserIdAndActId(loginUser.getUserId(),activity.getActId());
        if (rows>0){//
            //  已经参加了  拒绝

            message = "success";
        }else{


            message = "fail";
        }
        return  message;
    }


//    @PostMapping("/activityReg")
//    public String activityReg(Activity activity, HttpSession session, RedirectAttributes attributes){
//        User loginUser = (User) session.getAttribute("loginUser");
//        if(loginUser == null){
//
//        }
//        User regUser = userService.queryUserActivityByUserIdAndActId(loginUser.getUserId(),activity.getActId());
//        if (regUser!=null){//
//            //  已经参加了  拒绝
//            attributes.addAttribute("message","你已注册");
//
//
//        }else{
//            activityService.addActivityUser(activity.getActId(),loginUser.getUserId());
//            attributes.addAttribute("message","注册成功");
//        }
//        return  "redirect:/actdetail/" + activity.getActId();
//    }
//    @GetMapping("/actdetail/{actId}")
//    public String actdetail(@PathVariable int actId, Model model) {
//        Activity activity = activityService.queryActivityById(actId);
//        model.addAttribute("activity",activity);
//        model.addAttribute("comments",activity.getComments());
//
//
//        return "activity_detail :: actdetail";
//    }

    /**
     * 个人界面的   我参与的活动
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/toActivityJoined")
    public String toActivityJoined(HttpSession session,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");


        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByJoinedUser(loginUser),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);
        model.addAttribute("pageRequest","/toActivityJoined");
        return "activity_joined";
    }


    /**
     * 通过标签查活动列表
     * @param tagId
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/activityByTagAndJoinedUser/{tagId}")
    public String activityByTagAndJoinedUser(HttpSession session,@PathVariable int tagId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        System.out.println("loginUser"+loginUser);

        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByTagIdAndJoinedUser(1,0,tagId,loginUser),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);

        model.addAttribute("pageRequest","/activityByTagAndJoinedUser/"+tagId);
        return "activity_joined";
    }

    /**
     * 通过状态
     * @param statusId
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/activityByStatusAndJoinedUser/{statusId}")
    public String activityByStatusAndJoinedUser(HttpSession session,@PathVariable int statusId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");

        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByStatusIdAndJoinedUser(1,0,statusId,loginUser),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);
        model.addAttribute("pageRequest","/activityByStatusAndJoinedUser/"+statusId);
        return "activity_joined";
    }

    /**
     * 通过时间
     * @param startYear
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/activityByYearAndJoinedUser/{startYear}")
    public String activityByYearAndJoinedUser(HttpSession session,@PathVariable int startYear, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");

        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByStartYearAndJoinedUser(1,0,startYear,loginUser),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);
        model.addAttribute("pageRequest","/activityByYearAndJoinedUser/"+startYear);
        return "activity_joined";
    }



    /**
     * 个人界面的   我发起的活动
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/toActivityLaunched")
    public String toActivityLaunched(HttpSession session,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByLaunchedUser(loginUser),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);
        model.addAttribute("pageRequest","/toActivityLaunched");
        return "activity_launched";
    }



    /**
     * 通过审查 活动列表
     * @param actIsCheck
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/activityByCheckAndLaunchedUser/{actIsCheck}")
    public String activityByCheckAndLaunchedUser(HttpSession session,@PathVariable int actIsCheck, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        System.out.println("loginUser"+loginUser);

        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByCheckAndLaunchedUser(actIsCheck,0,loginUser),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);

        model.addAttribute("pageRequest","/activityByCheckAndLaunchedUser/"+actIsCheck);
        return "activity_launched";
    }



    /**
     * 通过标签查活动列表
     * @param tagId
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/activityByTagAndLaunchedUser/{tagId}")
    public String activityByTagAndLaunchedUser(HttpSession session,@PathVariable int tagId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        System.out.println("loginUser"+loginUser);

        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByTagIdAndLaunchedUser(1,0,tagId,loginUser),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);

        model.addAttribute("pageRequest","/activityByTagAndLaunchedUser/"+tagId);
        return "activity_launched";
    }

    /**
     * 通过状态
     * @param statusId
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/activityByStatusAndLaunchedUser/{statusId}")
    public String activityByStatusAndLaunchedUser(HttpSession session,@PathVariable int statusId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");

        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByStatusIdAndLaunchedUser(1,0,statusId,loginUser),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);
        model.addAttribute("pageRequest","/activityByStatusAndLaunchedUser/"+statusId);
        return "activity_launched";
    }

    /**
     * 通过时间
     * @param startYear
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/activityByYearAndLaunchedUser/{startYear}")
    public String activityByYearAndLaunchedUser(HttpSession session,@PathVariable int startYear, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");

        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Activity> pageInfo = new PageInfo<>(activityService.queryActivityListByStartYearAndLaunchedUser(1,0,startYear,loginUser),8);
        model.addAttribute("pageInfo",pageInfo);

        List<Tag> tags = tagService.queryTagList();
        List<Status> statuses = statusService.queryStatusList();
        List<Integer> years = activityService.queryActivityStartYear();

        model.addAttribute("tags",tags);
        model.addAttribute("statuses",statuses);
        model.addAttribute("years",years);
        model.addAttribute("pageRequest","/activityByYearAndLaunchedUser/"+startYear);
        return "activity_launched";
    }
    @RequestMapping("/activity/map")
    public String activitymap(){
        return "map";
    }


    /**
     * 取消活动
     * @param actId
     * @return
     */
    @RequestMapping("/activityCancel/{actId}")
    public String activityCancel(@PathVariable int actId){
        System.out.println("actId"+actId);
        //取消的时候
        // 1先把参与的用户搜出来  邮箱告知
        //2正式取消（活动表关联的也去掉或者不用管）
        Activity activity = activityService.queryActivityById(actId);
        activity.setActIsCancel(1);
        int row = activityService.updateActivityIsCancel(activity);

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



        return "redirect:/activity";
    }
    @RequestMapping("/activitySignPeople/{actId}")
    public String activitySignPeople(@PathVariable int actId
            , @RequestParam(defaultValue = "1") int pageNum
            , @RequestParam(defaultValue = "12") int pageSize
            , Model model){
        System.out.println("actId"+actId);
//查询的时候应该八个人以及团队都查出来
        List<User> users = userService.queryUserListByActivityId(actId);

        PageHelper.startPage(pageNum,pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(users,8);
        model.addAttribute("pageInfo",pageInfo);

        model.addAttribute("pageRequest","/activitySignPeople/"+actId);
        return "sign_people";
    }

}
