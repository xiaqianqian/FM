package com.xqq.fm.transfer.presenter;

import com.xqq.fm.data.local.card.entity.CardTransferDetail;
import com.xqq.fm.transfer.model.TransferResponse;
import com.xqq.fm.transfer.view.ITransferView;

import java.util.List;

/**
 * Created by xqq on 2017/4/16.
 * <p>
 * 转账模块的控制器，逻辑处理
 */

public class TransferPresenter implements ITransferPresenter {

    private TransferResponse response;
    private ITransferView view;

    public TransferPresenter(TransferResponse response, ITransferView view) {
        this.response = response;
        this.view = view;
    }

    @Override
    public List<CardTransferDetail> getCardTransferDetail() {
        return response.getCardTransferDetail();
    }
}
