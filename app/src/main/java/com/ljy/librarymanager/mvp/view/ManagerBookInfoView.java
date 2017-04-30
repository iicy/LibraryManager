package com.ljy.librarymanager.mvp.view;

import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Category;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by luojiayu on 2017/4/6.
 */

public interface ManagerBookInfoView extends BaseView{

    void saveSuccess();

    void uploadPicSuccess(BmobFile pic);

    void reset();

    void getCategory(List<Category> data);

}
