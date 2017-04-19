package com.ljy.librarymanager.mvp.view;

import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Category;

import java.util.List;

/**
 * Created by luojiayu on 2017/4/6.
 */

public interface ManagerCategoryView extends BaseView{

    void setList(List<Category> data);

    void deleteSuccess();
}
