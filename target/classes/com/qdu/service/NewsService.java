package com.qdu.service;

import com.github.pagehelper.Page;
import com.qdu.pojo.News;
import com.qdu.pojo.User;

import java.util.List;

public interface NewsService {
    Page<News> queryNewsListByPage(int isNewsIsCheck, int score);

    News queryNewsById(int newsId);

    Page<News> queryNewsListByReleaseYearAndMonth(int isNewsIsCheck, int score, int releaseYear, int releaseMonth);

    Page<News> queryNewsListByTagId(int isNewsIsCheck, int score, int tagId);

    int updateNews(News news);
    int addNews(News news);

    Page<News> queryNewsListByLaunchedUser(User user);

    Page<News> queryNewsListByReleaseYearAndMonthAndLaunchedUser(int isNewsIsCheck, int score, int releaseYear, int releaseMonth, User user);

    Page<News> queryNewsListByTagIdAndLaunchedUser(int isNewsIsCheck, int score, int tagId, User user);

    List<News> queryNewsListByTop(int isNewsIsCheck, int score, int num);

    List<News> queryBlogListByPage();

    int deteleNews(News news);

    Page<News> queryBlogListBySearch(News news);
}
