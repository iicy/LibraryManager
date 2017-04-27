package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Comment;
import com.ljy.librarymanager.mvp.model.AddAnnouncementModel;
import com.ljy.librarymanager.mvp.model.SendCommentModel;
import com.ljy.librarymanager.mvp.view.AddAnnouncementView;
import com.ljy.librarymanager.mvp.view.SendCommentView;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class SendCommentPresenter extends BasePresenter<SendCommentView, String> {

    private SendCommentModel sendCommentModel;

    @Inject
    public SendCommentPresenter(SendCommentModel sendCommentModel) {
        this.sendCommentModel = sendCommentModel;
    }

    public void send(Comment comment) {
        sendCommentModel.send(this,comment);
    }

    @Override
    public void success(String s) {
        super.success(s);
        mView.send();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
