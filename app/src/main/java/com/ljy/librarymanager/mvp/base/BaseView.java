package com.ljy.librarymanager.mvp.base;

/**
 * Created by jiayu on 2017/3/11.
 */

public interface BaseView {
    void showProgress();

    void hideProgress();

    void showMsg(String message);
}