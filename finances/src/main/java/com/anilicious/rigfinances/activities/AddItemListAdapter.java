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
import android.widget.EditText;

import com.anilicious.rigfinances.beans.AddItem;
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
    private List<String> items;
    private String voucherType;
    private boolean notified;

    private List<String> items_dummy;

    private List<String> items_item;
    private List<String> items_quantity;
    private List<String> items_price;

    public AddItemListAdapter(Activity activity, List<String> items, String voucherType){
        this.activity = activity;
        this.items = items;
        this.voucherType = voucherType;

        items_item = new ArrayList<String>();
        items_quantity = new ArrayList<String>();
        items_price = new ArrayList<String>();
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
        return null;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void onCallbackNotified(){
        //return true;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        final ViewHolder holder;

        if(inflater == null)
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Button btn_delete = null;

        // Row Layout varies for the Voucher Types
        switch(voucherType){
            case CommonUtils.VOUCHER_COOK:
                if(convertView == null){
                    convertView = inflater.inflate(R.layout.fragment_cook_rows, null);

                    holder = new ViewHolder();

                    holder.item = (EditText)convertView.findViewById(R.id.addItem_item);
                    holder.quantity = (EditText)convertView.findViewById(R.id.addItem_quantity);
                    holder.price = (EditText)convertView.findViewById(R.id.addItem_price);
                    //holder.delete = (EditText)convertView.findViewById(R.id.cook_delete);

                    convertView.setTag(holder);

                    btn_delete = (Button)convertView.findViewById(R.id.cook_delete); // TODO : Add to viewholder
                } else{
                    holder = (ViewHolder) convertView.getTag();
                }

                // TODO: Validation
                if(items_item.size() > position)
                    holder.item.setText(items_item.get(position));
                if(items_quantity.size() > position)
                    holder.quantity.setText(items_quantity.get(position));
                if(items_price.size() > position)
                    holder.price.setText(items_price.get(position));

                // Save textbox contents to a list
                cacheTextContent(holder, position);

                break;
            case CommonUtils.VOUCHER_TOOL: // TODO: Add to viewholder
                if(convertView == null){
                    convertView = inflater.inflate(R.layout.fragment_tool_rows, null);
                    btn_delete = (Button)convertView.findViewById(R.id.tool_delete);
                }
                break;
        }

        if(btn_delete != null){

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }

    static class ViewHolder{
        EditText item;
        EditText details;
        EditText quantity;
        EditText price;
    }

    private void cacheTextContent(ViewHolder holder, final int position){
        holder.item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if(items_item.size() <= position)
                    items_item.add(position, e.toString());
                else
                    items_item.set(position, e.toString());
            }
        });

        holder.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if(items_quantity.size() <= position)
                    items_quantity.add(position, e.toString());
                else
                    items_quantity.set(position, e.toString());
            }
        });

        holder.price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if(items_price.size() <= position)
                    items_price.add(position, e.toString());
                else
                    items_price.set(position, e.toString());
            }
        });
    }
}