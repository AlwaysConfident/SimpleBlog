package com.simpleblog.chen.service;

import com.simpleblog.chen.dao.BookDAO;
import com.simpleblog.chen.pojo.Book;
import com.simpleblog.chen.pojo.Category;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  @Autowired
  BookDAO bookDAO;

  @Autowired
  CategoryService categoryService;

  public List<Book> listAllBooks() {
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    return bookDAO.findAll(sort);
  }

  public void addOrUpdateBook(Book book) {
    bookDAO.save(book);
  }

  public void deleteBookById(int id) {
    bookDAO.deleteById(id);
  }

  public List<Book> listBooksByCategory(int cid) {
    Category c = categoryService.getById(cid);
    return bookDAO.findAllByCategory(c);
  }

  public List<Book> searchBooks(String keywords) {
    return bookDAO.findAllByTitleLikeOrAuthorLike('%' + keywords + '%', '%' + keywords + '%');
  }
}
