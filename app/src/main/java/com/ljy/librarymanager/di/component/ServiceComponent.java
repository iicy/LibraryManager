package com.ljy.librarymanager.di.component;

import android.content.Context;

import com.ljy.librarymanager.di.module.ServiceModule;
import com.ljy.librarymanager.di.scope.ContextLife;
import com.ljy.librarymanager.di.scope.PerService;

import dagger.Component;

/**
 * Created by jiayu on 2017/3/11.
 */

@PerService
@Component(dependencies = AppComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}