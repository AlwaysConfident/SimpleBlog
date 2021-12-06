package com.simpleblog.chen.dao;

import com.simpleblog.chen.pojo.AdminPermission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPermissionDAO extends JpaRepository<AdminPermission, Integer> {
}
