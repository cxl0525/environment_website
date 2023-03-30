package com.qdu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private int actId;
    private String actName;
    private String actImg;
    private String actPlace;
    private String actRequire;
    private String actSlogan;
    private String actProfile;
    private Date actStartDate;
    private Date actDeadLine;
    private Date actRegDate;
    private Date actRegDeadline;
    private int actNumber;
    private int actScore;
    private Status actStatus;
    private Tag actTag;
    private int actIsCheck;    //0待审  1通过  2未通过
    private int actIsCancel;
    private User actPromoter;



    private List<Comment> comments;
    private List<User> users;
    private Date actStartDate1;//搜索时间的时候
    private Date actStartDate2;//同上
    private List<Team> teams;
    private int actRegNumber;//报名人数  ：个人报名+团队报名

}
