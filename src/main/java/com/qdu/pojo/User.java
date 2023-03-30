package com.qdu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private String userRealName;
    private String userGender;
    private String userPhone;
    private String userIdentity;
    private String userEmail;
    private String userAddress;
    private String userQQId;
    private String userWeChatId;
    private String userAvatar;
    private Date userCreateDate;
    private int userIsCheck; //1 完美的   2已停用  3  删除  4  彻底删除  0  待审核

    //
    private int userScore;
    private Rate userRate;

    private Date userCreateDate1;
    private Date userCreateDate2;

}
