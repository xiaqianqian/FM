package com.xqq.fm.search.presenter;

import com.xqq.fm.data.local.bill.entity.BillBody;
import com.xqq.fm.data.local.bill.entity.BillHeader;
import com.xqq.fm.search.model.SearchResponse;
import com.xqq.fm.search.view.ISearchView;

import java.util.List;

/**
 * Created by xqq on 2017/4/15.
 */

public class SearchPresenter implements ISearchPresenter {

    private SearchResponse response;
    private ISearchView view;

    public SearchPresenter(SearchResponse response, ISearchView view) {
        this.response = response;
        this.view = view;
    }

    public List<BillHeader> getBillHeaderByCategory(String category) {
        return response.getBillHeaderByCategory(category);
    }

    public List<List<BillBody>> getBillBody(String category) {
        return response.getBillBody(category);
    }

}
