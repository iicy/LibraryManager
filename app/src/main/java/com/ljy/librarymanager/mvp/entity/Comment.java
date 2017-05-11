package com.ljy.librarymanager.mvp.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by luojiayu on 2017/4/27.
 */

public class Comment extends BmobObject {
    private String user;
    private String username;
    private String content;
    private String bookId;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
