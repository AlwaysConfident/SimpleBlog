package com.simpleblog.chen.service;

import com.simpleblog.chen.dao.CategoryDAO;
import com.simpleblog.chen.pojo.Category;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  @Autowired
  CategoryDAO categoryDAO;

  public List<Category> listAllCategory() {
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    return categoryDAO.findAll(sort);
  }

  public Category getById(int id) {
    return categoryDAO.findById(id).orElse(null);
  }
}
