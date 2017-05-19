package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.AnnouncementListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.presenter.HomeListPresenter;
import com.ljy.librarymanager.mvp.ui.activity.BookListActivity;
import com.ljy.librarymanager.mvp.ui.activity.MainActivity;
import com.ljy.librarymanager.mvp.ui.activity.UserInfoActivity;
import com.ljy.librarymanager.mvp.view.HomeListView;
import com.ljy.librarymanager.widget.FullyLinearLayoutManager;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class HomeListFragment extends BaseFragment implements HomeListView {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.all_book)
    CardView all_book;
    @BindView(R.id.category)
    CardView category;
    @BindView(R.id.booking_info)
    CardView booking_info;
    @BindView(R.id.borrow_info)
    CardView borrow_info;
    @BindView(R.id.collection_info)
    CardView collection_info;
    @BindView(R.id.my_info)
    CardView my_info;
    @BindView(R.id.loading)
    FrameLayout loading;
    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    @Inject
    Activity mActivity;
    @Inject
    HomeListPresenter homeListPresenter;
    @Inject
    BookingListFragment bookingListFragment;
    @Inject
    BorrowListFragment borrowListFragment;
    @Inject
    CategoryListFragment categoryListFragment;
    @Inject
    CollectionListFragment collectionListFragment;

    private List<Announcement> mData;
    private AnnouncementListAdapter mAdapter;
    private FragmentTransaction ft;
    private LoadingFragment loadingFragment;
    private static final String TAG_LOADING_FRAGMENT = "LOADING_FRAGMENT";
    private int more = 0;

    @Inject
    public HomeListFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_homelist, container, false);
        mFragmentComponent.inject(this);
        homeListPresenter.attachView(this);
        loadingFragment = new LoadingFragment();
        return view;
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingFragment.setText(getString(R.string.loading));
                homeListPresenter.getList(0);
                more = 0;
                refreshLayout.setRefreshing(false);
            }
        });
        all_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookListActivity.class);
                intent.putExtra("account", MainActivity.instance.getAccount());
                startActivity(intent);
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.instance.getMain_toolbar().setTitle("分类");
                MainActivity.instance.changeFragment(categoryListFragment);
            }
        });
        booking_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.instance.getMain_toolbar().setTitle("预订记录");
                MainActivity.instance.changeFragment(bookingListFragment);
            }
        });
        borrow_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.instance.getMain_toolbar().setTitle("借阅记录");
                MainActivity.instance.changeFragment(borrowListFragment);
            }
        });
        collection_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.instance.getMain_toolbar().setTitle("收藏");
                MainActivity.instance.changeFragment(collectionListFragment);
            }
        });
        my_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                intent.putExtra("account", MainActivity.instance.getAccount());
                startActivity(intent);
            }
        });
        list.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void loadMore() {
                more += 5;
                homeListPresenter.getList(more);
            }
        });
    }

    @Override
    protected void initData() {
        mAdapter = new AnnouncementListAdapter(getActivity(),mData);
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(getActivity());
        list.setNestedScrollingEnabled(false);
        //设置布局管理器
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(mAdapter);
    }

    @Override
    public void setList(List<Announcement> list) {
        mData = list;
        if(list.size()==0){
            loadingFragment.setText(getString(R.string.no_data));
            showProgress();
        }else{
            loadingFragment.setText(getString(R.string.loading));
        }
        mAdapter.setNewData(mData);
    }

    @Override
    public void showProgress() {
        ft = getChildFragmentManager().beginTransaction();
        if(getChildFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT)==null){
            ft.add(R.id.loading, loadingFragment, TAG_LOADING_FRAGMENT);
        }
        ft.show(loadingFragment);
        loading.setVisibility(View.VISIBLE);
        ft.commit();
    }

    @Override
    public void hideProgress() {
        ft = getChildFragmentManager().beginTransaction();
        loadingFragment =(LoadingFragment) getChildFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT);
        ft.hide(loadingFragment);
        loading.setVisibility(View.GONE);
        ft.commit();
    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void onResume() {
        super.onResume();
        homeListPresenter.getList(0);
    }

}
