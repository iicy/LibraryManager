package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Comment;
import com.ljy.librarymanager.mvp.presenter.ManagerCommentsPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerCommentsModel {

    @Inject
    public ManagerCommentsModel() {
    }

    public void getComments(final ManagerCommentsPresenter managerCommentsPresenter, String bookId) {
        BmobQuery<Comment> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("bookId", bookId);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    managerCommentsPresenter.getCommentsSuccess(list);
                } else {
                    managerCommentsPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void delete(final ManagerCommentsPresenter managerCommentsPresenter, Comment comment) {
        comment.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    managerCommentsPresenter.success("");
                } else {
                    managerCommentsPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}
