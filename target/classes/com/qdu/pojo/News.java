package com.qdu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private int newsId;
    private String newsTitle;
    private String newsContent;
    private Date newsReleaseDate;
    private User newsPublisher;
    private String newsImagePath;
    private String newsVideoPath;
    private int newsIsCheck;
    private int newsClicks;
    private Tag newsTag;

    private Date newsReleaseDate1;
    private Date newsReleaseDate2;



}
