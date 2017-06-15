package com.xqq.fm.daily.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.xqq.fm.R;
import com.xqq.fm.data.local.bill.entity.BillBody;
import com.xqq.fm.data.local.bill.entity.BillHeader;

import java.util.List;

/**
 * Created by xqq on 2017/3/28.
 */

public class ExpandListAdapter extends BaseExpandableListAdapter {
    private List<BillHeader> headers;
    private List<List<BillBody>> bodies;
    private LayoutInflater inflater;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            notifyDataSetChanged();
            super.handleMessage(msg);
        }
    };

    public ExpandListAdapter(Context context, List<BillHeader> headers, List<List<BillBody>> bodies) {
        inflater = LayoutInflater.from(context);
        this.headers = headers;
        this.bodies = bodies;
    }

    public void refresh(List<BillHeader> headers, List<List<BillBody>> bodies) {
        this.headers = headers;
        this.bodies = bodies;
        handler.sendEmptyMessage(0);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return bodies.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headers.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return bodies.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.elv_header, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_bill_date);
            groupViewHolder.tvInMoney = (TextView) convertView.findViewById(R.id.tv_bill_sum_in_money);
            groupViewHolder.tvPayMoney = (TextView) convertView.findViewById(R.id.tv_bill_sum_pay_money);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        if (groupPosition < headers.size()) {
            BillHeader header = headers.get(groupPosition);
            groupViewHolder.tvDate.setText(header.getDate());

            double income = header.getSumInMoney();
            groupViewHolder.tvInMoney.setText((income > 0 ? "￥" + income : ""));
            double pay = header.getSumPayMoney();
            groupViewHolder.tvPayMoney.setText((pay > 0 ? "￥" + pay : ""));
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.elv_body, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvCategory = (TextView) convertView.findViewById(R.id.tv_bill_category);
            childViewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tv_bill_money);
            childViewHolder.tvRemark = (TextView) convertView.findViewById(R.id.tv_bill_remark);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        if (groupPosition < bodies.size() && childPosition < bodies.get(groupPosition).size()) {
            childViewHolder.tvCategory.setText(bodies.get(groupPosition).get(childPosition).getCategory());
            childViewHolder.tvMoney.setText("￥" + bodies.get(groupPosition).get(childPosition).getMoney() + "");
            childViewHolder.tvRemark.setText(bodies.get(groupPosition).get(childPosition).getRemark());
        }
        return convertView;
    }

    static class GroupViewHolder {
        TextView tvDate;
        TextView tvPayMoney;
        TextView tvInMoney;
    }

    static class ChildViewHolder {
        TextView tvCategory;
        TextView tvRemark;
        TextView tvMoney;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}