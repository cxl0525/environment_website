create database environment;

use environment;


create table User(

userId int not null auto_increment COMMENT '用户id',
userName varchar(50) not null COMMENT '用户名',
userPassword varchar(50) not null COMMENT '密码',
userRealName varchar(50) not null COMMENT '真实姓名',
userGender varchar(2) not null COMMENT '性别',
userPhone varchar(50) not null COMMENT '电话号码',
userIdentity varchar(50) not null COMMENT '身份证',
userEmail varchar(50) not null COMMENT '邮箱',
userAddress varchar(100) not null  COMMENT'地址',
userQQId varchar(50) COMMENT 'qq号',
userWeChatId varchar(50) COMMENT '微信号',
userAvatar varchar(255) not null COMMENT '头像',
userCreateDate datetime not null COMMENT '创建日期',
userIsCheck int not null COMMENT '是否已审核 1完美的   2已停用  3删除  4彻底删除  0待审核',
PRIMARY KEY ( `userId` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table Score(
userId int not null auto_increment COMMENT '用户id',
userScore int not null COMMENT '积分数',
PRIMARY KEY ( `userId` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;




create table Rate(
rateId int not null auto_increment COMMENT '等级id',
rateName varchar(50) not null COMMENT '等级名称',
rateMinScore int not null COMMENT '等级分数最低值',
rateMaxScore int not null COMMENT '等级分数最高值',
PRIMARY KEY ( `rateId` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



create table Activity(
actId int not null auto_increment COMMENT '活动id',
actName varchar(100) not null COMMENT '活动名称',
actImg varchar(255) not null COMMENT '活动海报',
actStartDate datetime not null COMMENT '活动开始日期',
actDeadLine datetime not null COMMENT '活动截止日期',
actRegDate datetime not null COMMENT '报名开始时间',
actRegDeadline datetime not null COMMENT '报名截止时间',
actPlace varchar(100) not null COMMENT '地点',
actNumber int not null  COMMENT'人数限制',
actRequire varchar(100) not null COMMENT '报名要求',
actSlogan varchar(100) not null COMMENT '宣传口号',
actScore int not null COMMENT '活动积分',
actProfile text not null COMMENT '活动简介',
actStatus int not null COMMENT '活动状态0待审  1通过  2未通过',
actTag int not null COMMENT '标签',
actIsCheck int not null COMMENT '活动是否通过检验',
actIsCancel int not null COMMENT '活动取消',
actPromoter int not null COMMENT '活动发起人Id',
PRIMARY KEY ( `actId` ),
FOREIGN KEY (actPromoter) REFERENCES User (userId)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;



create table Status(
statusId int not null auto_increment COMMENT '状态id',
statusName varchar(50) not null COMMENT '状态名称',
PRIMARY KEY ( `statusId` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table Tag(
tagId int not null auto_increment COMMENT '标签id',
tagName varchar(50) not null COMMENT '标签名称',
PRIMARY KEY ( `tagId` )

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table News(

newsId int not null auto_increment COMMENT '新闻id',
newsTitle varchar(50) not null COMMENT '新闻标题',
newsContent text not null COMMENT '新闻内容',
newsReleaseDate datetime not null COMMENT '新闻发布日期',
newsPublisher int not null COMMENT '新闻发布作者id',
newsImagePath varchar(255) COMMENT '新闻配图',
newsVideoPath varchar(255) COMMENT '新闻视频',
newsIsCheck int not null COMMENT '新闻是否通过检验',
newsClicks int not null COMMENT '新闻点击量',
newsTag int not null COMMENT '新闻标签',
PRIMARY KEY (`newsId`),
FOREIGN KEY (newsPublisher) REFERENCES User (userId),
FOREIGN KEY (newsTag) REFERENCES Tag (tagId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table Team(

teamId int not null auto_increment COMMENT '团队id',
teamName varchar(50) not null COMMENT '团队名称',
teamLeader int not null COMMENT '队长Id',
teamPlayerInfo text not null COMMENT '队员信息',
teamNumber int not null COMMENT '队员人数',
teamAct int not null  COMMENT '团队所属活动id',
PRIMARY KEY (`teamId`),
FOREIGN KEY (teamAct) REFERENCES Activity (actId),
FOREIGN KEY (teamLeader) REFERENCES User (userId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table UserActivity(

actId int not null  COMMENT '活动id',
userId int not null COMMENT '用户id',
PRIMARY KEY (`actId`,`userId`),
FOREIGN KEY (actId) REFERENCES Activity (actId),
FOREIGN KEY (userId) REFERENCES User (userId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;





create table Comment(

comId int not null auto_increment COMMENT '评论id',
comUser int not null COMMENT '用户id',
comContent text not null COMMENT '评论内容',
comCreateTime datetime not null COMMENT '创建时间',
comParentComment int  COMMENT '父评论id',
comLikeNum int COMMENT '点赞数',
PRIMARY KEY (`comId`),
FOREIGN KEY (comUser) REFERENCES User (userId),
FOREIGN KEY (comParentComment) REFERENCES Comment (comId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table ActivityComment(

actId int not null  COMMENT '活动id',
comId int not null COMMENT '评论id',
PRIMARY KEY (`actId`,`comId`),
FOREIGN KEY (actId) REFERENCES Activity (actId),
FOREIGN KEY (comId) REFERENCES Comment (comId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table Type(
typeId int not null auto_increment COMMENT '类型id',
typeName varchar(50) not null COMMENT '类型名称',
typeParent int   COMMENT '类型父亲',
PRIMARY KEY ( `typeId` ),
FOREIGN KEY (typeParent) REFERENCES Type (typeId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table Picture(

picId int not null auto_increment COMMENT '图片id',
picImagePath varchar(255) not null COMMENT '图片地址',
picUser int not null COMMENT '上传者id',
picClicks int not null COMMENT '图片点击量',
picType int not null COMMENT '类型id',
picName varchar(255) not null COMMENT '图片名称',
PRIMARY KEY (`picId`),
FOREIGN KEY (picUser) REFERENCES User (userId),
FOREIGN KEY (picType) REFERENCES Type (typeId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table Advertisement(

adId int not null auto_increment COMMENT '广告id',
adTitle varchar(255) not null COMMENT '广告标题',
adContent int not null COMMENT '广告内容',
adLink int not null COMMENT '链接地址',
adImagePath int not null COMMENT '图片路径',
adExpirationDate datetime not null COMMENT '过期日期',
adCreateDate datetime not null COMMENT '创建时间',
PRIMARY KEY (`adId`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table UserCommentLike(
userId int not null  COMMENT '用户id',
comId int not null COMMENT '评论id',
PRIMARY KEY (`userId`,`comId`),
FOREIGN KEY (userId) REFERENCES user (userId),
FOREIGN KEY (comId) REFERENCES Comment (comId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;




create table contactUs(
contId int not null auto_increment COMMENT '联系我id',
contUser  int not null  COMMENT '用户id',
contContent text not null COMMENT '信息内容',
contReply text  COMMENT '信息回复',
PRIMARY KEY ( `contId` ),
FOREIGN KEY (contUser) REFERENCES User (userId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 查看是否开启定时器
SHOW VARIABLES LIKE 'event_scheduler';
-- 开启定时器 0：off 1：on
SET GLOBAL event_scheduler = 1;


-- 开启event_scheduler SQL指令
SET GLOBAL event_scheduler = ON;
SET @@global.event_scheduler = ON;
SET GLOBAL event_scheduler = 1;
SET @@global.event_scheduler = 1;


-- 定义存储过程
DELIMITER |
DROP PROCEDURE IF EXISTS update_remind_status |
CREATE PROCEDURE update_remind_status()
BEGIN
    IF exists (select actId from activity where SYSDATE()<actRegDate) THEN
            update activity set `actStatus`=1 where SYSDATE()<remind_time and `actStatus`!=1;
    END IF;
		IF exists (select actId from activity where SYSDATE()>=actRegDate and  SYSDATE()<=actRegDeadline) THEN
            update activity set `actStatus`=2 where SYSDATE()>=actRegDate and  SYSDATE()<=actRegDeadline and `actStatus`!=2;
    END IF;
		IF exists (select actId from activity where SYSDATE()>actRegDeadline and SYSDATE()<actStartDate) THEN
            update activity set `actStatus`=3 where SYSDATE()>actRegDeadline and SYSDATE()<actStartDate and `actStatus`!=3;
    END IF;
		IF exists (select actId from activity where SYSDATE()>=actStartDate and SYSDATE()<=actDeadLine) THEN
            update activity set `actStatus`=4 where SYSDATE()>=actStartDate and SYSDATE()<=actDeadLine and `actStatus`!=4;
    END IF;
		IF exists (select actId from activity where SYSDATE()>actDeadLine) THEN
            update activity set `actStatus`=5 where SYSDATE()>actDeadLine and `actStatus`!=5;
    END IF;
END
 |
DELIMITER;


--创建定时器，每间隔一秒调用一次存储过程。
DELIMITER //
CREATE EVENT  event_remind_status
ON SCHEDULE EVERY 1 second  do
begin
call update_remind_status();
end //
DELIMITER;

-- 启动定时器
ALTER EVENT event_remind_status ON
COMPLETION PRESERVE ENABLE;


SHOW VARIABLES LIKE 'event_scheduler';
SET GLOBAL event_scheduler = 1;


SET GLOBAL event_scheduler = ON;
SET @@global.event_scheduler = ON;
SET GLOBAL event_scheduler = 1;
SET @@global.event_scheduler = 1;


---------------------------------
INSERT INTO `user` (userName ,userPassword ,userRealName ,userGender ,userPhone ,userIdentity ,userEmail ,userAddress,userQQId,userWeChatId ,
userAvatar,userCreateDate,userIsCheck)
VALUES ('l','156','里','1','61651','466561','163@qq.com','sss','651651','s1xs','xnsnx','2019-05-19 18:18:00',1)
INSERT INTO `user` (userName ,userPassword ,userRealName ,userGender ,userPhone ,userIdentity ,userEmail ,userAddress,userQQId,userWeChatId ,
userAvatar,userCreateDate,userIsCheck)
VALUES ('admin',	'admin'	,'网站管理员',	'男',	'10086',	'xxx'	,'1530464203@qq.com','青岛-崂山区',	'119',	'120',	'xx',,'2019-05-19 18:18:00',1)


insert into status (statusName)
VALUES
('准备招募'),
('正在招募中'),
('招募完成'),
('正在进行中'),
('已结束');


insert into tag (tagName)
VALUES
('植树防沙'),
('海洋保护'),
('垃圾分类'),
('清洁水源'),
('动物保护'),
('培训讲座');

insert into activity (actName,actImg,	actStartDate,	actDeadLine,	actRegDate,	actRegDeadline,	actPlace,	actNumber,	actRequire,	actSlogan,	actScore,	actProfile,	actStatus,	actTag,	actIsCheck,	actIsCancel,	actPromoter)
values('1','1','2019-05-19 18:18:00','2019-05-19 18:18:00','2019-05-19 18:18:00','2019-05-19 18:18:00','1',1,'1','1',2,'1',1,1,1,1,1)


insert into `comment` (comUser,comContent	,comCreateTime,	comParentComment,	comLikeNum)
values(1,'啦啦啦','2020-05-10 20:20:20',1,1)
INSERT INTO activitycomment VALUES(156,1);
INSERT INTO activitycomment VALUES(156,2);
INSERT INTO activitycomment VALUES(156,3);
INSERT INTO activitycomment VALUES(156,4);
INSERT INTO activitycomment VALUES(156,5);

SELECT c.*,u.* FROM `comment` c,activitycomment ac,activity a,`user`u
WHERE u.userId = c.comUser and  c.comId = ac.comId AND ac.actId =a.actId  And a.actId = 156


   <select id="queryCommentListOneNoChildByKeyWord" resultMap="CommentMap">
        SELECT * FROM `comment` c1
LEFT JOIN `user` u on c1.comUser = u.userId
WHERE c1.comId
not in (SELECT comId FROM activitycomment )
AND c1.comParentComment is NULL
<if  test="keyWords != null and keyWords.size() > 0">
  and
<foreach collection="keyWords" item="item" index="index" open="(" separator="or" close=")">
-- c1.comContent LIKE '%${item}%'
c1.comContent LIKE  CONCAT('%',#{item},'%')
</foreach>
</if>
    </select>


insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('xx',1,10,5,'哈哈')
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');
insert into Picture(picImagePath,picUser,picClicks,picType,picName)
VALUES('/files-pic/2020-05-24/2020052421184509dd9876492e4dd198e3b3556e8236b9.jpg',3,10,6,'jim');


INSERT INTO news (newsTitle,	newsContent	,newsReleaseDate,	newsPublisher,	newsImagePath	,newsVideoPath,	newsIsCheck	,newsClicks	,newsTag)
VALUES ('杨柳漫天飞絮太烦人了，当初为啥要播种？','离开江南二十多年，很多事情记得已经不是很真切了，也淡忘了江南柳絮飞舞的情景。在北京柳絮是遭人厌烦的，每年的晚春，帝都的柳絮如曹雪芹在《红楼梦》描绘的大雪，飞棉扯絮一般漫天飞舞，不住飘到眼睛里，说话间就会有柳絮乘机钻进嘴里。周末找个雅致的露天餐厅享受闲暇，柳絮也不时来捣乱，尽管不停的赶，稍不留神就飘进菜里、碗里，令人防不胜防。柳絮几乎是无孔不入，门窗关得再紧，屋内总能找到一团一团的柳絮，扫把一扫就飞了起来，很难收拾干净。落到地上的柳絮到是有些趣味，自动呼朋唤友在墙角、街沿打转像雪球一样很快滚成一大团，聚成堆，连绵起来很长有些壮观。落到草地上的柳絮飞不动了，就地铺在草地上，远看像覆盖了厚厚的雪，白雪中探出绿草，仿佛下了一场春雪，却没有丝毫的凉意，着实有些个浪漫。影楼发现了商机，给青年的男女拍婚纱，当成一道风景。'
,'2020-05-19 18:18:00','1','/images/landscape10.jpg',NULL,1,10,1)




INSERT INTO rate(rateName,rateMinScore,rateMaxScore)
 VALUES
 ('1',0,2),
 ('2',3,8),
 ('3',9,17),
 ('4',18,29),
 ('5',30,44),
 ('6',45,62),
 ('7',63,83),
 ('8',84,107),
 ('9',108,134),
 ('10',135,10000)