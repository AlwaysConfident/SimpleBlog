package com.simpleblog.chen.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "admin_menu")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class AdminMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "path")
    private String path;

    @Column(name = "name")
    private String name;

    @Column(name = "name_zh")
    private String nameZH;

    @Column(name = "icon_cls")
    private String iconCls;

    @Column(name = "component")
    private String component;

    @Column(name = "parent_id")
    private int parentId;

    @Transient // 标注数据库中不存在的字段
    List<AdminMenu> children; // 存储字节点
}
