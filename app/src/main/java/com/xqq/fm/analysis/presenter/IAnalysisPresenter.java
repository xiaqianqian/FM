package com.xqq.fm.analysis.presenter;

import android.view.View;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

/**
 * Created by xqq on 2017/4/9.
 */

public interface IAnalysisPresenter {

    List<BarEntry> getPayAndIncomeByDate(String date);

    String[] getHChartLabels(HorizontalBarChart chart, String date);

    List<BarEntry> getHChartYData(HorizontalBarChart chart, String date);

    void setBarDataSet(BarDataSet set, HorizontalBarChart chart);

    List<PieEntry> getPieChartLabels(PieChart chart, String date);

    void showPopWindow(View v);
}
