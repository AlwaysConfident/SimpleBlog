package com.simpleblog.chen.controller;

import java.io.File;

import com.simpleblog.chen.pojo.Article;
import com.simpleblog.chen.service.ArticleService;
import com.simpleblog.chen.utils.Response;
import com.simpleblog.chen.utils.ResponseFactory;
import com.simpleblog.chen.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ArticleContoller {
    @Autowired
    ArticleService articleService;

    @PostMapping("/api/admin/content/article")
    public Response postArticle(@RequestBody Article article) {
        articleService.postArticle(article);
        return ResponseFactory.buildSucceResponse("Article Posted");
    }

    @DeleteMapping("/api/admin/content/article/{id}")
    public Response deleteArticle(@PathVariable("id") int id) {
        articleService.deleteArticleById(id);
        return ResponseFactory.buildSucceResponse("Delete Success");
    }

    @PostMapping("api/admin/content/books/covers")
    public String coversUpload(MultipartFile file) throws Exception {
        String folder = "D:/Work/GitHub/SimpleBlog/Workspace/articleImg";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.generateRamdomString(6)
                + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdir();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8080/api/file/" + f.getName();
            return imgURL;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("/api/article/{id}")
    public Response getArticleById(@PathVariable("id") int id) {
        return ResponseFactory.buildSucceResponse(articleService.findArticleById(id));
    }

    @GetMapping("/api/article/{size}/{page}")
    public Response listArticles(@PathVariable("size") int size, @PathVariable("page") int page) {
        return ResponseFactory.buildSucceResponse(articleService.list(page - 1, size));
    }
}
