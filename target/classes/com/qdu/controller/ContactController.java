package com.qdu.controller;

import com.qdu.pojo.ContactUs;
import com.qdu.pojo.User;
import com.qdu.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;


@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact")
    public String contact(Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        User admin = contactService.queryAdmin();
        model.addAttribute("admin",admin);
        model.addAttribute("user",loginUser);
        return "contact";
    }


    @ResponseBody
    @PostMapping("/sendMessage")
    public String sendMessage(HttpSession session,@RequestBody HashMap<String, String> map){
        User loginUser = (User) session.getAttribute("loginUser");
        ContactUs contactUs = new ContactUs();
        contactUs.setContUser(loginUser);
        contactUs.setContContent(map.get("message"));
        int id = contactService.addMessage(contactUs);
        String str="";
        if(id>0) str="发送成功";
        else str="发送失败";
        return str;
    }
}
