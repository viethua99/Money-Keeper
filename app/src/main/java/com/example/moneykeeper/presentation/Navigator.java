package com.example.moneykeeper.presentation;

import android.content.Intent;

import com.example.moneykeeper.presentation.chart.ChartActivity;
import com.example.moneykeeper.presentation.home.HomeActivity;
import com.example.moneykeeper.presentation.summary.SummaryActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator {
    @Inject
    public Navigator() {

    }

    public void openChartActivity(HomeActivity homeActivity) {
        Intent intent = new Intent(homeActivity, ChartActivity.class);
        homeActivity.startActivity(intent);
    }

    public void openSummaryActivity(HomeActivity homeActivity) {
        Intent intent = new Intent(homeActivity, SummaryActivity.class);
        homeActivity.startActivity(intent);
    }
}
