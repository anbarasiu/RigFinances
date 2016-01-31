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

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

                Calendar inserted_date_c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                String formattedDate = df.format(inserted_date_c.getTime());
                int inserted_date = Integer.parseInt(formattedDate);
                if(((VouchersActivity)getActivity()).validForm()){ // TODO: Test validation
                    String expense = etExpense.getText().toString();
                    int totalAmount = Integer.parseInt(etTotalAmount.getText().toString());
                    String spentBy = etSpentBy.getText().toString();

                    DebitFragment parent = (DebitFragment)getParentFragment();

                    Road road = new Road();
                    road.setExpenseDetails(expense);
                    road.setTotalAmount(totalAmount);
                    road.setSpentBy(spentBy);

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
                    Integer Road_date=Integer.parseInt(date1);
                    road.setDate(Road_date);
                    road.setInsertedDate(inserted_date);
                    // Insert to DB
                    DBAdapter dbAdapter = DBAdapter.getInstance(getActivity());
                    dbAdapter.insertRoad(road);

                    // Clear the Form
                    ((VouchersActivity)getActivity()).clearForm();
                }
            }
        });


        return view;
    }
}
