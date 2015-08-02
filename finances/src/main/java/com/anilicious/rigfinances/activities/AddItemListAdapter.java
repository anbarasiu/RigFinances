package com.anilicious.rigfinances.activities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

    private List<String> items_dummy;

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

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void onCallbackNotified(){
        //return true;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        if(inflater == null)
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Row Layout varies for the Voucher Types
        if(convertView == null && voucherType == CommonUtils.VOUCHER_COOK){
            convertView = inflater.inflate(R.layout.fragment_cook_rows, null);
            Button btn_delete = (Button)convertView.findViewById(R.id.cook_delete);
        }
        else if(convertView == null && voucherType == CommonUtils.VOUCHER_TOOL){
            convertView = inflater.inflate(R.layout.fragment_tool_rows, null);
            Button btn_delete = (Button)convertView.findViewById(R.id.tool_delete);
        }

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}