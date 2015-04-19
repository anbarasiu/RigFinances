package com.anilicious.rigfinances.activities;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anilicious.rigfinances.finances.R;

/**
 * Created by ANBARASI on 26/12/14.
 */
public class GridImageAdapter extends BaseAdapter {
    private Context context;
    private final String[] gridValues;

    public GridImageAdapter(Context context, String[] gridValues){
        this.context = context;
        this.gridValues = gridValues;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.main_grid_element, null);

            // set value into textview
            TextView tvDescription = (TextView) gridView.findViewById(R.id.main_grid_text);
            //TextView tvIcon = (TextView) gridView.findViewById(R.id.main_grid_icon);
            tvDescription.setText(gridValues[position]);
            //tvIcon.setText(gridValues[position].substring(0,1));

            String gridValue = gridValues[position];

            if (gridValue.equals("Vouchers")) {
                tvDescription.setBackgroundResource(R.drawable.vouchers);
            } else if (gridValue.equals("Bore Entry")) {
                tvDescription.setBackgroundResource(R.drawable.bore_entry);
            } else if (gridValue.equals("Reports")) {
                tvDescription.setBackgroundResource(R.drawable.reports);
            } else if (gridValue.equals("Employee Details")){
                tvDescription.setBackgroundResource(R.drawable.employee_details);
            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return gridValues.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
