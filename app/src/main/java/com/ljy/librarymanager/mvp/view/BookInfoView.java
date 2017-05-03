package com.ljy.librarymanager.mvp.view;

import com.ljy.librarymanager.mvp.base.BaseView;

/**
 * Created by luojiayu on 2017/4/6.
 */

public interface BookInfoView extends BaseView{

    void success();

    void hasBooking(boolean result,String id);

    void hasCollect(boolean result,String id);
}
