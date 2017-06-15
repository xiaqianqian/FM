package com.xqq.fm.daily.presenter;

import com.xqq.fm.data.local.bill.entity.Bill;
import com.xqq.fm.data.local.bill.entity.BillBody;
import com.xqq.fm.data.local.bill.entity.BillHeader;

import java.util.List;

/**
 * Created by xqq on 2017/4/5.
 */

public interface IDailyPresenter {

    List<BillHeader> getBillHeader();

    List<List<BillBody>> getBillBody();

    void refreshData(int groupPosition);

    void deleteBill(int groupPos, int childPos);

    Bill getBill(int groupPos, int childPos);

    String getCurrentMonthPay();

    String getCurrentMonthIncome();
}
