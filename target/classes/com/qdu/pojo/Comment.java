package com.qdu.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int comId;
    private User comUser;
    private String comContent;
    private Date comCreateTime;
    private Comment comParentComment;
    private int comLikeNum;

    private Activity comActivity;
    private List<Comment> childComments;
    private int isLike;



    //search 开始截止
    private Date comCreateTime1;
    private Date comCreateTime2;




}
