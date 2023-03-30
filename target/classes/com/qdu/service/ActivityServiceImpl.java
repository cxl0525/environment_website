package com.qdu.service;

import com.github.pagehelper.Page;
import com.qdu.mapper.ActivityMapper;
import com.qdu.mapper.CommentMapper;
import com.qdu.mapper.UserMapper;
import com.qdu.pojo.Activity;
import com.qdu.pojo.Comment;
import com.qdu.pojo.Team;
import com.qdu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl  implements  ActivityService{
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Page<Activity> queryActivityListByPage(int actIsCheck,int actIsCancel) {
        return activityMapper.queryActivityListByPage(actIsCheck,actIsCancel);
    }

    @Override
    public Activity queryActivityById(int actId) {
        Activity activity = activityMapper.queryActivityById(actId);
        //参与者
        List<User> users = userMapper.queryUserListByActivityId(actId);
        List<Team> teams = activityMapper.queryTeamListByActivityId(actId);
        //评论
        List<Comment> comments = commentMapper.queryCommentListByActivityId(actId);
        activity.setUsers(users);
        activity.setTeams(teams);
        activity.setComments(comments);
        Integer num1 = activityMapper.queryTeamPlayerNumberByActivityId(actId);
        int num = 0;
        if(num1!=null){
            num = num1;
        }
        activity.setActRegNumber(users.size()+num);
        return activity;
    }

    @Override
    public Page<Activity> queryActivityListByTagId(int actIsCheck,int actIsCancel,int tagId) {
        return activityMapper.queryActivityListByTagId(actIsCheck,actIsCancel,tagId);
    }

    @Override
    public Page<Activity> queryActivityListByStatusId(int actIsCheck,int actIsCancel,int statusId) {
        return activityMapper.queryActivityListByStatusId(actIsCheck,actIsCancel,statusId);
    }

    @Override
    public List<Integer> queryActivityStartYear() {
        return activityMapper.queryActivityStartYear();
    }

    @Override
    public Page<Activity> queryActivityListByStartYear(int actIsCheck,int actIsCancel,int year) {
        return activityMapper.queryActivityListByStartYear(actIsCheck,actIsCancel,year);
    }

    @Override
    public int addActivity(Activity activity) {
        return activityMapper.addActivity(activity);
    }

    @Override
    public int addActivityUser(int actId, int userId) {

        User user = userMapper.queryUserById(userId);
        user.setUserScore(user.getUserScore()+1);
        userMapper.updateScore(user);//积分数加一
        return activityMapper.addActivityUser(actId,userId);
    }

    @Override
    public Page<Activity> queryActivityListByJoinedUser(User user) {
        return activityMapper.queryActivityListByJoinedUser(user);
    }

    @Override
    public Page<Activity> queryActivityListByLaunchedUser(User user) {
        return activityMapper.queryActivityListByLaunchedUser(user);
    }

    @Override
    public Page<Activity> queryActivityListByStartYearAndJoinedUser(int actIsCheck, int actIsCancel, int startYear, User user) {
        return activityMapper.queryActivityListByStartYearAndJoinedUser(actIsCheck, actIsCancel, startYear, user);
    }

    @Override
    public Page<Activity> queryActivityListByStatusIdAndJoinedUser(int actIsCheck, int actIsCancel, int statusId, User user) {
        return activityMapper.queryActivityListByStatusIdAndJoinedUser(actIsCheck, actIsCancel, statusId, user);
    }

    @Override
    public Page<Activity> queryActivityListByTagIdAndJoinedUser(int actIsCheck, int actIsCancel, int tagId, User user) {
        return activityMapper.queryActivityListByTagIdAndJoinedUser(actIsCheck, actIsCancel, tagId, user);
    }

    @Override
    public Page<Activity> queryActivityListByStartYearAndLaunchedUser(int actIsCheck, int actIsCancel, int startYear, User user) {
        return activityMapper.queryActivityListByStartYearAndLaunchedUser(actIsCheck, actIsCancel, startYear, user);
    }

    @Override
    public Page<Activity> queryActivityListByStatusIdAndLaunchedUser(int actIsCheck, int actIsCancel, int statusId, User user) {
        return activityMapper.queryActivityListByStatusIdAndLaunchedUser(actIsCheck, actIsCancel, statusId, user);
    }

    @Override
    public Page<Activity> queryActivityListByTagIdAndLaunchedUser(int actIsCheck, int actIsCancel, int tagId, User user) {
        return activityMapper.queryActivityListByTagIdAndLaunchedUser(actIsCheck, actIsCancel, tagId, user);
    }

    @Override
    public Page<Activity> queryActivityListByCheckAndLaunchedUser(int actIsCheck, int actIsCancel, User user) {
        return activityMapper.queryActivityListByCheckAndLaunchedUser(actIsCheck, actIsCancel, user);
    }

    @Override
    public List<Activity> queryActivityListByTop(int actIsCheck, int actIsCancel, int num) {
        return activityMapper.queryActivityListByTop(actIsCheck, actIsCancel, num);
    }

    @Override
    public int updateActivityIsCancel(Activity activity) {
        return activityMapper.updateActivityIsCancel(activity);
    }

    @Override
    public int deteleActivity(Activity activity) {
        // 1先把参与的用户搜出来  邮箱告知
        //2正式取消（活动表关联的也去掉或者不用管）

//        activityMapper.deteleActivityUser()
//        activityMapper.deteleActivityTeam()
//        activityMapper.deteleActivityComment()

        return activityMapper.deteleActivity(activity);
    }

    @Override
    public int updateActivityIsCheck(Activity activity) {
        return activityMapper.updateActivityIsCheck(activity);
    }

    @Override
    public Page<Activity> queryActivityListBySearch(int actIsCheck, int actIsCancel, Activity activity) {
        return activityMapper.queryActivityListBySearch(actIsCheck, actIsCancel, activity);
    }

    @Override
    public int addActivityTeam(Team team) {
        return activityMapper.addActivityTeam(team);
    }
}
