package com.anilicious.rigfinances.activities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.anilicious.rigfinances.beans.AddItem;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.List;

/**
 * Created by ANBARASI on 19/10/14.
 * Adapter to generate dynamic rows for multiple items
 */
public class AddItemListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<String> items;
    private String voucherType;
    private boolean notified;

    public AddItemListAdapter(Activity activity, List<String> items, String voucherType){
        this.activity = activity;
        this.items = items;
        this.voucherType = voucherType;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public String getItem(int i) {
        return items.get(i);
    }

    public void notifyDataSetChanged(boolean notified) {
        super.notifyDataSetChanged();
        this.notified = notified;
    }

    public void onCallbackNotified(){
        //return true;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(inflater == null)
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Row Layout varies for the Voucher Types
        if(convertView == null && voucherType == CommonUtils.VOUCHER_COOK){
            convertView = inflater.inflate(R.layout.fragment_cook_rows, null);
            if(notified) onCallbackNotified();
        }
        else if(convertView == null && voucherType == CommonUtils.VOUCHER_TOOL)
            convertView = inflater.inflate(R.layout.fragment_tool_rows, null);

        return convertView;
    }

}