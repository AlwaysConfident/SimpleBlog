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
@Table(name = "admin_role")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class AdminRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_zh")
    private String nameZH;

    @Column(name = "enabled")
    private boolean enabled;

    @Transient
    private List<AdminPermission> permissions;

    @Transient
    private List<AdminMenu> menus;
}
