package com.qdu.service;

import com.github.pagehelper.Page;
import com.qdu.pojo.Activity;
import com.qdu.pojo.Team;
import com.qdu.pojo.User;

import java.util.List;

public interface ActivityService {
    Page<Activity> queryActivityListByPage(int actIsCheck, int actIsCancel);
    Activity queryActivityById(int actId);

    Page<Activity> queryActivityListByTagId(int actIsCheck, int actIsCancel, int tagId);

    Page<Activity> queryActivityListByStatusId(int actIsCheck, int actIsCancel, int statusId);
    List<Integer> queryActivityStartYear();
    Page<Activity> queryActivityListByStartYear(int actIsCheck, int actIsCancel, int year);
    int addActivity(Activity activity);

    int addActivityUser(int actId, int userId);

    Page<Activity> queryActivityListByJoinedUser(User user);

    Page<Activity> queryActivityListByLaunchedUser(User user);

    Page<Activity> queryActivityListByStartYearAndJoinedUser(int actIsCheck, int actIsCancel, int startYear, User user);

    Page<Activity> queryActivityListByStatusIdAndJoinedUser(int actIsCheck, int actIsCancel, int statusId, User user);

    Page<Activity> queryActivityListByTagIdAndJoinedUser(int actIsCheck, int actIsCancel, int tagId, User user);

    Page<Activity> queryActivityListByStartYearAndLaunchedUser(int actIsCheck, int actIsCancel, int startYear, User user);

    Page<Activity> queryActivityListByStatusIdAndLaunchedUser(int actIsCheck, int actIsCancel, int statusId, User user);

    Page<Activity> queryActivityListByTagIdAndLaunchedUser(int actIsCheck, int actIsCancel, int tagId, User user);

    Page<Activity> queryActivityListByCheckAndLaunchedUser(int actIsCheck, int actIsCancel, User user);

    List<Activity> queryActivityListByTop(int actIsCheck, int actIsCancel, int num);

    int updateActivityIsCancel(Activity activity);

    int deteleActivity(Activity activity);

    int updateActivityIsCheck(Activity activity);

    Page<Activity> queryActivityListBySearch(int actIsCheck, int actIsCancel, Activity activity);

    int addActivityTeam(Team team);
}
