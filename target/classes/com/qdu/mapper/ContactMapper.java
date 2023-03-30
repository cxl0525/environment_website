package com.qdu.mapper;

import com.qdu.pojo.ContactUs;
import com.qdu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ContactMapper {

    User queryAdmin();

    int addMessage(ContactUs contactUs);
}
