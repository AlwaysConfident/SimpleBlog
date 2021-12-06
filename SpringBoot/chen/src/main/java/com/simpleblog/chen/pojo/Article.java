package com.simpleblog.chen.pojo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "article")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "文章标题不能为空")
    @Column(name = "article_title")
    private String articleTitle;

    @Column(name = "article_content_html")
    private String articleContentHtml;

    @Column(name = "article_content_md")
    private String articleContentMd;

    @Column(name = "article_abstract")
    private String articleAbstract;

    @Column(name = "article_cover")
    private String articleCover;

    @Column(name = "article_date")
    private LocalDateTime articleDate;
}
