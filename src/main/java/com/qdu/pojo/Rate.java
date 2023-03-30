package com.qdu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    private int rateId;
    private String rateName;
    private int rateMinScore;
    private int rateMaxScore;
}
