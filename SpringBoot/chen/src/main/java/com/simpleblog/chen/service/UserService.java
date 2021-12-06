package com.simpleblog.chen.service;

import java.util.List;

import javax.validation.Valid;

import com.simpleblog.chen.dao.AdminUserRoleDAO;
import com.simpleblog.chen.dao.UserDAO;
import com.simpleblog.chen.pojo.AdminRole;
import com.simpleblog.chen.pojo.User;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

// Caused by:org.hibernate.exception.SQLGrammarException:could not extract ResultSet\r\n\t 
// ","message":"could not extract ResultSet; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not extract ResultSet","path":"/api/login"}
@Service
public class UserService { // 封装DAO层方法的业务层

  @Autowired
  UserDAO userDAO;

  @Autowired
  AdminUserRoleDAO adminUserRoleDAO;

  @Autowired
  AdminRoleService adminRoleService;

  @Autowired
  AdminUserRoleService adminUserRoleService;

  public List<User> listAlUsers() {
    List<User> users = userDAO.list();
    List<AdminRole> roles;
    for (User user : users) {
      roles = adminRoleService.listRolesByUser(user.getUserName());
      user.setRoles(roles);
    }
    return users;
  }

  public boolean isUserExist(String userName) {
    User user = getUserByName(userName);
    return null != user;
  }

  public User getUserByName(String userName) {
    return userDAO.findByUserName(userName);
  }

  public User getUser(String userName, String password) {
    return userDAO.getByUserNameAndPassword(userName, password);
  }

  public int addUser(User user) {
    String userName = user.getUserName();
    String name = user.getName();
    String phone = user.getPhone();
    String email = user.getEmail();
    String password = user.getPassword();

    userName = HtmlUtils.htmlEscape(userName);
    user.setUserName(userName);
    name = HtmlUtils.htmlEscape(name);
    user.setName(name);
    phone = HtmlUtils.htmlEscape(phone);
    user.setPhone(phone);
    email = HtmlUtils.htmlEscape(email);
    user.setEmail(email);
    user.setEnabled(true);

    if (userName.equals("") || password.equals("")) {
      return 0;
    }

    boolean exist = isUserExist(userName);

    if (exist) {
      return 2;
    }

    String salt = new SecureRandomNumberGenerator().nextBytes().toString();
    int times = 2;
    String encodePassword = new SimpleHash("md5", password, salt, times).toString();

    user.setSalt(salt);
    user.setPassword(encodePassword);

    userDAO.save(user);

    return 1;
  }

  public void editUser(User user) {
    User userInDB = userDAO.findByUserName(user.getUserName());
    userInDB.setName(user.getName());
    userInDB.setPhone(user.getPhone());
    userInDB.setEmail(user.getEmail());
    userDAO.save(userInDB);
    adminUserRoleService.saveRoleChanges(userInDB.getId(), user.getRoles());
  }

  public void deleteUserById(int id) {
    userDAO.deleteById(id);
    adminUserRoleService.deleteByUid(id);
  }

  public User resetPassword(@Valid User user) {
    User userInDB = userDAO.findByUserName(user.getUserName());
    String salt = new SecureRandomNumberGenerator().nextBytes().toString();
    int times = 2;
    userInDB.setSalt(salt);
    String encodePassword = new SimpleHash("md5", "123", salt, times).toString();
    userInDB.setPassword(encodePassword);
    return userDAO.save(userInDB);
  }
}
