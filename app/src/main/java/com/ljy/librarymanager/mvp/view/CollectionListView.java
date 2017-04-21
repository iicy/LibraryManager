package com.ljy.librarymanager.mvp.view;


import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Collection;

import java.util.List;

/**
 * Created by jiayu on 2017/3/11.
 */

public interface CollectionListView extends BaseView {

    void setList(List<Collection> data);
}
