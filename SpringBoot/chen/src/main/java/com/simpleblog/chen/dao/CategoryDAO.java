package com.simpleblog.chen.dao;

import com.simpleblog.chen.pojo.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category,Integer>{
    
}
