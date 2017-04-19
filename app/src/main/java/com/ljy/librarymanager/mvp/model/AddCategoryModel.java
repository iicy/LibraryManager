package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.presenter.AddAnnouncementPresenter;
import com.ljy.librarymanager.mvp.presenter.AddCategoryPresenter;

import javax.inject.Inject;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class AddCategoryModel {

    @Inject
    public AddCategoryModel() {
    }

    public void add(final AddCategoryPresenter addCategoryPresenter, Category category) {
        category.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    addCategoryPresenter.success(s);
                } else {
                    addCategoryPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
