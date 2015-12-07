package com.anilicious.rigfinances.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.anilicious.rigfinances.activities.VouchersActivity;
import com.anilicious.rigfinances.beans.Diesel;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;

/**
 * Created by ANBARASI on 9/11/14.
 */
public class DieselFragment extends Fragment {

    private Diesel diesel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vouchers_diesel, null);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        final RadioGroup rgDieselFor = (RadioGroup)view.findViewById(R.id.diesel_radioGroup);
        final EditText etLitres = (EditText)view.findViewById(R.id.editText);
        final EditText etTotalAmount = (EditText)view.findViewById(R.id.editText2);
        final EditText etSpentBy = (EditText)view.findViewById(R.id.editText3);

        // On Form Submission
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(((VouchersActivity)getActivity()).validForm()){
                    int litres = Integer.parseInt(etLitres.getText().toString());
                    int totalAmount = Integer.parseInt(etTotalAmount.getText().toString());
                    String spentBy = etSpentBy.getText().toString();
                    RadioButton rbDieselFor = (RadioButton)rgDieselFor.findViewById(rgDieselFor.getCheckedRadioButtonId());

                    String dieselFor = rbDieselFor.getText().toString();
                    DebitFragment parent = (DebitFragment)getParentFragment();

                    Diesel diesel = new Diesel();
                    diesel.setLitres(litres);
                    diesel.setTotalAmount(totalAmount);
                    diesel.setSpentBy(spentBy);
                    diesel.setDieselFor(dieselFor);
                    if(parent.getEntryDate().equals(""))
                    {
                        etLitres.setError("asdf");
                    }
                        else
                    {
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
                        Integer Diesel_date=Integer.parseInt(date1);
                        diesel.setDate(Diesel_date);

                        // Insert to DB
                        DBAdapter dbAdapter = DBAdapter.getInstance(getActivity());
                        dbAdapter.insertDiesel(diesel);

                        // Clear the Form
                        ((VouchersActivity)getActivity()).clearForm();
                    }
                }
            }
        });

        return view;
    }
}
