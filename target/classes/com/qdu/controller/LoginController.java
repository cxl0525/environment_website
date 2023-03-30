package com.qdu.controller;

import com.qdu.pojo.User;
import com.qdu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public String toLogin(){
        return "login";
    }
    @PostMapping("/user/login")
    public String login(@RequestParam("userName")String userName,
                        @RequestParam("userPassword")String userPassword,
                        @RequestParam("identity")String identity,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.queryUserByUserNameAndUserPassword(userName, userPassword);

        if(identity.equals("管理员")){//管理员登录
            if (user != null) {
                user.setUserPassword(null);//隐藏密码
                session.setAttribute("loginUser",user);
                return "admin/index";
            } else {
                attributes.addFlashAttribute("message", "用户名和密码错误");
                return "redirect:/login";
            }

        }else{//普通用户登录
            if (user != null) {
                if (user.getUserIsCheck()!=1){
                    attributes.addFlashAttribute("message", "用户名状态不对无法登陆");
                    return "redirect:/login";
                }else {
                  //  user.setUserPassword(null);//隐藏密码
                    session.setAttribute("loginUser",user);
                    System.out.println("user"+user);
                    return "redirect:/index";
                }


            } else {
                System.out.println("user"+user);
                attributes.addFlashAttribute("message", "用户名和密码错误");
                return "redirect:/login";
            }

        }


    }





    @RequestMapping("/user/logout")
    public  String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }

}
