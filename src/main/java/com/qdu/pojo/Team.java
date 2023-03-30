package com.qdu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    private int teamId;
    private String teamName;
    private User teamLeader;
    private String teamPlayerInfo;
    private int teamNumber;
    private Activity teamAct;

}
