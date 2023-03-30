package com.qdu.service;

import com.qdu.mapper.TagMapper;
import com.qdu.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements  TagService {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<Tag> queryTagList() {
        return tagMapper.queryTagList();
    }
}
