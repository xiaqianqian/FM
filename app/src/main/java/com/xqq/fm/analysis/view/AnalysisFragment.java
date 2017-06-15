package com.xqq.fm.analysis.view;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.xqq.fm.R;
import com.xqq.fm.analysis.model.AnalysisResponse;
import com.xqq.fm.analysis.model.ColorsData;
import com.xqq.fm.analysis.presenter.AnalysisPresenter;
import com.xqq.fm.analysis.presenter.IAnalysisPresenter;
import com.xqq.fm.widget.DatePopupWindow;
import com.xqq.fm.search.view.SearchActivity;
import com.xqq.fm.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 统计分析
 */
public class AnalysisFragment extends Fragment implements IAnalysisView, View.OnClickListener {

    private PieChart mPieChart;
    private PieChart mPieChartIncome;
    private Typeface mTfLight;
    private HorizontalBarChart mHBarChartAll;
    private HorizontalBarChart mHBarChartPay;
    private HorizontalBarChart mHBarChartIncome;

    private TextView tvDate;
    private TextView tvSettingYear;
    private TextView tvSettingMonth;
    private String currDate;

    private IAnalysisPresenter presenter;

    public AnalysisFragment() {
    }

    public static AnalysisFragment newInstance() {
        return new AnalysisFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);

        presenter = new AnalysisPresenter(new AnalysisResponse(), (IAnalysisView) this);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mHBarChartAll = (HorizontalBarChart) view.findViewById(R.id.hBarChar_all);
        mHBarChartIncome = (HorizontalBarChart) view.findViewById(R.id.hBarChar_income);
        mHBarChartPay = (HorizontalBarChart) view.findViewById(R.id.hBarChar_pay);
        tvDate = (TextView) view.findViewById(R.id.tv_analysis_date);
        tvSettingYear = (TextView) view.findViewById(R.id.tv_analysis_date_by_year);
        tvSettingMonth = (TextView) view.findViewById(R.id.tv_analysis_date_by_month);

        currDate = DateUtil.getCurrentMonth();
        tvDate.setText(currDate);
        tvSettingYear.setOnClickListener(this);
        tvSettingMonth.setOnClickListener(this);

        mPieChart = (PieChart) view.findViewById(R.id.pieChart_pay);
        mPieChartIncome = (PieChart) view.findViewById(R.id.pieChart_income);
        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        mHBarChartAll.setTouchEnabled(false);

        initData();
    }

    private void initData() {
        initPieChar(mPieChart);
        initPieChar(mPieChartIncome);

        initHorBarChar(mHBarChartAll);
        initHorBarChar(mHBarChartPay);
        initHorBarChar(mHBarChartIncome);
    }

    private void initHorBarChar(final HorizontalBarChart hBarChart) {

        hBarChart.setDrawBarShadow(false);
        hBarChart.setDrawValueAboveBar(true);

        hBarChart.getDescription().setEnabled(false);

        hBarChart.setPinchZoom(false);

        hBarChart.setDrawGridBackground(false);

        hBarChart.setScaleEnabled(false);// 关闭图表的缩放
        hBarChart.setDoubleTapToZoomEnabled(false);//双击缩放

        // 设置标签数据
        final String[] xVals = presenter.getHChartLabels(hBarChart, currDate);

        hBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("category", xVals[(int) e.getX()]);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {}
        });

        XAxis xl = hBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setCenterAxisLabels(false);
        xl.setDrawLabels(true);
        xl.setLabelCount(xVals.length);  // 设置显示的标签数与标签数组个数一致
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int pos = (int) value >= xVals.length || (int) value < 0 ? 0 : (int) value;
                return xVals[pos];
            }
        });

        YAxis yl = hBarChart.getAxisLeft();
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setDrawLabels(false);
        yl.setAxisMinimum(0f);   // 没有此行无法显示Y行数据

        YAxis yr = hBarChart.getAxisRight();
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        yr.setDrawLabels(false);
        yr.setAxisMinimum(0f);

        setHorBarChartData(hBarChart);

        hBarChart.setFitBars(true);
        hBarChart.animateY(1400);

        Legend l = hBarChart.getLegend();
        l.setEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    private void setHorBarChartData(HorizontalBarChart hBarChart) {

        float barWidth = 0.65f;

        // 获取条目数据
        List<BarEntry> entries = presenter.getHChartYData(hBarChart, currDate);

        BarDataSet set1;

        if (hBarChart.getData() != null &&
                hBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) hBarChart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            hBarChart.getData().notifyDataChanged();
            hBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(entries, " ");
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            presenter.setBarDataSet(set1, hBarChart);
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
            hBarChart.setData(data);
            hBarChart.invalidate();
        }
    }

    private void initPieChar(PieChart pieChart) {

        pieChart.setUsePercentValues(true);

        pieChart.getDescription().setEnabled(false);

        pieChart.setExtraOffsets(5, 10, 5, 5);// 设置间距

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setCenterTextTypeface(mTfLight);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);

        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleRadius(60f);

        pieChart.setDrawCenterText(true); // 是否绘制中心区域文字

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        setPieChartData(pieChart);

        // 设置标签
        Legend l = pieChart.getLegend();
        l.setEnabled(false);

        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf"));
        pieChart.setEntryLabelTextSize(12f);
    }

    private void setPieChartData(PieChart pieChart) {


        List<PieEntry> entries = presenter.getPieChartLabels(pieChart, currDate);

        if (entries == null) {
            return;
        }
        PieDataSet dataSet = new PieDataSet(entries, " ");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        dataSet.setColors(ColorsData.getColors());

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    @Override
    public void onClick(View view) {
        presenter.showPopWindow(view);
    }

    @Override
    public void showPopWindow(View view, String[] dates) {
        DatePopupWindow popup = new DatePopupWindow(getActivity(), dates, new DatePopupWindow.DatePopupWindowCallback() {
            @Override
            public void setDate(String date) {
                tvDate.setText(date);
                currDate = date;
                initData();
            }
        });
        popup.showPopupWindow(view);
    }
}
