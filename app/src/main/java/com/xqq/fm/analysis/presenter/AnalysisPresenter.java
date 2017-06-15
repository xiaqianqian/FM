package com.xqq.fm.analysis.presenter;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.xqq.fm.R;
import com.xqq.fm.analysis.model.AnalysisResponse;
import com.xqq.fm.analysis.model.ColorsData;
import com.xqq.fm.analysis.view.IAnalysisView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xqq on 2017/4/9.
 */

public class AnalysisPresenter implements IAnalysisPresenter {

    private AnalysisResponse analysisResponse;
    private IAnalysisView view;

    public AnalysisPresenter(AnalysisResponse analysisResponse, IAnalysisView view) {
        this.analysisResponse = analysisResponse;
        this.view = view;
    }

    @Override
    public List<BarEntry> getPayAndIncomeByDate(String date) {
        return analysisResponse.getPayAndIncomeByDate(date);
    }

    @Override
    public String[] getHChartLabels(HorizontalBarChart chart, String date) {
        String[] labels = null;
        switch (chart.getId()) {
            case R.id.hBarChar_all:
                labels = analysisResponse.getHChartAllLabels();
                break;
            case R.id.hBarChar_income:
                labels = analysisResponse.getHChartPayOrIncomeLabels("收入", date);
                break;
            case R.id.hBarChar_pay:
                labels = analysisResponse.getHChartPayOrIncomeLabels("支出", date);
                break;
        }
        return labels;
    }

    @Override
    public List<BarEntry> getHChartYData(HorizontalBarChart chart, String date) {
        List<BarEntry> entries = new ArrayList<>();
        switch (chart.getId()) {
            case R.id.hBarChar_all:
                entries = getPayAndIncomeByDate(date);
                break;
            case R.id.hBarChar_income:
                entries = analysisResponse.getCategoryMoney("收入", date);
                break;
            case R.id.hBarChar_pay:
                entries = analysisResponse.getCategoryMoney("支出", date);
                break;
        }
        return entries;
    }

    @Override
    public void setBarDataSet(BarDataSet set, HorizontalBarChart chart) {
        if (chart.getId() == R.id.hBarChar_all) {
            set.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry,
                                                int dataSetIndex, ViewPortHandler viewPortHandler) {
                    Double sum = (Double) entry.getData();
                    if (sum != null && sum < 0) {
                        return String.valueOf(sum);
                    }
                    return String.valueOf(value - 10);
                }
            });

            set.setColors(ColorsData.getAllHBarCharColors());
        } else {
            set.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry,
                                                int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return String.valueOf(value);
                }
            });
            set.setColors(ColorsData.getColors());
        }
    }

    @Override
    public List<PieEntry> getPieChartLabels(PieChart chart, String date) {
        List<PieEntry> entries = null;
        switch (chart.getId()) {
            case R.id.pieChart_pay:
                entries = analysisResponse.getPieEntries("支出", date);
                setPieChartCenterText(chart, "支出", date);
                break;
            case R.id.pieChart_income:
                entries = analysisResponse.getPieEntries("收入", date);
                setPieChartCenterText(chart, "收入", date);
                break;
        }
        return entries;
    }

    private void setPieChartCenterText(PieChart chart, String payOrIncome, String date) {
        double sum = analysisResponse.getSumMoneyByPayOrIncomeAndDate(payOrIncome, date);
        if (Math.abs(sum - 0) > 1) {
            chart.setCenterText(generateCenterSpannableText(payOrIncome, String.valueOf(sum)));
        }
    }

    private SpannableString generateCenterSpannableText(String payOrIncome, String content) {
        SpannableString s = new SpannableString("总" + payOrIncome + "\n¥ " + content);
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 3, 0);
        s.setSpan(new RelativeSizeSpan(1.4f), 4, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 4, s.length(), 0);
        return s;
    }

    @Override
    public void showPopWindow(View v) {
        String[] dates = null;
        switch (v.getId()) {
            case R.id.tv_analysis_date_by_year:
                dates = analysisResponse.getYearData();
                break;
            case R.id.tv_analysis_date_by_month:
                dates = analysisResponse.getMonthData();
                break;
        }
        view.showPopWindow(v, dates);
    }
}
