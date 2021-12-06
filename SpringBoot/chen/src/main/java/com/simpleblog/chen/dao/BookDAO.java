package com.simpleblog.chen.dao;

import com.simpleblog.chen.pojo.Book;
import com.simpleblog.chen.pojo.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDAO extends JpaRepository<Book, Integer> {
  List<Book> findAllByCategory(Category category);

  List<Book> findAllByTitleLikeOrAuthorLike(String keyword1, String keyword2);
}
