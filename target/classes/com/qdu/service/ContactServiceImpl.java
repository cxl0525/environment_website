package com.qdu.service;

import com.qdu.mapper.ContactMapper;
import com.qdu.pojo.ContactUs;
import com.qdu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactMapper contactMapper;

    @Override
    public User queryAdmin() {
        return contactMapper.queryAdmin();
    }

    @Override
    public int addMessage(ContactUs contactUs) {
        return contactMapper.addMessage(contactUs);
    }
}
