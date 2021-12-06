package com.simpleblog.chen.service;

import java.util.ArrayList;
import java.util.List;

import com.simpleblog.chen.dao.AdminUserRoleDAO;
import com.simpleblog.chen.pojo.AdminRole;
import com.simpleblog.chen.pojo.AdminUserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminUserRoleService {
    @Autowired
    AdminUserRoleDAO adminUserRoleDAO;

    public List<AdminUserRole> listAllByUid(int uid) {
        return adminUserRoleDAO.findAllByUid(uid);
    }

    @Transactional // 表示事务
    public void saveRoleChanges(int uid, List<AdminRole> roles) {
        adminUserRoleDAO.deleteAllByUid(uid);
        List<AdminUserRole> urs = new ArrayList<>();
        roles.forEach(r -> {
            AdminUserRole ur = new AdminUserRole();
            ur.setUid(uid);
            ur.setRid(r.getId());
            urs.add(ur);
        });
        adminUserRoleDAO.saveAll(urs);
    }

    public void deleteByUid(int uid) {
        adminUserRoleDAO.deleteAllByUid(uid);
    }

    public void deleteByRid(int rid) {
        adminUserRoleDAO.deleteAllByRid(rid);
    }
}
