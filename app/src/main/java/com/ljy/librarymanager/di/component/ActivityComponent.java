package com.ljy.librarymanager.di.component;

import android.app.Activity;
import android.content.Context;

import com.ljy.librarymanager.di.module.ActivityModule;
import com.ljy.librarymanager.di.scope.ContextLife;
import com.ljy.librarymanager.di.scope.PerActivity;

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

//    void inject(NewsActivity newsActivity);
//
//    void inject(NewsDetailActivity newsDetailActivity);
//
//    void inject(NewsChannelActivity newsChannelActivity);
//
//    void inject(NewsPhotoDetailActivity newsPhotoDetailActivity);

    //void inject(PhotoActivity photoActivity);

    //void inject(PhotoDetailActivity photoDetailActivity);
}
