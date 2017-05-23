package com.lies.malped.module.main.ui.adapter;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.ui.BaseActivity;
import com.lies.malped.R;
import com.lies.malped.utils.DialogHelper;
import com.lies.malped.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiesLee on 16/10/20.
 */
public class TestSimpleAdapter extends RecyclerView.Adapter<TestSimpleAdapter.Holder> {

    List<String> lists = new ArrayList<>();
    BaseActivity baseActivity;

    public TestSimpleAdapter(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(baseActivity).inflate(R.layout.item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.tv_str.setText(position+"");
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public List<String> getLists() {
        return lists;
    }

    public void setLists(List<String> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }
    public void setData(List<String> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public void addData(List<String> lists){
        if (this.lists!=null){
            this.lists.addAll(lists);
        }else{
            this.lists = new ArrayList<>(lists);
        }
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView tv_str;

        public Holder(View itemView) {
            super(itemView);
            tv_str = (TextView) itemView.findViewById(R.id.tv_str);
        }

    }

}
