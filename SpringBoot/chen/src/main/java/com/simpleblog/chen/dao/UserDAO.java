package com.simpleblog.chen.dao;

import java.util.List;

import com.simpleblog.chen.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<User, Integer> { // 用来操作数据库的对象，通过继承JPA来构造，可以按照规范提供方法名即可实现CRUD
  User findByUserName(String userName);

  User getByUserNameAndPassword(String userName, String password);

  @Query(value = "select new User(u.id,u.userName,u.name,u.phone,u.email,u.enabled) from User u")
  List<User> list();
}
