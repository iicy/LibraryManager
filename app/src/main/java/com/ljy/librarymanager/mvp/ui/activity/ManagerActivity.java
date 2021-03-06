package com.ljy.librarymanager.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.ui.fragment.ManagerAnnouncementFragment;
import com.ljy.librarymanager.mvp.ui.fragment.ManagerBookingFragment;
import com.ljy.librarymanager.mvp.ui.fragment.ManagerBorrowFragment;
import com.ljy.librarymanager.mvp.ui.fragment.ManagerCategoryFragment;
import com.ljy.librarymanager.mvp.ui.fragment.ManagerUserFragment;
import com.ljy.librarymanager.mvp.view.ManagerView;
import com.ljy.librarymanager.utils.RxBus;

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

    private TextView tv_username;
    private TextView tv_account;

    private FragmentTransaction ft;
    private ActionBarDrawerToggle drawerToggle;

    private String account;
    private String password;
    private String permission;
    private String username;
    public static ManagerActivity instance;

    @Inject
    ManagerAnnouncementFragment managerAnnouncementFragment;
    @Inject
    ManagerBookingFragment managerBookingFragment;
    @Inject
    ManagerBorrowFragment managerBorrowFragment;
    @Inject
    ManagerCategoryFragment managerCategoryFragment;
    @Inject
    ManagerUserFragment managerUserFragment;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_manager);
        //注入对象
        mActivityComponent.inject(this);
    }

    @Override
    protected void init() {
        instance = this;
        manager_toolbar.setTitle("公告管理");
        manager_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(manager_toolbar);
        changeFragment(managerAnnouncementFragment);
        drawerToggle = new ActionBarDrawerToggle(this, manager_drawer, manager_toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        manager_drawer.setDrawerListener(drawerToggle);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        password = intent.getStringExtra("password");
        permission = intent.getStringExtra("permission");
        username = intent.getStringExtra("username");

        View view = manager_navigation.getHeaderView(0);
        tv_username = (TextView) view.findViewById(R.id.username);
        tv_account = (TextView) view.findViewById(R.id.account);
        tv_username.setText(username);
        tv_account.setText(account);
    }

    @Override
    protected void setListener() {
        manager_toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.manager_toolbar_search: {
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.manager_fragment);
                        if (fragment instanceof ManagerAnnouncementFragment) {
                            RxBus.getInstance().post("searchAnnounce", fragment);
                        } else if (fragment instanceof ManagerBookingFragment) {
                            RxBus.getInstance().post("searchBooking", fragment);
                        } else if (fragment instanceof ManagerUserFragment) {
                            RxBus.getInstance().post("searchUser", fragment);
                        } else if (fragment instanceof ManagerCategoryFragment) {
                            RxBus.getInstance().post("searchCategory", fragment);
                        } else if (fragment instanceof ManagerBorrowFragment) {
                            RxBus.getInstance().post("searchBorrow", fragment);
                        }
                        break;
                    }
                    case R.id.manager_toolbar_add: {
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.manager_fragment);
                        if (fragment instanceof ManagerAnnouncementFragment) {
                            Intent intent = new Intent(ManagerActivity.this, AddAnnouncementActivity.class);
                            intent.putExtra("account", account);
                            startActivity(intent);
                        }
                        if (fragment instanceof ManagerUserFragment) {
                            Intent intent = new Intent(ManagerActivity.this, AddUserActivity.class);
                            startActivity(intent);
                        }
                        if (fragment instanceof ManagerCategoryFragment) {
                            Intent intent = new Intent(ManagerActivity.this, AddCategoryActivity.class);
                            startActivity(intent);
                        }
                        if (fragment instanceof ManagerBorrowFragment) {
                            Intent intent = new Intent(ManagerActivity.this, AddBorrowActivity.class);
                            intent.putExtra("manager", account);
                            intent.putExtra("passowrd", password);
                            startActivity(intent);
                        }
                        break;
                    }
                }
                return false;
            }
        });
        manager_navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_manager_announcement: {
                        manager_toolbar.setTitle("公告管理");
                        changeFragment(managerAnnouncementFragment);
                        break;
                    }
                    case R.id.nav_manager_booking: {
                        manager_toolbar.setTitle("预订记录");
                        changeFragment(managerBookingFragment);
                        break;
                    }
                    case R.id.nav_manager_borrow: {
                        manager_toolbar.setTitle("借阅记录");
                        changeFragment(managerBorrowFragment);
                        break;
                    }
                    case R.id.nav_manager_category: {
                        manager_toolbar.setTitle("分类管理");
                        changeFragment(managerCategoryFragment);
                        break;
                    }
                    case R.id.nav_manager_user: {
                        manager_toolbar.setTitle("用户管理");
                        changeFragment(managerUserFragment);
                        break;
                    }
                    case R.id.nav_manager_settings: {
                        Intent i = new Intent(ManagerActivity.this, PreferenceActivity.class);
                        startActivity(i);
                        break;
                    }
                }
                manager_drawer.closeDrawer(Gravity.LEFT);
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
        ft.replace(R.id.manager_fragment, fragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manager_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public String getAccount() {
        return account;
    }

    public void setUsername(String username) {
        this.username = username;
        SharedPreferences sp = this.getSharedPreferences("loginFlag", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_username.setText(username);
    }
}
