package com.anilicious.rigfinances.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.anilicious.rigfinances.activities.VouchersActivity;
import com.anilicious.rigfinances.beans.Road;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;

/**
 * Created by ANBARASI on 11/11/14.
 */
public class RoadFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vouchers_road, null);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        final EditText etExpense = (EditText)view.findViewById(R.id.editText);
        final EditText etTotalAmount = (EditText)view.findViewById(R.id.editText3);
        final EditText etSpentBy = (EditText)view.findViewById(R.id.editText5);

        // On Form Submission
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String expense = etExpense.getText().toString();
                int totalAmount = Integer.parseInt(etTotalAmount.getText().toString());
                String spentBy = etSpentBy.getText().toString();

                DebitFragment parent = (DebitFragment)getParentFragment();

                Road road = new Road();
                road.setExpenseDetails(expense);
                road.setTotalAmount(totalAmount);
                road.setSpentBy(spentBy);
                road.setDate(parent.getEntryDate());

                // Insert to DB
                DBAdapter dbAdapter = DBAdapter.getInstance(getActivity());
                dbAdapter.insertRoad(road);

                // Clear the Form
                ((VouchersActivity)getActivity()).clearForm();
            }
        });


        return view;
    }
}
