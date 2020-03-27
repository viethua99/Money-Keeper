package com.example.local.database;

import android.content.Context;
import android.util.Log;

import com.example.domain.executor.ExecutionThread;
import com.example.domain.model.Category;
import com.example.local.database.dao.CategoryDao;
import com.example.local.database.dao.PercentDao;
import com.example.local.database.dao.TransactionDao;
import com.example.local.model.CategoryModel;
import com.example.local.model.TransactionModel;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Database(entities = {TransactionModel.class, CategoryModel.class}, version = 13)
public abstract class MoneyKeeperDatabase extends RoomDatabase {
    public static MoneyKeeperDatabase instance;

    public abstract TransactionDao transactionDao();

    public abstract CategoryDao categoryDao();

    public abstract PercentDao percentDao();

    public static MoneyKeeperDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MoneyKeeperDatabase.class, "mkp_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            final CategoryDao categoryDao = instance.categoryDao();
            Completable completable = Completable.create(new CompletableOnSubscribe() {
                @Override
                public void subscribe(@io.reactivex.rxjava3.annotations.NonNull CompletableEmitter emitter) throws Throwable {
                    //Add Default Category Data
                    categoryDao.insert(new CategoryModel("Food", "nfood", "cfood", "Expense"));
                    categoryDao.insert(new CategoryModel("Transport", "ntransport", "ctransport", "Expense"));
                    categoryDao.insert(new CategoryModel("Shopping", "nshopping", "cshopping", "Expense"));
                    categoryDao.insert(new CategoryModel("Bills", "nbill", "cbill", "Expense"));
                    categoryDao.insert(new CategoryModel("Health", "nhealth", "chealth", "Expense"));
                    categoryDao.insert(new CategoryModel("Telephone", "nphone", "cphone", "Expense"));
                    categoryDao.insert(new CategoryModel("Home", "nhome", "chome", "Expense"));
                    categoryDao.insert(new CategoryModel("Education", "neducation", "ceducation", "Expense"));
                    categoryDao.insert(new CategoryModel("Travel", "ntravel", "ctravel", "Expense"));
                    categoryDao.insert(new CategoryModel("Insurance", "ninsurance", "cinsurance", "Expense"));
                    categoryDao.insert(new CategoryModel("Social", "nsocial", "csocial", "Expense"));
                    categoryDao.insert(new CategoryModel("Sport", "nsport", "csport", "Expense"));
                    categoryDao.insert(new CategoryModel("Gift", "ngift", "cgift", "Expense"));
                    categoryDao.insert(new CategoryModel("Others", "nother", "cother", "Expense"));

                    categoryDao.insert(new CategoryModel("Salary", "nsalary", "csalary", "Income"));
                    categoryDao.insert(new CategoryModel("Awards", "naward", "caward", "Income"));
                    categoryDao.insert(new CategoryModel("Grants", "ngrant", "cgrant", "Income"));
                    categoryDao.insert(new CategoryModel("Sale", "nsale", "csale", "Income"));
                    categoryDao.insert(new CategoryModel("Rental", "nrental", "crental", "Income"));
                    categoryDao.insert(new CategoryModel("Coupons", "ncoupon", "ccoupon", "Income"));
                    categoryDao.insert(new CategoryModel("Lottery", "nlottery", "clottery", "Income"));
                    categoryDao.insert(new CategoryModel("Dividend", "ndividend", "cdividend", "Income"));
                    categoryDao.insert(new CategoryModel("Invest", "ninvestment", "cinvestment", "Income"));
                    categoryDao.insert(new CategoryModel("Others", "nother", "cother", "Income"));

                    emitter.onComplete();
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            compositeDisposable.add(completable.subscribeWith(new InsertCategoryObserver()));
        }
    };

    private static class InsertCategoryObserver extends DisposableCompletableObserver {
        @Override
        public void onComplete() {
            Log.d("CATEGORY", "DEFAULT DATA SUCCEED");
        }

        @Override
        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

        }
    }


}
