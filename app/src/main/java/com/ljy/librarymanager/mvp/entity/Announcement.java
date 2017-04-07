package com.ljy.librarymanager.mvp.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by luojiayu on 2017/4/7.
 */

public class Announcement extends BmobObject {

    private String account;
    private String content;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
