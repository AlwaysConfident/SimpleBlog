package com.simpleblog.chen.dao;

import java.util.List;

import com.simpleblog.chen.pojo.AdminRoleMenu;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRoleMenuDAO extends JpaRepository<AdminRoleMenu, Integer> {
    List<AdminRoleMenu> findAllByRidIn(List<Integer> rids);

    List<AdminRoleMenu> findAllByRid(int rid);

    void deleteAllByRid(int rid);
}
