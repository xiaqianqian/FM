package com.xqq.fm.transfer.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xqq.fm.R;
import com.xqq.fm.account.view.AccountActivity;
import com.xqq.fm.data.local.card.entity.CardTransferDetail;
import com.xqq.fm.transfer.model.TransferResponse;
import com.xqq.fm.transfer.presenter.ITransferPresenter;
import com.xqq.fm.transfer.presenter.TransferPresenter;

import java.util.List;

/**
 * 转账模块的实现
 */
public class TransferFragment extends Fragment implements ITransferView, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private ITransferPresenter presenter;
    private MyAdapter adapter;
    private FloatingActionButton mFabAddCard;

    public TransferFragment() {
    }

    public static TransferFragment newInstance() {
        return new TransferFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_transfer, container, false);
        initView(layout);
        return layout;
    }

    private void initView(View layout) {
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_transfer);
        presenter = new TransferPresenter(new TransferResponse(), (ITransferView) this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        adapter = new MyAdapter(presenter.getCardTransferDetail());
        mRecyclerView.setAdapter(adapter);

        mFabAddCard = (FloatingActionButton) layout.findViewById(R.id.fab_add_card);
        mFabAddCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add_card:
                startActivityForResult(new Intent(getActivity(), AddCardActivity.class),
                        AccountActivity.CARD_RESULT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AccountActivity.CARD_RESULT) {
            adapter.refreshData(presenter.getCardTransferDetail());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class MyAdapter extends RecyclerView.Adapter {

        private List<CardTransferDetail> cards;

        public MyAdapter(List<CardTransferDetail> cards) {
            this.cards = cards;
        }

        public void refreshData(List<CardTransferDetail> cards) {
            this.cards = cards;
            notifyItemInserted(cards.size());
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 给ViewHolder设置布局文件
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_transfer_cardview_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            CardTransferDetail detail = cards.get(position);
            viewHolder.tvType.setText(detail.getCardType());
            viewHolder.tvNumber.setText(detail.getCardNumber());
            viewHolder.tvIncome.setText(String.valueOf(detail.getIncome()));
            viewHolder.tvPay.setText(String.valueOf(detail.getPay()));
        }

        @Override
        public int getItemCount() {
            return cards.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvType;
            public TextView tvNumber;
            public TextView tvIncome;
            public TextView tvPay;

            public ViewHolder(View view) {
                super(view);
                tvType = (TextView) view.findViewById(R.id.tv_card_type);
                tvNumber = (TextView) view.findViewById(R.id.tv_card_number);
                tvIncome = (TextView) view.findViewById(R.id.tv_card_income);
                tvPay = (TextView) view.findViewById(R.id.tv_card_pay);
            }
        }
    }
}
