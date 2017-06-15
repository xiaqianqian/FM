package com.xqq.fm.data.local.bill;

import com.xqq.fm.data.local.bill.db.BillModel;
import com.xqq.fm.data.local.bill.entity.Bill;
import com.xqq.fm.data.local.bill.entity.BillBody;
import com.xqq.fm.data.local.bill.entity.BillHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xqq on 2017/4/7.
 */

public class BillsResponse {

    private List<Bill> bills;

    private List<BillHeader> headers;

    private List<List<BillBody>> bodies;

    private IBillModel billModel;

    public BillsResponse() {
        bills = new ArrayList<>();
        headers = new ArrayList<>();
        bodies = new ArrayList<>();
        billModel = new BillModel();
    }

    public void saveBill(Bill bill) {
        billModel.save(bill);
    }

    public List<BillHeader> getBillHeader() {
        if (bodies == null || bodies.isEmpty()) {
            headers = billModel.getBillHeader();
        }
        return headers;
    }

    public List<List<BillBody>> getBillBody() {
        if (bodies == null || bodies.isEmpty()) {
            bodies = billModel.getBillBody(headers);
        }
        return bodies;
    }

    public void refreshData() {
        headers = billModel.getBillHeader();
        bodies = billModel.getBillBody(headers);
    }

    public int getBillIdByGroupAndChildPosition(int groupPos, int childPos) {
        return bodies.get(groupPos).get(childPos).getId();
    }

    public void delete(int id) {
        billModel.delete(id);
    }

    public double getCurrentMonthPayOrInMoney(String payOrIncome) {
        return billModel.getCurrentMonthPayOrInMoney(payOrIncome);
    }

    public Bill getBillById(int id) {
        return billModel.getBillById(id);
    }

    public void updateBill(Bill bill) {
        billModel.updateBill(bill);
    }
}
