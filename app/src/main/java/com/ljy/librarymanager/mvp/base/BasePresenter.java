package com.ljy.librarymanager.mvp.base;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class BasePresenter<T extends BaseView, E> {

    protected T mView;

    public void attachView(BaseView view) {
        mView = (T) view;
    }

    public void success(E data) {
        mView.hideProgress();
    }

    public void onError(String errorMsg) {
        mView.hideProgress();
        mView.showMsg(errorMsg);
    }

}
