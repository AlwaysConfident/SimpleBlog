package com.simpleblog.chen.dao;

import com.simpleblog.chen.pojo.Article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleDAO extends JpaRepository<Article, Integer> {
    Article findById(int id);
}
