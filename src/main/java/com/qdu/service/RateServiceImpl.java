package com.qdu.service;

import com.qdu.mapper.RateMapper;
import com.qdu.pojo.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    private RateMapper rateMapper;

    @Override
    public List<Rate> queryRateList() {
        return rateMapper.queryRateList();
    }

    @Override
    public int delRateById(int rateId) {
        return rateMapper.delRateById(rateId);
    }

    @Override
    public Rate queryRateById(int rareId) {
        return rateMapper.queryRateById(rareId);
    }

    @Override
    public int updateRate(Rate rate) {
        return rateMapper.updateRate(rate);
    }

    @Override
    public int addRate(Rate rate) {
        return rateMapper.addRate(rate);
    }
}
