package com.example.moneykeeper.presentation.chart;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.moneykeeper.R;
import com.example.moneykeeper.presentation.base.BaseActivity;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

/**
 * Created by Viet Hua on 3/11/2020
 */
public class ChartActivity extends BaseActivity implements ChartContract.View {
    @Inject
    ChartContract.Presenter presenter;
    @BindView(R.id.chart_pie)
    AnimatedPieView animatedPieView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        ButterKnife.bind(this);
        setupToolbar();
        setupPieChart();


    }


    @Override
    protected int getResLayoutId() {
        return R.layout.activity_chart;
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_summary, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void setupToolbar() {
        toolbar.inflateMenu(R.menu.menu_summary);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void setupPieChart() {
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.strokeWidth(200);
        config.startAngle(-90)// Starting angle offset
                .addData(new SimplePieInfo(50f, Color.parseColor("#00ff00"), "Income"))
                .addData(new SimplePieInfo(50f, Color.parseColor("#d34ede"), "CC"))
                .addData(new SimplePieInfo(50f, Color.parseColor("#ff45ed"), "SS"))
                .addData(new SimplePieInfo(50f, Color.parseColor("#ff0000"), "Expense"))
                .duration(1000);// dr// aw pie animation duration
        animatedPieView.applyConfig(config);
        animatedPieView.start();
    }
}
