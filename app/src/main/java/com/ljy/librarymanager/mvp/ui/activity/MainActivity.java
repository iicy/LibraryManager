package com.ljy.librarymanager.mvp.ui.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.presenter.LoginPresenter;
import com.ljy.librarymanager.mvp.ui.fragment.BooksListFragment;
import com.ljy.librarymanager.mvp.view.LoginView;
import com.ljy.librarymanager.mvp.view.MainView;
import com.ljy.librarymanager.utils.CheckCodeUtil;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by jiayu on 2017/3/11.
 */

public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.main_drawer)
    DrawerLayout main_drawer;
    @BindView(R.id.main_toolbar)
    Toolbar main_toolbar;
    @BindView(R.id.main_navigation)
    NavigationView main_navigation;

    private FragmentTransaction ft;
    private ActionBarDrawerToggle drawerToggle;

    @Inject
    BooksListFragment booksListFragment;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
        //注入对象
        mActivityComponent.inject(this);
    }

    @Override
    protected void init() {
        main_toolbar.setTitle("");
        setSupportActionBar(main_toolbar);
        changeFragment(booksListFragment);
        drawerToggle = new ActionBarDrawerToggle(this, main_drawer, main_toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        main_drawer.setDrawerListener(drawerToggle);
    }

    @Override
    protected void setListener() {
        main_navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
        ft.replace(R.id.main_fragment,fragment);
        ft.commit();
    }
}
