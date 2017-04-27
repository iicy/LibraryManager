package com.ljy.librarymanager.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.ui.fragment.BookingListFragment;
import com.ljy.librarymanager.mvp.ui.fragment.BorrowListFragment;
import com.ljy.librarymanager.mvp.ui.fragment.CategoryListFragment;
import com.ljy.librarymanager.mvp.ui.fragment.CollectionListFragment;
import com.ljy.librarymanager.mvp.ui.fragment.HomeListFragment;
import com.ljy.librarymanager.mvp.ui.fragment.LoadingFragment;
import com.ljy.librarymanager.mvp.view.MainView;

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
    @BindView(R.id.loading)
    FrameLayout loading;
    @BindView(R.id.main_navigation)
    NavigationView main_navigation;

    private TextView tv_username;
    private TextView tv_account;

    private FragmentTransaction ft;
    private ActionBarDrawerToggle drawerToggle;

    private String account;
    private String password;
    private String permission;
    private String username;
    public static MainActivity instance;
    private LoadingFragment loadingFragment;
    private static final String TAG_LOADING_FRAGMENT = "LOADING_FRAGMENT";

    @Inject
    HomeListFragment homeListFragment;
    @Inject
    BookingListFragment bookingListFragment;
    @Inject
    BorrowListFragment borrowListFragment;
    @Inject
    CategoryListFragment categoryListFragment;
    @Inject
    CollectionListFragment collectionListFragment;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
        //注入对象
        mActivityComponent.inject(this);
        loadingFragment = new LoadingFragment();
    }

    @Override
    protected void init() {
        instance = this;
        main_toolbar.setTitle("主页");
        main_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(main_toolbar);
        changeFragment(homeListFragment);
        drawerToggle = new ActionBarDrawerToggle(this, main_drawer, main_toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        main_drawer.setDrawerListener(drawerToggle);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        password = intent.getStringExtra("password");
        permission = intent.getStringExtra("permission");
        username = intent.getStringExtra("username");

        View view = main_navigation.getHeaderView(0);
        tv_username= (TextView) view.findViewById(R.id.username);
        tv_account= (TextView) view.findViewById(R.id.account);
        tv_username.setText(username);
        tv_account.setText(account);
    }

    @Override
    protected void setListener() {
        main_toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_toolbar_search: {
                        startActivity(new Intent(MainActivity.this,SearchBarActivity.class));
                        break;
                    }
                }
                return false;
            }
        });
        main_navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:{
                        main_toolbar.setTitle("主页");
                        changeFragment(homeListFragment);
                        break;
                    }
                    case R.id.nav_category:{
                        main_toolbar.setTitle("分类");
                        changeFragment(categoryListFragment);
                        break;
                    }
                    case R.id.nav_booking:{
                        main_toolbar.setTitle("预订记录");
                        changeFragment(bookingListFragment);
                        break;
                    }
                    case R.id.nav_borrow:{
                        main_toolbar.setTitle("借阅记录");
                        changeFragment(borrowListFragment);
                        break;
                    }
                    case R.id.nav_collection:{
                        main_toolbar.setTitle("收藏");
                        changeFragment(collectionListFragment);
                        break;
                    }
                    case R.id.nav_settings:{
                        Intent i = new Intent(MainActivity.this,PreferenceActivity.class);
                        i.putExtra("account",account);
                        i.putExtra("password",password);
                        startActivity(i);
                        break;
                    }
                }
                main_drawer.closeDrawer(Gravity.LEFT);
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
        ft = getSupportFragmentManager().beginTransaction();
        if(getSupportFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT)==null){
            ft.add(R.id.loading, loadingFragment, TAG_LOADING_FRAGMENT);
        }
        ft.show(loadingFragment);
        loading.setVisibility(View.VISIBLE);
        ft.commit();
    }

    @Override
    public void hideProgress() {
        ft = getSupportFragmentManager().beginTransaction();
        loadingFragment =(LoadingFragment) getSupportFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT);
        ft.hide(loadingFragment);
        loading.setVisibility(View.GONE);
        ft.commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public String getAccount(){
        return account;
    }

    public void hasData(boolean b){
        if(b){
            loadingFragment.setText("正在加载...");
        }else{
            loadingFragment.setText("暂无数据");
        }
    }
}
