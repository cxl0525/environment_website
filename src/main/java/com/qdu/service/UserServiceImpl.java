package com.qdu.service;


import com.qdu.mapper.UserMapper;
import com.qdu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryUserList() {
        return userMapper.queryUserList();
    }

    @Override
    public List<User> queryUserListByUserIsCheck(int userIsCheck) {
        return userMapper.queryUserListByUserIsCheck(userIsCheck);
    }

    @Override
    public List<User> queryUserListByUserIsCheck12() {
        return userMapper.queryUserListByUserIsCheck12();
    }

    @Override
    public User queryUserById(int userId) {
        return userMapper.queryUserById(userId);
    }

    @Override
    public int addUser(User user) {
        user.setUserScore(0);
        int rows1 = userMapper.addUser(user);
        //设置用户积分表
        int rows2 = userMapper.insertScore(user);
        return rows1+rows2;
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUser(int userId) {
        return userMapper.deleteUser(userId);
    }

    @Override
    public User queryUserByUserNameAndUserPassword(String userName, String userPassword) {
        return userMapper.queryUserByUserNameAndUserPassword(userName,userPassword);
    }

    @Override
    public User queryUserByUserName(String userName) {
        return userMapper.queryUserByUserName(userName);
    }

    @Override
    public List<User> queryUserListByActivityId(int actId) {
        return userMapper.queryUserListByActivityId(actId);
    }

    @Override
    public User queryUserActivityByUserIdAndActId(int userId, int actId) {
        return userMapper.queryUserActivityByUserIdAndActId(userId,actId);
    }

    @Override
    public int updateScore(User user) {
        return userMapper.updateScore(user);
    }

    @Override
    public int updateUserEdit(User user) {
        return userMapper.updateUserEdit(user);
    }

    @Override
    public int updateUserPwd(User user) {
        return userMapper.updateUserPwd(user);
    }

    @Override
    public int updateUserIsCheck(User user) {
        return userMapper.updateUserIsCheck(user);
    }

    @Override
    public int insertScore(User user) {
        return userMapper.insertScore(user);
    }

    @Override
    public int updateUserBasic(User user) {
        return userMapper.updateUserBasic(user);
    }

    @Override
    public int updateUserAvatar(User user) {
        return userMapper.updateUserAvatar(user);
    }

    @Override
    public List<User> queryUserListSearch(User user) {
        return userMapper.queryUserListSearch(user);
    }

    @Override
    public List<User> queryUserDelSearch(User user) {
        return userMapper.queryUserDelSearch(user);
    }

    @Override
    public List<User> queryUserViewSearch(User user) {
        return userMapper.queryUserViewSearch(user);
    }
}
