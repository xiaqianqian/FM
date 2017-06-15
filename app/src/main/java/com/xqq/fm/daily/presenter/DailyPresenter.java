package com.xqq.fm.daily.presenter;

import com.xqq.fm.daily.view.IDailyView;
import com.xqq.fm.data.local.bill.BillsResponse;
import com.xqq.fm.data.local.bill.entity.Bill;
import com.xqq.fm.data.local.bill.entity.BillBody;
import com.xqq.fm.data.local.bill.entity.BillHeader;

import java.util.List;

/**
 * Created by xqq on 2017/4/5.
 */

public class DailyPresenter implements IDailyPresenter {
    private BillsResponse billsResponse;
    private IDailyView view;

    public DailyPresenter(BillsResponse billsResponse, IDailyView view) {
        this.billsResponse = billsResponse;
        this.view = view;
    }

    @Override
    public List<BillHeader> getBillHeader() {
        return billsResponse.getBillHeader();
    }

    @Override
    public List<List<BillBody>> getBillBody() {
        return billsResponse.getBillBody();
    }

    @Override
    public void refreshData(int groupPosition) {
        billsResponse.refreshData();
        view.refreshBills(groupPosition);
    }

    @Override
    public void deleteBill(int groupPos, int childPos) {
        int id = billsResponse.getBillIdByGroupAndChildPosition(groupPos, childPos);
        billsResponse.delete(id);

        refreshData(groupPos);
    }

    @Override
    public String getCurrentMonthPay() {
        return String.valueOf(billsResponse.getCurrentMonthPayOrInMoney("支出"));
    }

    @Override
    public String getCurrentMonthIncome() {
        return String.valueOf(billsResponse.getCurrentMonthPayOrInMoney("收入"));
    }

    @Override
    public Bill getBill(int groupPos, int childPos) {
        int id = billsResponse.getBillIdByGroupAndChildPosition(groupPos, childPos);

        return billsResponse.getBillById(id);
    }
}
