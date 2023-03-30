package com.qdu.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qdu.pojo.Rate;
import com.qdu.pojo.User;
import com.qdu.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/admin")
public class AdminRateController {

    @Autowired
    private RateService rateService;
    //跳转到等级管理
    @RequestMapping("/adminRateList")
    public String adminRateList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "12") int pageSize, Model model){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Rate> pageInfo = new PageInfo<>(rateService.queryRateList(),8);
        model.addAttribute("pageInfo",pageInfo);
        return "/admin/member-level";
    }

    @ResponseBody
    @RequestMapping("/delRate") //彻底删除
    public String delRate(@RequestBody Rate rate){
        rateService.delRateById(rate.getRateId());
        String message="";
        return  message;
    }


    @RequestMapping("/toUpdateRate/{rateId}")
    public String toUpdateRate(Model model,@PathVariable int rateId){
        Rate rate = rateService.queryRateById(rateId);
        model.addAttribute("rate",rate);
        return "/admin/level-edit";
    }

    @PostMapping("/updateRate")
    public String updateRate(Model model
            ,@RequestParam("rateId") int rateId
            ,@RequestParam("rateName") String rateName
            ,@RequestParam("rateScore") String rateScore
    ){
        System.out.println("****************************");
        Rate rate = new Rate();
        rate.setRateId(rateId);
        rate.setRateName(rateName);
        String s[] = rateScore.split("-");
        System.out.println(s[0]+"   "+s[1]+"**********************");
        rate.setRateMinScore(Integer.parseInt(s[0]));
        rate.setRateMaxScore(Integer.parseInt(s[1]));
        rateService.updateRate(rate);
        return "redirect:/admin/adminRateList";
    }

    @RequestMapping("/toAddRate")
    public String toAddRate(){
        return "/admin/level-add";
    }

    @PostMapping("/addRate")
    public String addRate(Model model
            ,@RequestParam("rateName") String rateName
            ,@RequestParam("rateScore") String rateScore
    ){

        Rate rate = new Rate();
        rate.setRateName(rateName);
        String s[] = rateScore.split("-");
        System.out.println(s[0]+" "+s[1]);
        rate.setRateMinScore(Integer.parseInt(s[0]));
        rate.setRateMaxScore(Integer.parseInt(s[1]));
        rateService.addRate(rate);
        return "redirect:/admin/adminRateList";
    }



}
