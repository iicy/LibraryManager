package com.ljy.librarymanager.di.component;

import android.app.Activity;
import android.content.Context;

import com.ljy.librarymanager.di.module.ActivityModule;
import com.ljy.librarymanager.di.scope.ContextLife;
import com.ljy.librarymanager.di.scope.PerActivity;
import com.ljy.librarymanager.mvp.ui.activity.AddAnnouncementActivity;
import com.ljy.librarymanager.mvp.ui.activity.LoginActivity;
import com.ljy.librarymanager.mvp.ui.activity.MainActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerActivity;
import com.ljy.librarymanager.mvp.ui.activity.SearchBarActivity;
import com.ljy.librarymanager.mvp.view.SearchBarView;

import dagger.Component;

/**
 * Created by jiayu on 2017/3/11.
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(LoginActivity loginActivity);

    void inject(SearchBarActivity searchBarActivity);

    void inject(MainActivity mainActivity);

    void inject(ManagerActivity managerActivity);

    void inject(AddAnnouncementActivity addAnnouncementActivity);
}
