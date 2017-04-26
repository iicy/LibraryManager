package com.ljy.librarymanager.mvp.view;

import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Category;

import java.util.List;

/**
 * Created by luojiayu on 2017/4/6.
 */

public interface ManagerBookInfoView extends BaseView{

    void saveSuccess();

    void reset();

    void getCategory(List<Category> data);

}
