package com.qdu.service;

import com.qdu.pojo.ContactUs;
import com.qdu.pojo.User;

public interface ContactService {
    User queryAdmin();

    int addMessage(ContactUs contactUs);
}
