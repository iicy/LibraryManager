package com.ljy.librarymanager.di.component;

import android.content.Context;

import com.ljy.librarymanager.di.module.AppModule;
import com.ljy.librarymanager.di.scope.ContextLife;
import com.ljy.librarymanager.di.scope.PerApp;

import dagger.Component;

/**
 * Created by jiayu on 2017/3/11.
 */

@PerApp
@Component(modules = AppModule.class)
public interface AppComponent {
    @ContextLife("Application")
    Context getApplication();
}