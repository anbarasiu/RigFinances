package com.anilicious.rigfinances.activities;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.anilicious.rigfinances.beans.AddItem;
import com.anilicious.rigfinances.beans.CookItem;
import com.anilicious.rigfinances.beans.ToolItem;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANBARASI on 19/10/14.
 * Adapter to generate dynamic rows for multiple items
 */
public class AddItemListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List items;
    //private List<ToolItem> items;
    private String voucherType;
    private boolean notified;

    private List<String> items_dummy;

    private List<String> items_item;
    private List<String> items_quantity;
    private List<String> items_price;

    public AddItemListAdapter(Activity activity, List items, String voucherType){
        this.activity = activity;
        this.items = items;
        this.voucherType = voucherType;
    }

    /*public AddItemListAdapter(Activity activity, List<ToolItem> items, String voucherType){
        this.activity = activity;
        this.items = items;
        this.voucherType = voucherType;
    }*/

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
        return null;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void onCallbackNotified(){
        //return true;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder = new ViewHolder();

        if(inflater == null)
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Button btn_delete = null;
        // Row Layout varies for the Voucher Types
        switch(voucherType){
            case CommonUtils.VOUCHER_COOK:
                if(convertView == null){
                    convertView = inflater.inflate(R.layout.item_cook, null);
                    convertView.setTag(holder);
                } else{
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.item = (TextView)convertView.findViewById(R.id.cook_details_item);
                holder.quantity = (TextView)convertView.findViewById(R.id.cook_details_quantity);
                holder.price = (TextView)convertView.findViewById(R.id.cook_details_price);
                holder.delete = (Button)convertView.findViewById(R.id.cook_details_delete);

                holder.item.setText(((CookItem)items.get(position)).getItem());
                holder.quantity.setText((Integer.toString(((CookItem)items.get(position)).getQuantity())));
                holder.price.setText((Float.toString(((CookItem)items.get(position)).getAmount())));
                break;
            case CommonUtils.VOUCHER_TOOL:
                if(convertView == null){
                    convertView = inflater.inflate(R.layout.item_tool, null);
                    convertView.setTag(holder);
                } else{
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.item = (TextView)convertView.findViewById(R.id.tool_details_item);
                holder.quantity = (TextView)convertView.findViewById(R.id.tool_details_quantity);
                holder.price = (TextView)convertView.findViewById(R.id.tool_details_price);
                holder.details = (TextView)convertView.findViewById(R.id.tool_details_price);
                holder.delete = (Button)convertView.findViewById(R.id.tool_details_delete);

                holder.item.setText(((ToolItem)items.get(position)).getItem());
                holder.quantity.setText((Integer.toString(((ToolItem)items.get(position)).getQuantity())));
                holder.price.setText((Float.toString(((ToolItem)items.get(position)).getAmount())));
                holder.details.setText(((ToolItem)items.get(position)).getDetails());
                break;
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    static class ViewHolder{
        TextView item;
        TextView details;
        TextView quantity;
        TextView price;
        Button delete;
    }
}