package com.anilicious.rigfinances.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import com.anilicious.rigfinances.activities.AddItemListAdapter;
import com.anilicious.rigfinances.activities.VouchersActivity;
import com.anilicious.rigfinances.beans.AddItem;
import com.anilicious.rigfinances.beans.Cook;
import com.anilicious.rigfinances.beans.CookItem;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANBARASI on 11/11/14.
 */
public class CookFragment extends Fragment {

    private List<CookItem> cookItems;
    private AddItemListAdapter list_cook_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vouchers_cook, null);
        setupCookList(view);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        //final EditText etTotalAmount = (EditText)view.findViewById(R.id.editText3);
        final EditText etSpentBy = (EditText)view.findViewById(R.id.editText5);

        // On Click of Submit
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(((VouchersActivity)getActivity()).validForm()){ // TODO: Test validation
                    //float totalAmount = Float.parseFloat(etTotalAmount.getText().toString());
                    String spentBy = etSpentBy.getText().toString();
                    //List<CookItem> cookItems = addCookItems();

                    ViewGroup group = (ViewGroup)getActivity().findViewById(R.id.list_cook);
                    //for(int i = 0; i < group.getChildCount(); i++){
                    for(CookItem cookItem : cookItems){
                        //View form_field = group.getChildAt(i);
                        //EditText etItem = (EditText)form_field.findViewById(R.id.addItem_item);
                        //EditText etQuantity = (EditText)form_field.findViewById(R.id.addItem_quantity);
                        //EditText etPrice = (EditText)form_field.findViewById(R.id.addItem_price);

                        //String item = etItem.getText().toString();
                        String item = cookItem.getItem();
                        //int quantity = Integer.parseInt(etQuantity.getText().toString());
                        int quantity = cookItem.getQuantity();
                        //float price = Float.parseFloat(etPrice.getText().toString());
                            float price = cookItem.getAmount();

                        //CookItem cookItem = new CookItem(item, quantity, price);
                        //cookItems.add(cookItem);

                        DebitFragment parent = (DebitFragment)getParentFragment();

                        Cook cook = new Cook();
                        //TODO: Calculate Total Amount?
                        cook.setSpentBy(spentBy);
                        cook.setItem(item);
                        cook.setQuantity(quantity);
                        cook.setPrice(price);

                        String date = parent.getEntryDate().toString();
                        Integer Cook_date = Integer.parseInt(CommonUtils.formatDateEntry(date));  // TODO: Test
                        cook.setDate(Cook_date);

                        // Insert to DB
                        DBAdapter dbAdapter = DBAdapter.getInstance(getActivity());
                        dbAdapter.insertCook(cook);
                    }

                    // Clear the Form
                    ((VouchersActivity)getActivity()).clearForm();
                    cookItems.clear();
                    list_cook_adapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    // Loop through and add the list of Cook Items
    public List<CookItem> addCookItems(){
        List<CookItem> cookItems = new ArrayList<CookItem>();

        ViewGroup group = (ViewGroup)getActivity().findViewById(R.id.list_cook);
        for(int i = 0; i < group.getChildCount(); i++){
            View form_field = group.getChildAt(i);
            EditText etItem = (EditText)form_field.findViewById(R.id.addItem_item);
            EditText etQuantity = (EditText)form_field.findViewById(R.id.addItem_quantity);
            EditText etPrice = (EditText)form_field.findViewById(R.id.addItem_price);

            String item = etItem.getText().toString();
            int quantity = Integer.parseInt(etQuantity.getText().toString());
            float price = Float.parseFloat(etPrice.getText().toString());

            CookItem cookItem = new CookItem(item, quantity, price);
            cookItems.add(cookItem);
        }

        return cookItems;
    }

    public void setupCookList(View view){
        cookItems = new ArrayList<CookItem>();

        ListView list_cook = (ListView)view.findViewById(R.id.list_cook);
        list_cook_adapter = new AddItemListAdapter(getActivity(), cookItems, CommonUtils.VOUCHER_COOK);
        list_cook.setAdapter(list_cook_adapter);

        Button btn_addItem = (Button)view.findViewById(R.id.button_addItem);
        btn_addItem.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.fragment_cook_rows);
                dialog.setTitle("Cooking Expense Details");

                final EditText etItem = (EditText)dialog.findViewById(R.id.addItem_item);
                final EditText etQuantity = (EditText)dialog.findViewById(R.id.addItem_quantity);
                final EditText etPrice = (EditText)dialog.findViewById(R.id.addItem_price);

                Button btn_addDetails = (Button)dialog.findViewById(R.id.cook_submit_details);
                btn_addDetails.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        ViewGroup group = (ViewGroup)dialog.findViewById(R.id.addCookItem_parent);
                        if(CommonUtils.validForm(group)){
                            String item = etItem.getText().toString();
                            int quantity = Integer.parseInt(etQuantity.getText().toString());
                            float price = Float.parseFloat(etPrice.getText().toString());

                            CookItem cookItem = new CookItem(item, quantity, price);
                            cookItems.add(cookItem);
                            list_cook_adapter.notifyDataSetChanged();

                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
     });
    }
}
