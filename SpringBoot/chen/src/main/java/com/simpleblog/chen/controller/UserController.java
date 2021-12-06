package com.simpleblog.chen.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.simpleblog.chen.pojo.User;
import com.simpleblog.chen.service.AdminUserRoleService;
import com.simpleblog.chen.service.UserService;
import com.simpleblog.chen.utils.Response;
import com.simpleblog.chen.utils.ResponseFactory;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AdminUserRoleService adminUserRoleService;

    @RequiresPermissions("/api/admin/user")
    @GetMapping("/api/admin/user")
    @ResponseBody
    public List<User> listUsers() throws Exception {
        return userService.listAlUsers();
    }

    @PutMapping("/api/admin/user")
    @ResponseBody
    public Response editUser(@RequestBody @Valid User requestUser) {
        userService.editUser(requestUser);
        return ResponseFactory.buildSucceResponse("Update Success");
    }

    @PostMapping("/api/admin/user/add")
    @ResponseBody
    public Response addUser(@RequestBody @Valid User requestUser) {
        int state = userService.addUser(requestUser);
        switch (state) {
            case 0:
                return ResponseFactory.buildFailResponse("Null UserName Or Password");
            case 1:
                return ResponseFactory.buildSucceResponse("Add Success");
            case 2:
                return ResponseFactory.buildFailResponse("User Already Exist");
            default:
                break;
        }
        return ResponseFactory.buildFailResponse("Unknown Exception");
    }

    @PostMapping("/api/admin/user/delete")
    @ResponseBody
    public Response deleteUser(@RequestBody Map<String, Integer> map) {
        userService.deleteUserById(map.get("id"));
        return ResponseFactory.buildSucceResponse("Delete Success");
    }

    @PostMapping("/api/admin/users/delete")
    @ResponseBody
    public Response deleteUserBatch(@RequestBody Map<String, List<User>> map) {
        List<User> users = map.get("users");
        users.forEach(user -> {
            userService.deleteUserById(user.getId());
        });
        return ResponseFactory.buildSucceResponse("Delete Batch Success");
    }

    @PutMapping("/api/admin/user/password")
    @ResponseBody
    public Response resetPassword(@RequestBody @Valid User requestUser) {
        userService.resetPassword(requestUser);
        return ResponseFactory.buildSucceResponse("Password Reseted!");
    }
}
