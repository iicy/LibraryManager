package com.ljy.librarymanager.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ljy.librarymanager.greendao.DaoMaster;
import com.ljy.librarymanager.greendao.DaoSession;
import com.ljy.librarymanager.greendao.SearchHistoryDao;
import com.ljy.librarymanager.mvp.entity.SearchHistory;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by jiayu on 2017/5/7.
 */

public class DBManager {

    private final static String dbName = "library_manager";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    private DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    public Observable<List<SearchHistory>> getSearchHistory(final String account) {
        return Observable.create(new Observable.OnSubscribe<List<SearchHistory>>() {
            @Override
            public void call(Subscriber<? super List<SearchHistory>> subscriber) {
                DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
                DaoSession daoSession = daoMaster.newSession();
                SearchHistoryDao searchHistoryDao = daoSession.getSearchHistoryDao();
                QueryBuilder<SearchHistory> qb = searchHistoryDao.queryBuilder();
                qb.where(SearchHistoryDao.Properties.Account.eq(account))
                        .orderDesc(SearchHistoryDao.Properties.Id);
                List<SearchHistory> list = qb.list();
                subscriber.onNext(list);
            }
        });
    }

    public Observable saveSearchHistory(final SearchHistory searchHistory) {
        return Observable.create(new Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
                DaoSession daoSession = daoMaster.newSession();
                SearchHistoryDao searchHistoryDao = daoSession.getSearchHistoryDao();
                searchHistoryDao.insert(searchHistory);
            }
        });
    }

    public Observable clearSearchHistory(final String account) {
        return Observable.create(new Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
                DaoSession daoSession = daoMaster.newSession();
                SearchHistoryDao searchHistoryDao = daoSession.getSearchHistoryDao();
                QueryBuilder<SearchHistory> qb = searchHistoryDao.queryBuilder();
                DeleteQuery<SearchHistory> dq = qb.where(SearchHistoryDao.Properties.Account.eq(account)).buildDelete();
                dq.executeDeleteWithoutDetachingEntities();
            }
        });
    }
}
