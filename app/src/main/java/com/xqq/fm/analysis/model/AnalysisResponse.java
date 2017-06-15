package com.xqq.fm.analysis.model;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.xqq.fm.data.local.bill.IBillModel;
import com.xqq.fm.data.local.bill.db.BillModel;
import com.xqq.fm.data.local.bill.entity.Bill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xqq on 2017/4/9.
 */

public class AnalysisResponse {

    private IBillModel billModel;
    private String[] categoriesPay;
    private String[] categoriesIncome;
    private List<BarEntry> entriesPay;
    private List<BarEntry> entriesIncome;

    private List<Bill> bills;

    public AnalysisResponse() {
        billModel = new BillModel();
    }

    public List<BarEntry> getPayAndIncomeByDate(String date) {
        List<BarEntry> entries = new ArrayList<>();
        double pay = billModel.getPayOrInMoneyByDate("支出", date);
        double income = billModel.getPayOrInMoneyByDate("收入", date);
        double sum = income - pay;

        if (sum >= 0) {
            entries.add(new BarEntry(0f, (float) sum + 10));
        } else {
            entries.add(new BarEntry(0f, 10, sum));
        }
        entries.add(new BarEntry(1f, (float) pay + 10));
        entries.add(new BarEntry(2f, (float) income + 10));
        return entries;
    }

    public String[] getHChartAllLabels() {
        return new String[]{"结余", "支出", "收入"};
    }

    public String[] getHChartPayOrIncomeLabels(String payOrIncome, String date) {

        boolean isPay = payOrIncome.equals("支出");
        if (isPay && categoriesPay != null && categoriesPay.length > 0) {
            return categoriesPay;
        }

        if (!isPay && categoriesIncome != null && categoriesIncome.length > 0) {
            return categoriesIncome;
        }
        int i = 0;
        List<String> list = billModel.getCategoriesByDateAndPayOrIncome(payOrIncome, date);
        String[] categories = new String[list.size()];
        for (String category : list) {
            categories[i++] = category;
        }
        if (isPay) {
            categoriesPay = categories;
        } else {
            categoriesIncome = categories;
        }
        return categories;
    }

    public List<BarEntry> getCategoryMoney(String payOrIncome, String date) {

        boolean isPay = payOrIncome.equals("支出");
        if (isPay && entriesPay != null && !entriesPay.isEmpty()) {
            return entriesPay;
        }

        if (!isPay && entriesIncome != null && !entriesIncome.isEmpty()) {
            return entriesIncome;
        }
        List<BarEntry> entries = new ArrayList<>();
        float pos = 0.0f;
        String[] categories;
        if (isPay) {
            categories = categoriesPay;
        } else {
            categories = categoriesIncome;
        }
        for (String category : categories) {
            BarEntry entry = new BarEntry(pos++, (float) billModel
                    .getSumMoneyByCategoryAndDateAndPayOrIncome(category, payOrIncome, date));
            entries.add(entry);
        }

        if (isPay) {
            entriesPay = entries;
        } else {
            entriesIncome = entries;
        }
        return entries;
    }

    public List<PieEntry> getPieEntries(String payOrIncome, String date) {
        return billModel.getPieEntries(payOrIncome, date);
    }

    public double getSumMoneyByPayOrIncomeAndDate(String payOrIncome, String date) {
        return billModel.getSumMoneyByPayOrIncomeAndDate(payOrIncome, date);
    }

    public String[] getYearData() {
        return new String[]{"2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010"};
    }

    public String[] getMonthData() {
        return new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    }
}
