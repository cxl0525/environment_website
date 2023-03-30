package com.qdu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture {
    private int picId;
    private String picImagePath;
    private User picUser;
    private int picClicks;
    private Type picType;
    private String picName;
}
