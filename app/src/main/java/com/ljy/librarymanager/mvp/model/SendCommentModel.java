package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Comment;
import com.ljy.librarymanager.mvp.presenter.SendCommentPresenter;

import javax.inject.Inject;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class SendCommentModel {

    @Inject
    public SendCommentModel() {
    }

    public void send(final SendCommentPresenter sendCommentPresenter, Comment comment) {
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    sendCommentPresenter.success(s);
                } else {
                    sendCommentPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
