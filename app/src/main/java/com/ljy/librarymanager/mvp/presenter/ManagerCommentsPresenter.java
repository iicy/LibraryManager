package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Comment;
import com.ljy.librarymanager.mvp.model.ManagerCommentsModel;
import com.ljy.librarymanager.mvp.view.ManagerCommentsView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerCommentsPresenter extends BasePresenter<ManagerCommentsView, String> {

    private ManagerCommentsModel managerCommentsModel;

    @Inject
    public ManagerCommentsPresenter(ManagerCommentsModel managerCommentsModel) {
        this.managerCommentsModel = managerCommentsModel;
    }

    public void delete(Comment comment) {
        managerCommentsModel.delete(this, comment);
    }

    public void getComments(String bookId) {
        mView.showProgress();
        managerCommentsModel.getComments(this, bookId);
    }

    @Override
    public void success(String s) {
        super.success(s);
        mView.deleteSuccess();
    }

    public void getCommentsSuccess(List<Comment> data) {
        mView.hideProgress();
        mView.getComments(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
