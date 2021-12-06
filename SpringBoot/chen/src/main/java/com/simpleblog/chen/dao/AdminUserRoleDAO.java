package com.simpleblog.chen.dao;

import java.util.List;

import com.simpleblog.chen.pojo.AdminUserRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRoleDAO extends JpaRepository<AdminUserRole, Integer> {
    List<AdminUserRole> findAllByUid(int uid);

    void deleteAllByUid(int uid);

    void deleteAllByRid(int rid);
}
