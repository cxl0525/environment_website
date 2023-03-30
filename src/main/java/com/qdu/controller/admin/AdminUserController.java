package com.qdu.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qdu.pojo.Activity;
import com.qdu.pojo.Picture;
import com.qdu.pojo.User;
import com.qdu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    //跳转到个人界面
    @RequestMapping("/adminUserList")
    public String adminUserList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userService.queryUserListByUserIsCheck12(),8);
        model.addAttribute("pageInfo",pageInfo);
        return "/admin/member-list";
    }

    //跳转到已删除个人界面
    @RequestMapping("/adminDelUserList")
    public String adminDelUserList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userService.queryUserListByUserIsCheck(3),8);
        model.addAttribute("pageInfo",pageInfo);
        return "/admin/member-del";
    }

    //跳转到已待审核个人界面
    @RequestMapping("/adminViewUserList")
    public String adminViewUserList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userService.queryUserListByUserIsCheck(0),8);
        model.addAttribute("pageInfo",pageInfo);
        return "/admin/member-view";
    }

    //跳转到积分界面
    @RequestMapping("/adminScore")
    public String adminScore(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userService.queryUserList(),8);
        model.addAttribute("pageInfo",pageInfo);
        return "/admin/member-kiss";
    }

    @ResponseBody
    @RequestMapping("/delUser") //彻底删除
    public String delUser(@RequestBody User user){
        //System.out.println(user.getUserId());
        userService.deleteUser(user.getUserId());
        String message="";
        return  message;
    }

    @ResponseBody
    @RequestMapping("/stopUser")
    public String stopUser(@RequestBody User user){
       // System.out.println(user.getUserId()+"***************");
        user.setUserIsCheck(2);
        //System.out.println(user.getUserIsCheck()+"********************");
        userService.updateUserIsCheck(user);
        String message="";
        return  message;
    }

    @ResponseBody
    @RequestMapping("/openUser")
    public String openUser(@RequestBody User user){
        //System.out.println(user.getUserId()+"%%%%%%%");
        user.setUserIsCheck(1);
        userService.updateUserIsCheck(user);
        String message="";
        return  message;
    }

    @ResponseBody
    @RequestMapping("/delUpdateUser")
    public String delUpdateUser(@RequestBody User user){
        user.setUserIsCheck(3);//删除更新
        userService.updateUserIsCheck(user);
        String message="";
        return  message;
    }

    @RequestMapping("/userByIdShow/{userId}")
    public String userByIdShow(Model model,@PathVariable int userId){
        User user = userService.queryUserById(userId);
        model.addAttribute("user",user);
        return "/admin/member-show";
    }

    @RequestMapping("/userByIdEdit/{userId}")
    public String userByIdEdit(Model model,@PathVariable int userId){
        User user = userService.queryUserById(userId);
        model.addAttribute("user",user);
        return "/admin/member-edit";
    }

    @PostMapping("/updateUser")
    public String updateUser(Model model
            ,@RequestParam("userId") int userId
            ,@RequestParam("userEmail") String userEmail
            ,@RequestParam("userPhone") String userPhone
            ,@RequestParam("userName") String userName
            ,@RequestParam("sex") int sex
            ,@RequestParam("userAddress") String userAddress
                             ){

        User user = new User();
        user.setUserId(userId);
        user.setUserEmail(userEmail);
        user.setUserPhone(userPhone);
        user.setUserName(userName);
        if(sex==0){
            user.setUserGender("男");
        }else user.setUserGender("女");
        user.setUserAddress(userAddress);

        userService.updateUserEdit(user);
        return "redirect:/admin/adminUserList";
    }

    @RequestMapping("/userByIdPwd/{userId}")
    public String userByIdPwd(Model model,@PathVariable int userId){
        User user = userService.queryUserById(userId);
        model.addAttribute("user",user);
        return "/admin/member-password";
    }

    @RequestMapping("/toAddUser")
    public String toAddUser(){
        return "/admin/member-add";
    }

    @PostMapping("/updatePwd")
    public String updatePwd(Model model
            ,@RequestParam("userId") int userId
            ,@RequestParam("userPassword") String userPassword
    ){

        User user = new User();
        user.setUserId(userId);
        user.setUserPassword(userPassword);
        userService.updateUserPwd(user);
        return "redirect:/admin/adminUserList";
    }

    @PostMapping("/addUser")
    public String addUser(Model model
            ,@RequestParam("userEmail") String userEmail
            ,@RequestParam("userPhone") String userPhone
            ,@RequestParam("userName") String userName
            ,@RequestParam("userPassword") String userPassword
            ,@RequestParam("userIdentity") String userIdentity
            ,@RequestParam("userRealName") String userRealName
    ){

        User user = new User();
        user.setUserName(userName);
        user.setUserPhone(userPhone);
        user.setUserEmail(userEmail);
        user.setUserRealName(userRealName);
        user.setUserIdentity(userIdentity);
        user.setUserPassword(userPassword);
        //以下是默认字段
        user.setUserIsCheck(1);
        user.setUserGender("男");
        user.setUserAddress("山东-青岛");
        user.setUserCreateDate(new Date());
        user.setUserScore(0);
        user.setUserAvatar("/images/userAvatar.png");
        user.setUserQQId("000000");
        user.setUserWeChatId("000000");
        userService.addUser(user);
        return "redirect:/admin/adminUserList";
    }

    @ResponseBody
    @RequestMapping("/recoverUser")
    public String recoverUser(@RequestBody User user){
        user.setUserIsCheck(1);
        userService.updateUserIsCheck(user);
        String message="";
        return  message;
    }

    @ResponseBody
    @RequestMapping("/unsetUser")
    public String unsetUser(@RequestBody User user){
        user.setUserIsCheck(4);
        userService.updateUserIsCheck(user);
        String message="";
        return  message;
    }




    @RequestMapping("/userListSearch")
    public String userListSearch(@RequestParam("userName")String userName
            , @RequestParam("userCreateDate1")String userCreateDate1
            , @RequestParam("userCreateDate2")String userCreateDate2
            , Model model
            , @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize
    ) throws ParseException {
        User user = new User();
        user.setUserName(userName);
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH-mm-ss" );
        user.setUserCreateDate1(sdf.parse(userCreateDate1+" 00-00-00"));
        user.setUserCreateDate2(sdf.parse(userCreateDate2+" 23-59-59"));
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userService.queryUserListSearch(user),8);
        model.addAttribute("pageInfo",pageInfo);

        model.addAttribute("pageRequest","/admin/userListSearch?userCreateDate1="+userCreateDate1+"&userCreateDate2="+userCreateDate2+"&userName="+userName);
        return "admin/member-list";
    }


    @RequestMapping("/userDelSearch")
    public String userDelSearch(@RequestParam("userName")String userName
            , @RequestParam("userCreateDate1")String userCreateDate1
            , @RequestParam("userCreateDate2")String userCreateDate2
            , Model model
            , @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize
    ) throws ParseException {
        User user = new User();
        user.setUserName(userName);
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH-mm-ss" );
        user.setUserCreateDate1(sdf.parse(userCreateDate1+" 00-00-00"));
        user.setUserCreateDate2(sdf.parse(userCreateDate2+" 23-59-59"));
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userService.queryUserDelSearch(user),8);
        model.addAttribute("pageInfo",pageInfo);

        model.addAttribute("pageRequest","/admin/userDelSearch?userCreateDate1="+userCreateDate1+"&userCreateDate2="+userCreateDate2+"&userName="+userName);
        return "admin/member-del";
    }

    @RequestMapping("/userViewSearch")
    public String userViewSearch(@RequestParam("userName")String userName
            , @RequestParam("userCreateDate1")String userCreateDate1
            , @RequestParam("userCreateDate2")String userCreateDate2
            , Model model
            , @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize
    ) throws ParseException {
        User user = new User();
        user.setUserName(userName);
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH-mm-ss" );
        user.setUserCreateDate1(sdf.parse(userCreateDate1+" 00-00-00"));
        user.setUserCreateDate2(sdf.parse(userCreateDate2+" 23-59-59"));
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userService.queryUserViewSearch(user),8);
        model.addAttribute("pageInfo",pageInfo);

        model.addAttribute("pageRequest","/admin/userViewSearch?userCreateDate1="+userCreateDate1+"&userCreateDate2="+userCreateDate2+"&userName="+userName);
        return "admin/member-view";
    }

}
