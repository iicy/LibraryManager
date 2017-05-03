package com.ljy.librarymanager.mvp.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import cn.bmob.v3.BmobObject;

/**
 * Created by jiayu on 2017/4/3.
 */

@Entity
public class User extends BmobObject{

    @Id(autoincrement = true)
    private Long id;
    private String account;
    private String password;
    private String username;
    private String permission;
    @Generated(hash = 1757012403)
    public User(Long id, String account, String password, String username,
            String permission) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.username = username;
        this.permission = permission;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPermission() {
        return this.permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
}
