package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.ManagerCategoryPresenter;
import com.ljy.librarymanager.mvp.presenter.ManagerUserPresenter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerCategoryModel {

    @Inject
    public ManagerCategoryModel() {
    }

    public void getList(final ManagerCategoryPresenter managerCategoryPresenter) {
        BmobQuery<Category> bmobQuery = new BmobQuery<Category>();
        bmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> list, BmobException e) {
                if (e == null) {
                    Collections.sort(list);
                    managerCategoryPresenter.success(list);
                } else {
                    managerCategoryPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void delete(final ManagerCategoryPresenter managerCategoryPresenter, final Category category){
        BmobQuery<Books> bmobQuery = new BmobQuery<Books>();
        bmobQuery.addWhereEqualTo("category",category.getCategory_name());
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null) {
                    if((list.size()==0)||(list==null)){
                        category.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    managerCategoryPresenter.deleteSuccess();
                                } else {
                                    managerCategoryPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });
                    }else{
                        managerCategoryPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                    }
                } else {
                    managerCategoryPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
