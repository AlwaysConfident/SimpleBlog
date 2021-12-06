package com.simpleblog.chen.controller;

import java.util.List;
import java.util.Map;

import com.simpleblog.chen.pojo.AdminPermission;
import com.simpleblog.chen.pojo.AdminRole;
import com.simpleblog.chen.service.AdminMenuService;
import com.simpleblog.chen.service.AdminPermissionService;
import com.simpleblog.chen.service.AdminRoleMenuService;
import com.simpleblog.chen.service.AdminRolePermissionService;
import com.simpleblog.chen.service.AdminRoleService;
import com.simpleblog.chen.utils.Response;
import com.simpleblog.chen.utils.ResponseFactory;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@CrossOrigin
public class RoleController {
    @Autowired
    AdminRoleService adminRoleService;

    @Autowired
    AdminPermissionService adminPermissionService;

    @Autowired
    AdminMenuService adminMenuService;

    @Autowired
    AdminRolePermissionService adminRolePermissionService;

    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    @GetMapping("/api/admin/myRole")
    @ResponseBody
    public List<AdminRole> listRoles() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        String userName = subject.getPrincipals().toString();
        return adminRoleService.listRolesByUser(userName);
    }

    @GetMapping("/api/admin/role")
    @ResponseBody
    public List<AdminRole> listAllRoles() throws Exception {
        return adminRoleService.listWithPermsAndMenus();
    }

    @PostMapping("/api/admin/role")
    @ResponseBody
    public Response createRole(@RequestBody AdminRole role) {
        adminRoleService.addRole(role);
        return ResponseFactory.buildSucceResponse("Success");
    }

    @PutMapping("/api/admin/role")
    @ResponseBody
    public Response editRole(@RequestBody AdminRole requestRole) {
        adminRoleService.addOrUpdate(requestRole);
        adminRolePermissionService.savePermChanges(requestRole.getId(), requestRole.getPermissions());
        return ResponseFactory.buildSucceResponse("Success");
    }

    @PostMapping("/api/admin/role/delete")
    @ResponseBody
    public Response deleteRole(@RequestBody AdminRole role) {
        adminRoleService.deleteByRid(role.getId());
        return ResponseFactory.buildSucceResponse("Delete Success");
    }

    @PostMapping("/api/admin/roles/delete")
    @ResponseBody
    public Response deleteRoleBatch(@RequestBody Map<String, List<AdminRole>> roles) {
        List<AdminRole> rs = roles.get("roles");
        rs.forEach(r -> {
            adminRoleService.deleteByRid(r.getId());
        });
        return ResponseFactory.buildSucceResponse("Delete Multi Success");
    }

    @GetMapping("/api/admin/role/perm")
    @ResponseBody
    public List<AdminPermission> listAllPermissions() {
        return adminPermissionService.listAllPerm();
    }

    @PutMapping(value = "/api/admin/role/status")
    @ResponseBody
    public Response updateRoleStatus(@RequestBody AdminRole requestRole) {
        AdminRole adminRole = adminRoleService.updateRoleStatus(requestRole);
        return ResponseFactory.buildSucceResponse("用户" + adminRole.getNameZH() + "状态更新成功");
    }

    @PutMapping("/api/admin/role/menu")
    @ResponseBody
    public Response updateRoleMenu(@RequestParam int rid, @RequestBody Map<String, List<Integer>> menusIds) {
        adminRoleMenuService.updateRoleMenu(rid, menusIds);
        return ResponseFactory.buildSucceResponse("Update Success");
    }
}
