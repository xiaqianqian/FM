package com.xqq.fm.search.presenter;

import com.xqq.fm.data.local.bill.entity.BillBody;
import com.xqq.fm.data.local.bill.entity.BillHeader;

import java.util.List;

/**
 * Created by xqq on 2017/4/15.
 */

public interface ISearchPresenter {
    List<BillHeader> getBillHeaderByCategory(String category);

    List<List<BillBody>> getBillBody(String category);
}
