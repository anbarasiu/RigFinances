package com.anilicious.rigfinances.fragments;

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
    private List<String> tool_items_dummy;
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
//                float totalAmount = Float.parseFloat(etTotalAmount.getText().toString());
                String spentBy = etSpentBy.getText().toString();

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

                    DebitFragment parent = (DebitFragment)getParentFragment();

                    Tools tool = new Tools();
                    tool.setSpentBy(spentBy);
                    tool.setItem(item);
                    tool.setDetails(details);
                    tool.setQuantity(quantity);
                    tool.setPrice(price);
                    tool.setDate(parent.getEntryDate());

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
        ListView list_tool = (ListView) view.findViewById(R.id.list_tool);
        tool_items_dummy = new ArrayList<String>();
        list_tool_adapter = new AddItemListAdapter(getActivity(), tool_items_dummy, CommonUtils.VOUCHER_TOOL);
        list_tool.setAdapter(list_tool_adapter);

        Button btn_addItem = (Button)view.findViewById(R.id.button_addItem);
        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tool_items_dummy.add("New Dummy Row");
                list_tool_adapter.notifyDataSetChanged();
            }
        });
    }
}
