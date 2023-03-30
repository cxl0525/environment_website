package com.qdu.service;

import com.qdu.pojo.Rate;

import java.util.List;

public interface RateService {
    List<Rate> queryRateList();
    int delRateById(int rateId);
    Rate queryRateById(int rareId);
    int updateRate(Rate rate);
    int addRate(Rate rate);
}
