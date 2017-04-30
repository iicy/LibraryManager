package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.AddBookPresenter;
import com.ljy.librarymanager.mvp.presenter.AddUserPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class AddBookModel {

    @Inject
    public AddBookModel() {
    }

    public void add(final AddBookPresenter addBookPresenter, Books book) {
        book.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    addBookPresenter.success(s);
                } else {
                    addBookPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void getCategoryList(final AddBookPresenter addBookPresenter) {
        BmobQuery<Category> bmobQuery = new BmobQuery<Category>();
        bmobQuery.order("-category_name");
        bmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> list, BmobException e) {
                if (e == null) {
                    addBookPresenter.getCategorySuccess(list);
                } else {
                    addBookPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void uploadPic(final AddBookPresenter addBookPresenter, final BmobFile pic){
        pic.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    addBookPresenter.uploadPicSuccess(pic);
                } else {
                    addBookPresenter.onError("上传图片失败!");
                }
            }
        });
    }

}
