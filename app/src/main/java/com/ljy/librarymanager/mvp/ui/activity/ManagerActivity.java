package com.ljy.librarymanager.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.ui.fragment.BooksListFragment;
import com.ljy.librarymanager.mvp.view.MainView;
import com.ljy.librarymanager.mvp.view.ManagerView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by jiayu on 2017/3/11.
 */

public class ManagerActivity extends BaseActivity implements ManagerView {

    @BindView(R.id.manager_drawer)
    DrawerLayout manager_drawer;
    @BindView(R.id.manager_toolbar)
    Toolbar manager_toolbar;
    @BindView(R.id.manager_navigation)
    NavigationView manager_navigation;

    private FragmentTransaction ft;
    private ActionBarDrawerToggle drawerToggle;

    @Inject
    BooksListFragment booksListFragment;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_manager);
        //注入对象
        mActivityComponent.inject(this);
    }

    @Override
    protected void init() {
        manager_toolbar.setTitle("");
        setSupportActionBar(manager_toolbar);
        changeFragment(booksListFragment);
        drawerToggle = new ActionBarDrawerToggle(this, manager_drawer, manager_toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        manager_drawer.setDrawerListener(drawerToggle);
    }

    @Override
    protected void setListener() {
        manager_toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.manager_toolbar_search: {
                        startActivity(new Intent(ManagerActivity.this,SearchActivity.class));
                        break;
                    }
                }
                return false;
            }
        });
        manager_navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void changeFragment(Fragment fragment) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.manager_fragment,fragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manager_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
