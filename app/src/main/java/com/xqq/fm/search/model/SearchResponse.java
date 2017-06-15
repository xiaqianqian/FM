package com.xqq.fm.search.model;

import com.xqq.fm.data.local.bill.IBillModel;
import com.xqq.fm.data.local.bill.db.BillModel;
import com.xqq.fm.data.local.bill.entity.BillBody;
import com.xqq.fm.data.local.bill.entity.BillHeader;

import java.util.List;

/**
 * Created by xqq on 2017/4/15.
 */

public class SearchResponse {

    private IBillModel billModel;
    private List<BillHeader> headers;

    public SearchResponse() {
        billModel = new BillModel();
    }

    public List<BillHeader> getBillHeaderByCategory(String category) {
        headers = billModel.getBillHeaderByCategory(category);
        return headers;
    }

    public List<List<BillBody>> getBillBody(String category) {
        return billModel.getBillBodyByCategory(headers, category);
    }
}
