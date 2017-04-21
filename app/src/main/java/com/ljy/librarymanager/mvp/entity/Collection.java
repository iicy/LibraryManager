package com.ljy.librarymanager.mvp.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by luojiayu on 2017/4/19.
 */

public class Collection extends BmobObject{

    private String user;
    private String bookName;
    private String bookId;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

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
