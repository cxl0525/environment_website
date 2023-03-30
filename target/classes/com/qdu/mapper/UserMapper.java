package com.qdu.mapper;


import com.qdu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

//这个注解表示这是一个mybatis的mapper 类 ：Dao
@Mapper
@Repository
public interface UserMapper {
    List<User> queryUserList();
    List<User> queryUserListByUserIsCheck(int userIsCheck);
    List<User> queryUserListByUserIsCheck12();
    User queryUserById(int userId);
    User queryUserActivityByUserIdAndActId(int userId,int actId);
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(int userId);
    User queryUserByUserNameAndUserPassword(String userName, String userPassword);
    User queryUserByUserName(String userName);
    List<User> queryUserListByActivityId(int actId);
    int updateScore(User user);
    int updateUserEdit(User user);
    int updateUserPwd(User user);
    int updateUserIsCheck(User user);
    int updateUserBasic(User user);
    int updateUserAvatar(User user);

    int insertScore(User user);
    List<User> queryUserListSearch(User user);
    List<User> queryUserDelSearch(User user);
    List<User> queryUserViewSearch(User user);
}
