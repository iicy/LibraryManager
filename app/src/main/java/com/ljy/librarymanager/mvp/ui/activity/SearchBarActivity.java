package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.AnnouncementListAdapter;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.SearchBarPresenter;
import com.ljy.librarymanager.mvp.view.SearchBarView;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;
import com.ljy.librarymanager.widget.SearchView;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/17.
 */

public class SearchBarActivity extends BaseActivity implements SearchBarView {

    @BindView(R.id.search_search_view)
    SearchView search_search_view;
    @BindView(R.id.history_text)
    TextView history_text;
    @BindView(R.id.list)
    LoadMoreRecyclerView list;
    @BindView(R.id.clear_cache)
    Button clear_cache;

    private ProgressDialog pg;
    private String searchType;
    private String account;
    private String identity;

    @Inject
    SearchBarPresenter searchBarPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_search);
        //注入对象
        mActivityComponent.inject(this);
        searchBarPresenter.attachView(this);
        pg = new ProgressDialog(SearchBarActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("正在搜素！");
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        searchType = getIntent().getStringExtra("searchType");
        account = getIntent().getStringExtra("account");
        identity = getIntent().getStringExtra("identity");
    }

    @Override
    protected void setListener() {
        search_search_view.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onCheckBoxClick(EditText v) {
                v.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                v.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                                || actionId == EditorInfo.IME_ACTION_DONE
                                || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                            showProgress();
                            if(searchType.equals("announcement")){
                                List<Announcement> mData = (List<Announcement>) getIntent().getSerializableExtra("list");
                                searchBarPresenter.searchAnnouncement(mData,v.getText().toString());
                            }else if(searchType.equals("booking")){
                                List<Booking> mData = (List<Booking>) getIntent().getSerializableExtra("list");
                                searchBarPresenter.searchBooking(mData,v.getText().toString());
                            }else if(searchType.equals("book")){
                                List<Books> mData = (List<Books>) getIntent().getSerializableExtra("list");
                                searchBarPresenter.searchBooks(mData,v.getText().toString());
                            }else if(searchType.equals("borrow")){
                                List<Borrow> mData = (List<Borrow>) getIntent().getSerializableExtra("list");
                                searchBarPresenter.searchBorrow(mData,v.getText().toString());
                            }else if(searchType.equals("user")){
                                List<User> mData = (List<User>) getIntent().getSerializableExtra("list");
                                searchBarPresenter.searchUser(mData,v.getText().toString());
                            }else if(searchType.equals("category")){
                                List<Category> mData = (List<Category>) getIntent().getSerializableExtra("list");
                                searchBarPresenter.searchCategory(mData,v.getText().toString());
                            }
                        }
                        return true;
                    }
                });
            }
        });
        search_search_view.setOnBackListener(new SearchView.OnBackListener() {
            @Override
            public void onBackClick(ImageButton v) {
                finish();
            }
        });
        clear_cache.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.clear_cache: {
                break;
            }
        }
    }

    @Override
    public void searchAnnouncement(List<Announcement> mData) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("list", (Serializable) mData);
        intent.putExtra("searchType","announcement");
        intent.putExtra("account",account);
        startActivity(intent);
    }

    @Override
    public void searchBooking(List<Booking> mData) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("list", (Serializable) mData);
        intent.putExtra("searchType","booking");
        startActivity(intent);
    }

    @Override
    public void searchBooks(List<Books> mData) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("list", (Serializable) mData);
        intent.putExtra("searchType","book");
        intent.putExtra("identity",identity);
        intent.putExtra("account",account);
        startActivity(intent);
    }

    @Override
    public void searchBorrow(List<Borrow> mData) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("list", (Serializable) mData);
        intent.putExtra("searchType","borrow");
        startActivity(intent);
    }

    @Override
    public void searchUser(List<User> mData) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("list", (Serializable) mData);
        intent.putExtra("searchType","user");
        startActivity(intent);
    }

    @Override
    public void searchCategory(List<Category> mData) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("list", (Serializable) mData);
        intent.putExtra("searchType","category");
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        pg.show();
    }

    @Override
    public void hideProgress() {
        pg.dismiss();
    }

    @Override
    public void showMsg(String message) {

    }
}
