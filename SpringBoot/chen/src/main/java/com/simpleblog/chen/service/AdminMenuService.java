package com.simpleblog.chen.service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.simpleblog.chen.dao.AdminMenuDAO;
import com.simpleblog.chen.pojo.AdminMenu;
import com.simpleblog.chen.pojo.AdminRoleMenu;
import com.simpleblog.chen.pojo.AdminUserRole;
import com.simpleblog.chen.pojo.User;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMenuService {
    @Autowired
    AdminMenuDAO adminMenuDAO;

    @Autowired
    UserService userService;

    @Autowired
    AdminUserRoleService adminUserRoleService;

    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public List<AdminMenu> getMenuByCurrentuser() {
        // 通过shiro获取用户名
        String userName = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.getUserByName(userName);

        // 通过用户名获取用户的所有角色id
        List<Integer> rids = adminUserRoleService.listAllByUid(user.getId()).stream().map(AdminUserRole::getRid)
                .collect(Collectors.toList());

        // 通过角色id获取用户能够访问的所有菜单id
        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rids).stream().map(AdminRoleMenu::getMid)
                .collect(Collectors.toList());

        // 通过菜单id获取所有要渲染的菜单
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds).stream().distinct().collect(Collectors.toList());

        // 处理菜单的树层次结构
        handleMenus(menus);
        return menus;
    }

    public void handleMenus(List<AdminMenu> menus) {
        // 遍历菜单，由id查询子项并加入children属性
        // 由JPA提供的持久化上下文(实体的集合)，用于确保相同的持久化对象只有一个实例，且在存在相应实例时不会再次访问数据库
        // 此时遍历的AdminMenu对象实例是复用了Menus列表中的AdminMenu对象
        for (AdminMenu menu : menus) {
            List<AdminMenu> children = getAllByParentId(menu.getId());
            menu.setChildren(children);
        }

        // 剔除所有子项，只保留第一层的父项
        // 在Java中的对象都是引用类型，如将b-->a，c-->b，得到的结果是c-->b-->a，所以一次遍历就可以得到正确的层级关系
        Iterator<AdminMenu> iterator = menus.iterator();
        while (iterator.hasNext()) {
            AdminMenu menu = iterator.next();
            if (menu.getParentId() != 0) {
                // remove方法将对象名指向null，对象本身仍然存在，所以虽然无法通过b,c获取对象，但a中的信息不变
                // 使用iterator而不用List的remove是因为使用List遍历时，删除元素会令后面的元素补上，即之后的索引和列表长度都会改变
                // 使用iterator会自动把当前的索引和循环次数减一，避免了遗漏元素和下标溢出
                iterator.remove();
            }
        }
    }

    public List<AdminMenu> getAllByParentId(int id) {
        return adminMenuDAO.findAllByParentId(id);
    }

    public List<AdminMenu> getMenusByRid(int rid) {
        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rid).stream().map(AdminRoleMenu::getMid)
                .collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds);

        handleMenus(menus);

        return menus;
    }
}
