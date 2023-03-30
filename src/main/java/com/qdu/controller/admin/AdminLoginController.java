package com.qdu.controller.admin;

import com.qdu.pojo.User;
import com.qdu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class AdminLoginController {
    @Autowired
    private UserService userService;
    @RequestMapping("/admin/login")
    public String toLogin(){
        return "admin/admin_login";
    }

    @ResponseBody
    @PostMapping("/admin/loginSubmit")
    public String loginSubmit( @RequestBody User admin,
                        HttpSession session,
                        RedirectAttributes attributes) {

        System.out.println("****************"+admin);
        admin = userService.queryUserByUserNameAndUserPassword(admin.getUserName(), admin.getUserPassword());
        System.out.println(admin);
        if(admin!=null){
            if(admin.getUserIsCheck()!=1){
                return "checkfail";
            }
            session.setAttribute("loginAdmin",admin);
            return "success";
        }else{
            return "fail";
        }

    }


    @RequestMapping("/admin/logout")
    public  String logout(HttpSession session){
        session.invalidate();
        return "redirect:/admin/login";
    }



}
