package com.xqq.fm.transfer.presenter;

import com.xqq.fm.data.local.card.entity.CardTransferDetail;

import java.util.List;

/**
 * Created by xqq on 2017/4/16.
 * <p>
 * 转账模块逻辑处理接口
 */

public interface ITransferPresenter {

    List<CardTransferDetail> getCardTransferDetail();
}
