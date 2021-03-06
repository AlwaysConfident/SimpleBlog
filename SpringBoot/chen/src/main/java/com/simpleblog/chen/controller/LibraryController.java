package com.simpleblog.chen.controller;

import com.simpleblog.chen.pojo.Book;
import com.simpleblog.chen.service.BookService;
import com.simpleblog.chen.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CrossOrigin
public class LibraryController {

  @Autowired
  BookService bookService;

  @GetMapping("/api/books")
  @ResponseBody
  public List<Book> listAllBooks() throws Exception {
    return bookService.listAllBooks();
  }

  @PostMapping("/api/books")
  @ResponseBody
  public Book addOrUpdateBook(@RequestBody Book book) throws Exception {
    bookService.addOrUpdateBook(book);
    return book;
  }

  @PostMapping("/api/deleteBook")
  @ResponseBody
  public void deleteBook(@RequestBody Book book) throws Exception {
    bookService.deleteBookById(book.getId());
  }

  @GetMapping("/api/categories/{cid}/books")
  @ResponseBody
  public List<Book> listAllBooksByCategory(@PathVariable("cid") int cid)
    throws Exception {
    if (cid != 0) {
      return bookService.listBooksByCategory(cid);
    } else {
      return listAllBooks();
    }
  }

  @GetMapping("/api/searchBooks")
  @ResponseBody
  public List<Book> searchBooks(
    @RequestParam(value = "keywords") String keywords
  )
    throws Exception {
    if (keywords.equals("")) {
      return listAllBooks();
    } else {
      return bookService.searchBooks(keywords);
    }
  }

  @PostMapping("/api/covers")
  @ResponseBody
  public String coversUpload(MultipartFile file) throws Exception { // MultipartFile:SpringMVC?????????????????????????????????
    String folder = "D:/Work/GitHub/SimpleBlog/Workspace/img";
    File imageFolder = new File(folder); // ??????????????????????????????????????????????????????????????????????????? File ??????
    File f = new File(
      imageFolder,
      StringUtils.generateRamdomString(6) +
      file
        .getOriginalFilename()
        .substring(file.getOriginalFilename().length() - 4)
    ); // ???????????????????????????????????????????????????????????????????????????File?????????
    if (!f.getParentFile().exists()) f.getParentFile().mkdir();
    try {
      file.transferTo(f); // ?????????????????????????????????????????????????????????
      String imgURL = "http://localhost:8080/api/file/" + f.getName();
      return imgURL;
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
  }
}
