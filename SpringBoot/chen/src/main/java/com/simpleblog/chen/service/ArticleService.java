package com.simpleblog.chen.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.simpleblog.chen.dao.ArticleDAO;
import com.simpleblog.chen.pojo.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    ArticleDAO articleDAO;

    public void postArticle(Article article) {
        LocalDateTime dateTime = LocalDateTime.now();
        article.setArticleDate(dateTime);
        articleDAO.save(article);
    }

    // 分页
    public Page<Article> list(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return articleDAO.findAll(PageRequest.of(page, size, sort));
    }

    public void deleteArticleById(int id) {
        articleDAO.deleteById(id);
    }

    public Article findArticleById(int id) {
        return articleDAO.findById(id);
    }

}
