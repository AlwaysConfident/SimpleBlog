package com.simpleblog.chen.controller;

import java.util.List;

import com.simpleblog.chen.pojo.AdminMenu;
import com.simpleblog.chen.service.AdminMenuService;
import com.simpleblog.chen.service.AdminRoleService;
import com.simpleblog.chen.service.UserService;
import com.simpleblog.chen.utils.Response;
import com.simpleblog.chen.utils.ResponseFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class MenuController {
    @Autowired
    AdminMenuService adminMenuService;

    @Autowired
    UserService userService;

    @Autowired
    AdminRoleService adminRoleService;

    @GetMapping("/api/menu")
    @ResponseBody
    public List<AdminMenu> getMenu() throws Exception {
        return adminMenuService.getMenuByCurrentuser();
    }

    @GetMapping("/api/admin/role/menu")
    @ResponseBody
    public List<AdminMenu> listAllMenus() {
        return adminMenuService.getMenusByRid(1);
    }

    @PutMapping("/api/admin/user/status")
    @ResponseBody
    public Response changeStatus() {
        return ResponseFactory.buildSucceResponse("success");
    }
}
