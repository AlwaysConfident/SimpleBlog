package com.simpleblog.chen.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity // 标注Hibernate映射的一个实体类
@Table(name = "user") // 声明此对象映射到数据库的表
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" }) // 前后端交互使用JSON，JPA在将实体类持久化时会使用代理类来继承User类，代理类会加入无需转换成JSON的字段
public class User { // 用户实体类

  @Id // 声明为主键
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略 IDENTITY 主键由数据库生成, 采用数据库自增长
  @Column(name = "id") // 声明属性与字段的联系
  private int id;

  @Column(name = "username")
  private String userName;

  @Column(name = "password")
  private String password;

  @Column(name = "salt")
  private String salt;

  @Column(name = "name")
  private String name;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String email;

  @Column(name = "enabled")
  private boolean enabled;

  @Transient
  List<AdminRole> roles;

  public User(int id, String userName, String name, String phone, String email, boolean enabled) {
    this.id = id;
    this.userName = userName;
    this.password = " ";
    this.salt = " ";
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.enabled = enabled;
  }

  public User() {
    this.id = 0;
    this.userName = " ";
    this.password = " ";
    this.salt = " ";
    this.phone = " ";
    this.email = " ";
    this.enabled = false;
  }
}
