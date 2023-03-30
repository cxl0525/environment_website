package com.qdu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {
    private int adId;
    private String adTitle;
    private String adContent;
    private String adLink;
    private String adImagePath;
    private Date adExpirationDate;
    private Date adCreateDate;
}
