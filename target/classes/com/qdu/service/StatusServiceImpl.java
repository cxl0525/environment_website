package com.qdu.service;

import com.qdu.mapper.StatusMapper;
import com.qdu.pojo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusMapper statusMapper;

    @Override
    public List<Status> queryStatusList() {
        return statusMapper.queryStatusList();
    }
}
