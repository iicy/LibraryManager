package com.ljy.librarymanager.mvp.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by luojiayu on 2017/4/19.
 */

public class Borrow extends BmobObject {

    private String user;
    private String status;
    private BmobDate returnDate;
    private String manager;
    private String bookName;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BmobDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(BmobDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
