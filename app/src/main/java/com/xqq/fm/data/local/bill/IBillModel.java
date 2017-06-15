package com.xqq.fm.data.local.bill;

import com.github.mikephil.charting.data.PieEntry;
import com.xqq.fm.data.local.bill.entity.Bill;
import com.xqq.fm.data.local.bill.entity.BillBody;
import com.xqq.fm.data.local.bill.entity.BillHeader;

import java.util.List;

/**
 * Created by xqq on 2017/3/28.
 */

public interface IBillModel {
    void save(Bill bill);

    List<BillHeader> getBillHeader();

    List<Bill> getBills();

    List<BillHeader> getBillHeaderByCategory(String category);

    List<List<BillBody>> getBillBody(List<BillHeader> headers);

    List<List<BillBody>> getBillBodyByCategory(List<BillHeader> headers, String category);

    void delete(int id);

    double getCurrentMonthPayOrInMoney(String payOrIncome);

    Bill getBillById(int id);

    double getPayOrInMoneyByDate(String payOrIncome, String date);

    List<String> getCategoriesByDateAndPayOrIncome(String payOrIncome, String date);

    double getSumMoneyByCategoryAndDateAndPayOrIncome(String category, String payOrIncome, String date);

    List<PieEntry> getPieEntries(String payOrIncome, String date);

    double getSumMoneyByPayOrIncomeAndDate(String payOrIncome, String date);

    double getPayOrInMoneyByCardNumber(String payOrIncome, String cardNumber);

    void updateBill(Bill bill);

    int count();
}