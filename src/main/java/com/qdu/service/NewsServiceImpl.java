package com.qdu.service;

import com.github.pagehelper.Page;
import com.qdu.mapper.NewsMapper;
import com.qdu.pojo.News;
import com.qdu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService{
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public Page<News> queryNewsListByPage(int isNewsIsCheck, int score) {
        return newsMapper.queryNewsListByPage(isNewsIsCheck,score);
    }

    @Override
    public News queryNewsById(int newsId) {
        return newsMapper.queryNewsById(newsId);
    }

    @Override
    public Page<News> queryNewsListByReleaseYearAndMonth(int isNewsIsCheck, int score, int releaseYear, int releaseMonth) {
        return newsMapper.queryNewsListByReleaseYearAndMonth(isNewsIsCheck,score,releaseYear,releaseMonth);
    }

    @Override
    public Page<News> queryNewsListByTagId(int isNewsIsCheck, int score, int tagId) {
        return newsMapper.queryNewsListByTagId(isNewsIsCheck,score,tagId);
    }

    @Override
    public int updateNews(News news) {
        return newsMapper.updateNews(news);
    }

    @Override
    public int addNews(News news) {
        return newsMapper.addNews(news);
    }

    @Override
    public Page<News> queryNewsListByLaunchedUser(User user) {
        return newsMapper.queryNewsListByLaunchedUser(user);
    }

    @Override
    public Page<News> queryNewsListByReleaseYearAndMonthAndLaunchedUser(int isNewsIsCheck, int score, int releaseYear, int releaseMonth, User user) {
        return newsMapper.queryNewsListByReleaseYearAndMonthAndLaunchedUser(isNewsIsCheck, score, releaseYear, releaseMonth, user);
    }

    @Override
    public Page<News> queryNewsListByTagIdAndLaunchedUser(int isNewsIsCheck, int score, int tagId, User user) {
        return newsMapper.queryNewsListByTagIdAndLaunchedUser(isNewsIsCheck, score, tagId, user);
    }

    @Override
    public List<News> queryNewsListByTop(int isNewsIsCheck, int score, int num) {
        return newsMapper.queryNewsListByTop(isNewsIsCheck, score, num);
    }

    @Override
    public List<News> queryBlogListByPage() {
        return newsMapper.queryBlogListByPage();
    }

    @Override
    public int deteleNews(News news) {
        return newsMapper.deteleNews(news);
    }

    @Override
    public Page<News> queryBlogListBySearch(News news) {
        return newsMapper.queryBlogListBySearch(news);
    }


}
