package com.ljy.librarymanager.mvp.view;


import android.support.v4.app.Fragment;

import com.ljy.librarymanager.mvp.base.BaseView;

/**
 * Created by jiayu on 2017/3/11.
 */

public interface MainView extends BaseView {

    void changeFragment(Fragment fragment);

}
