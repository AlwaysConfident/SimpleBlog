package com.simpleblog.chen.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.simpleblog.chen.dao.AdminPermissionDAO;
import com.simpleblog.chen.dao.AdminRolePermissionDAO;
import com.simpleblog.chen.pojo.AdminPermission;
import com.simpleblog.chen.pojo.AdminRole;
import com.simpleblog.chen.pojo.AdminRolePermission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminPermissionService {
    @Autowired
    AdminPermissionDAO adminPermissionDAO;

    @Autowired
    AdminRoleService adminRoleService;

    @Autowired
    AdminRolePermissionDAO adminRolePermissionDAO;

    @Autowired
    AdminRolePermissionService adminRolePermissionService;

    // 获取用户权限，即可以访问的url
    public Set<String> listPermissionURLsByUser(String userName) {
        // 由用户名查询角色id
        List<Integer> rids = adminRoleService.listRolesByUser(userName).stream().map(AdminRole::getId)
                .collect(Collectors.toList());
        // 由角色id查询权限id
        List<Integer> pids = adminRolePermissionDAO.findAllByRidIn(rids).stream().map(AdminRolePermission::getPid)
                .collect(Collectors.toList());
        // 由权限id查询权限
        List<AdminPermission> perms = adminPermissionDAO.findAllById(pids);
        // 由权限查询可以访问的url
        Set<String> URLs = perms.stream().map(AdminPermission::getUrl).collect(Collectors.toSet());
        return URLs;
    }

    public boolean needFilter(String requestAPI) {
        List<AdminPermission> ps = adminPermissionDAO.findAll();
        for (AdminPermission p : ps) {
            if (p.getUrl().equals(requestAPI)) {
                return true;
            }
        }
        return false;
    }

    public List<AdminPermission> listAllPerm() {
        return adminPermissionDAO.findAll();
    }

    public List<AdminPermission> listPermsByRid(int rid) {
        List<Integer> pids = adminRolePermissionService.findAllByRid(rid).stream().map(AdminRolePermission::getPid)
                .collect(Collectors.toList());
        return adminPermissionDAO.findAllById(pids);
    }
}
