package com.simpleblog.chen.service;

import java.util.List;
import java.util.stream.Collectors;

import com.simpleblog.chen.dao.AdminRoleDAO;
import com.simpleblog.chen.dao.AdminUserRoleDAO;
import com.simpleblog.chen.pojo.AdminMenu;
import com.simpleblog.chen.pojo.AdminPermission;
import com.simpleblog.chen.pojo.AdminRole;
import com.simpleblog.chen.pojo.AdminUserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleService {
    @Autowired
    AdminRoleDAO adminRoleDAO;

    @Autowired
    UserService userService;

    @Autowired
    AdminUserRoleService adminUserRoleService;

    @Autowired
    AdminUserRoleDAO adminUserRoleDAO;

    @Autowired
    AdminRolePermissionService adminRolePermissionService;

    @Autowired
    AdminPermissionService adminPermissionService;

    @Autowired
    AdminMenuService adminMenuService;

    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public List<AdminRole> listRolesByUser(String userName) {
        int uid = userService.getUserByName(userName).getId();
        List<Integer> rids = adminUserRoleService.listAllByUid(uid).stream().map(AdminUserRole::getRid)
                .collect(Collectors.toList());
        return adminRoleDAO.findAllById(rids);
    }

    public List<AdminRole> listAllRoles() {
        return adminRoleDAO.findAll();
    }

    public void addRole(AdminRole role) {
        adminRoleDAO.save(role);
        adminRolePermissionService.savePermChanges(role.getId(), role.getPermissions());
    }

    public AdminRole updateRoleStatus(AdminRole role) {
        AdminRole roleInDB = adminRoleDAO.findById(role.getId());
        roleInDB.setEnabled(role.isEnabled());
        return adminRoleDAO.save(roleInDB);
    }

    public void addOrUpdate(AdminRole role) {
        adminRoleDAO.save(role);
    }

    public List<AdminRole> listWithPermsAndMenus() {
        List<AdminRole> roles = adminRoleDAO.findAll();
        List<AdminPermission> perms;
        List<AdminMenu> menus;
        for (AdminRole role : roles) {
            perms = adminPermissionService.listPermsByRid(role.getId());
            menus = adminMenuService.getMenusByRid(role.getId());
            role.setPermissions(perms);
            role.setMenus(menus);
        }
        return roles;
    }

    public void deleteByRid(int rid) {
        adminRoleDAO.deleteById(rid);
        adminRoleMenuService.deleteByRid(rid);
        adminRolePermissionService.deleteByRid(rid);
        adminUserRoleService.deleteByRid(rid);
    }
}
