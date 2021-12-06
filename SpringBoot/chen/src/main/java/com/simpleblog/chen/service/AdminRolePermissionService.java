package com.simpleblog.chen.service;

import java.util.ArrayList;
import java.util.List;

import com.simpleblog.chen.dao.AdminRolePermissionDAO;
import com.simpleblog.chen.pojo.AdminPermission;
import com.simpleblog.chen.pojo.AdminRolePermission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRolePermissionService {
    @Autowired
    AdminRolePermissionDAO adminRolePermissionDAO;

    public void savePermChanges(int rid, List<AdminPermission> permissions) {
        if (permissions != null) {
            adminRolePermissionDAO.deleteAllByRid(rid);
            List<AdminRolePermission> rps = new ArrayList<>();
            permissions.forEach(p -> {
                AdminRolePermission rp = new AdminRolePermission();
                rp.setRid(rid);
                rp.setPid(p.getId());
                rps.add(rp);
            });
            adminRolePermissionDAO.saveAll(rps);
        }
    }

    public List<AdminRolePermission> findAllByRid(int rid) {
        return adminRolePermissionDAO.findAllByRid(rid);
    }

    public void deleteByRid(int rid) {
        adminRolePermissionDAO.deleteAllByRid(rid);
    }
}
