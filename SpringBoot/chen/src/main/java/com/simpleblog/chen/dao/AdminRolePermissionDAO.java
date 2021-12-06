package com.simpleblog.chen.dao;

import java.util.List;

import com.simpleblog.chen.pojo.AdminRolePermission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRolePermissionDAO extends JpaRepository<AdminRolePermission, Integer> {
    List<AdminRolePermission> findAllByRidIn(List<Integer> rids);

    void deleteAllByRid(int rid);

    List<AdminRolePermission> findAllByRid(int rid);
}
