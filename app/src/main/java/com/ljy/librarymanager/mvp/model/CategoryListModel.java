package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.presenter.CategoryListPresenter;
import com.ljy.librarymanager.mvp.presenter.ManagerCategoryPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class CategoryListModel {

    @Inject
    public CategoryListModel() {
    }

    public void getList(final CategoryListPresenter categoryListPresenter) {
        BmobQuery<Category> bmobQuery = new BmobQuery<Category>();
        bmobQuery.order("-category_name");
//        bmobQuery.setLimit(10);
        bmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> list, BmobException e) {
                if (e == null) {
                    categoryListPresenter.success(list);
                } else {
                    categoryListPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


}
