package com.anilicious.rigfinances.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.anilicious.rigfinances.activities.AddItemListAdapter;
import com.anilicious.rigfinances.activities.VouchersActivity;
import com.anilicious.rigfinances.beans.AddItem;
import com.anilicious.rigfinances.beans.ToolItem;
import com.anilicious.rigfinances.beans.Tools;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANBARASI on 11/11/14.
 */
public class ToolFragment extends Fragment {
    private List<ToolItem> toolItems;
    private AddItemListAdapter list_tool_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vouchers_tool, null);
        setupToolList(view);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
       // final EditText etTotalAmount = (EditText)view.findViewById(R.id.editText3);
        final EditText etSpentBy = (EditText)view.findViewById(R.id.editText5);

        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(((VouchersActivity)getActivity()).validForm()){
    //                float totalAmount = Float.parseFloat(etTotalAmount.getText().toString());
                    String spentBy = etSpentBy.getText().toString();

                    ViewGroup group = (ViewGroup)getActivity().findViewById(R.id.list_tool);
                    for(ToolItem toolItem : toolItems){
                        //View form_field = group.getChildAt(i);

                        //EditText etItem = (EditText)form_field.findViewById(R.id.addItem_item);
                        //EditText etDetails = (EditText)form_field.findViewById(R.id.addItem_details);
                        //EditText etQuantity = (EditText)form_field.findViewById(R.id.addItem_quantity);
                        //EditText etPrice = (EditText)form_field.findViewById(R.id.addItem_price);

                       // String item = etItem.getText().toString();
                        String item = toolItem.getItem();
                        //String details = etDetails.getText().toString();
                        String details = toolItem.getDetails();
                        //int quantity = Integer.parseInt(etQuantity.getText().toString());
                        int quantity = toolItem.getQuantity();
                        //float price = Float.parseFloat(etPrice.getText().toString());
                        float price = toolItem.getAmount();

                        DebitFragment parent = (DebitFragment)getParentFragment();

                        Tools tool = new Tools();
                        tool.setSpentBy(spentBy);
                        tool.setItem(item);
                        tool.setDetails(details);
                        tool.setQuantity(quantity);
                        tool.setPrice(price);
                        String date = parent.getEntryDate().toString();
                        String[] test=date.split("/");
                        if(test[1].length()<=1)
                        {
                            test[1] = "0"+test[1];
                        }
                        if(test[0].length()<=1)
                        {
                            test[0] = "0"+test[0];
                        }
                        String date1 =(test[2]+test[1]+test[0]);
                        Integer Tool_date=Integer.parseInt(date1);
                        tool.setDate(Tool_date);

                        //ToolItem toolItem = new ToolItem(item, details, quantity, price);
                        //toolItems.add(toolItem);

                        // Insert to DB
                        DBAdapter dbAdapter = DBAdapter.getInstance(getActivity());
                        dbAdapter.insertTool(tool);
                    }

                    /*Tools tool = new Tools();
                    tool.setSpentBy(spentBy);
                    tool.setToolItems(toolItems);*/

                    // Clear the Form
                    ((VouchersActivity)getActivity()).clearForm();
                }
            }
        });

        return view;
    }

    // Loop through and add the list of Tool Items
    public List<ToolItem> addToolItems(){
        List<ToolItem> toolItems = new ArrayList<ToolItem>();

        ViewGroup group = (ViewGroup)getActivity().findViewById(R.id.list_tool);
        for(int i = 0; i < group.getChildCount(); i++){
            View form_field = group.getChildAt(i);

            EditText etItem = (EditText)form_field.findViewById(R.id.addItem_item);
            EditText etDetails = (EditText)form_field.findViewById(R.id.addItem_details);
            EditText etQuantity = (EditText)form_field.findViewById(R.id.addItem_quantity);
            EditText etPrice = (EditText)form_field.findViewById(R.id.addItem_price);

            String item = etItem.getText().toString();
            String details = etDetails.getText().toString();
            int quantity = Integer.parseInt(etQuantity.getText().toString());
            float price = Float.parseFloat(etPrice.getText().toString());

            ToolItem toolItem = new ToolItem(item, details, quantity, price);
            toolItems.add(toolItem);
        }

        return toolItems;
    }

    public void setupToolList(View view){
        toolItems = new ArrayList<ToolItem>();

        ListView list_tool = (ListView) view.findViewById(R.id.list_tool);
        list_tool_adapter = new AddItemListAdapter(getActivity(), toolItems, CommonUtils.VOUCHER_TOOL);
        list_tool.setAdapter(list_tool_adapter);

        //list_tool_adapter = new AddItemListAdapter(getActivity(), tool_items_dummy, CommonUtils.VOUCHER_TOOL);
        //list_tool.setAdapter(list_tool_adapter);

        Button btn_addItem = (Button)view.findViewById(R.id.button_addItem);
        btn_addItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.fragment_tool_rows);
                dialog.setTitle("Tools Expense Details");

                final EditText etItem = (EditText)dialog.findViewById(R.id.addItem_item);
                final EditText etQuantity = (EditText)dialog.findViewById(R.id.addItem_quantity);
                final EditText etPrice = (EditText)dialog.findViewById(R.id.addItem_price);
                final EditText etDetails = (EditText)dialog.findViewById(R.id.addItem_details);

                Button btn_addDetails = (Button)dialog.findViewById(R.id.tool_submit_details);
                btn_addDetails.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        ViewGroup group = (ViewGroup)dialog.findViewById(R.id.addToolItem_parent);
                        if(CommonUtils.validForm(group)){
                            String item = etItem.getText().toString();
                            String details = etDetails.getText().toString();
                            int quantity = Integer.parseInt(etQuantity.getText().toString());
                            float price = Float.parseFloat(etPrice.getText().toString());

                            ToolItem toolItem = new ToolItem(item, details, quantity, price);
                            toolItems.add(toolItem);
                            list_tool_adapter.notifyDataSetChanged();

                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }

            /*
            @Override
            public void onClick(View view) {
                items_dummy.add("New Dummy Row");
                list_tool_adapter.notifyDataSetChanged();
            }
            */
        });
    }
}
