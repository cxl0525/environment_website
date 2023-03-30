package com.qdu.service;


import com.qdu.pojo.User;

import java.util.List;

public interface UserService {
    List<User> queryUserList();
    List<User> queryUserListByUserIsCheck(int userIsCheck);
    List<User> queryUserListByUserIsCheck12();
    User queryUserById(int userId);
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(int userId);
    User queryUserByUserNameAndUserPassword(String userName, String userPassword);
    User queryUserByUserName(String userName);
    List<User> queryUserListByActivityId(int actId);
    User queryUserActivityByUserIdAndActId(int userId,int actId);
    int updateScore(User user);
    int updateUserEdit(User user);
    int updateUserPwd(User user);
    int updateUserIsCheck(User user);
    int insertScore(User user);
    int updateUserBasic(User user);
    int updateUserAvatar(User user);
    List<User> queryUserListSearch(User user);
    List<User> queryUserDelSearch(User user);

    List<User> queryUserViewSearch(User user);
}
