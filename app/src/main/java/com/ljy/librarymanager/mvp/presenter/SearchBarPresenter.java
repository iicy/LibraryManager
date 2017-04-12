package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.model.ManagerAnnouncementModel;
import com.ljy.librarymanager.mvp.model.SearchBarModel;
import com.ljy.librarymanager.mvp.view.ManagerAnnouncementView;
import com.ljy.librarymanager.mvp.view.SearchBarView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class SearchBarPresenter extends BasePresenter<SearchBarView, String> {

    private SearchBarModel searchBarModel;

    @Inject
    public SearchBarPresenter(SearchBarModel searchBarModel) {
        this.searchBarModel = searchBarModel;
    }

    public void searchAnnouncement(String key) {
        searchBarModel.searchAnnouncement(this,key);
    }

    @Override
    public void success(String data) {
        super.success(data);
    }

    public void searchAnnouncementSuccess(List<Announcement> data){
        mView.hideProgress();
        mView.searchAnnouncement(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
