package com.simpleblog.chen.dao;

import com.simpleblog.chen.pojo.AdminRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRoleDAO extends JpaRepository<AdminRole, Integer> {
    AdminRole findById(int id);
}
