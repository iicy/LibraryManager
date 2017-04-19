package com.ljy.librarymanager.mvp.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by luojiayu on 2017/4/19.
 */

public class Booking extends BmobObject{

    private String user;
    private String bookName;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
