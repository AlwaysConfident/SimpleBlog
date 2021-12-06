package com.simpleblog.chen.dao;

import java.util.List;

import com.simpleblog.chen.pojo.AdminMenu;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMenuDAO extends JpaRepository<AdminMenu, Integer> {
    List<AdminMenu> findAllByParentId(int id);
}
