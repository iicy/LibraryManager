package com.ljy.librarymanager.mvp.view;


import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Category;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by jiayu on 2017/3/11.
 */

public interface AddBookView extends BaseView {

    void add(Books book);

    void addSuccess();

    void uploadPicSuccess(BmobFile pic);

    void getCategory(List<Category> data);
}
