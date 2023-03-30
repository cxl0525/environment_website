package com.qdu.mapper;


import com.qdu.pojo.Rate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RateMapper {
    List<Rate> queryRateList();
    int delRateById(int rateId);
    Rate queryRateById(int rareId);
    int updateRate(Rate rate);
    int addRate(Rate rate);
}
