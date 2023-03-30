package com.qdu.mapper;

import com.qdu.pojo.Status;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StatusMapper {
    List<Status> queryStatusList();
}
