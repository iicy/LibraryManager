package com.ljy.librarymanager.mvp.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by luojiayu on 2017/4/7.
 */

public class Category extends BmobObject {

    private String category_name;

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
