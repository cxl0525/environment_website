package com.qdu.mapper;

import com.github.pagehelper.Page;
import com.qdu.pojo.Activity;
import com.qdu.pojo.Picture;
import com.qdu.pojo.Team;
import com.qdu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Mapper
@Repository
public interface ActivityMapper {
    List<Activity> queryActivityList();
    Activity queryActivityById(int actId);
    Page<Activity> queryActivityListByPage(int actIsCheck, int actIsCancel);
    Page<Activity> queryActivityListByTagId(int actIsCheck, int actIsCancel, int tagId);
    Page<Activity> queryActivityListByStatusId(int actIsCheck, int actIsCancel, int statusId);
    List<Integer> queryActivityStartYear();
    Page<Activity> queryActivityListByStartYear(int actIsCheck, int actIsCancel, int year);
    int addActivityUser(int actId, int userId);
    int addActivity(Activity activity);

    /*参与的活动
     * */
    Page<Activity> queryActivityListByJoinedUser(User user);

    Page<Activity> queryActivityListByStartYearAndJoinedUser(int actIsCheck, int actIsCancel, int startYear, User user);

    Page<Activity> queryActivityListByStatusIdAndJoinedUser(int actIsCheck, int actIsCancel, int statusId, User user);

    Page<Activity> queryActivityListByTagIdAndJoinedUser(int actIsCheck, int actIsCancel, int tagId, User user);

/*发起的活动
* */
    Page<Activity> queryActivityListByLaunchedUser(User user);

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


    List<Team> queryTeamListByActivityId(int actId);
    Integer  queryTeamPlayerNumberByActivityId(int actId);
}
