package com.lies.malped.module.my.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.lies.malped.bean.DataInfo;
import com.lies.malped.widgets.AMzItemLayout;
import com.lies.malped.widgets.DataImportUtils;
import com.lies.malped.widgets.RetailMeNotLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiesLee on 17/4/23.
 */

public class RetailMeNotAdapter extends RetailMeNotLayout.Adapter  {
    List<DataInfo> list = new ArrayList<>();

    public void set() {
        List<DataInfo> l1 = DataImportUtils.init();
        List<DataInfo> l2 = DataImportUtils.init();
        List<DataInfo> l3 = DataImportUtils.init();

        list.addAll(l1);
        list.addAll(l2);
        list.addAll(l3);

        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, ViewGroup parent, int expandedHeight, int normalHeight) {
        AMzItemLayout item = new AMzItemLayout(parent.getContext());
        item.setData(position, list.get(position), expandedHeight, normalHeight);
        return item;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
