package com.ljy.librarymanager.mvp.view;

import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Comment;

import java.util.List;

/**
 * Created by luojiayu on 2017/4/6.
 */

public interface ManagerCommentsView extends BaseView {

    void deleteSuccess();

    void getComments(List<Comment> data);
}
