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

                if(etLitres.getText().toString().equals(""))
                {
                    etLitres.setError("Please enter diesel in liters");
                }
                else
                {
                    if(etTotalAmount.getText().toString().equals(""))
                    {
                        etTotalAmount.setError("Please enter total amount spent");
                    }
                    else
                    {
                        if(etSpentBy.getText().toString().equals(""))
                        {
                            etSpentBy.setError("Please enter name who spent for diesel");
                        }
                        else
                        {
                            int litres = Integer.parseInt(etLitres.getText().toString());
                            int totalAmount = Integer.parseInt(etTotalAmount.getText().toString());
                            String spentBy = etSpentBy.getText().toString();
                            RadioButton rbDieselFor = (RadioButton)rgDieselFor.findViewById(rgDieselFor.getCheckedRadioButtonId());

                            String dieselFor = rbDieselFor.getText().toString();

                            // VouchersActivity v = (VouchersActivity)getActivity();
                            DebitFragment parent = (DebitFragment)getParentFragment();

                            Diesel diesel = new Diesel();
                            diesel.setLitres(litres);
                            diesel.setTotalAmount(totalAmount);
                            diesel.setSpentBy(spentBy);
                            diesel.setDieselFor(dieselFor);
                            diesel.setDate(parent.getEntryDate());

                            // Insert to DB
                            DBAdapter dbAdapter = DBAdapter.getInstance(getActivity());
                            dbAdapter.insertDiesel(diesel);

                            // Clear the Form
                            ((VouchersActivity)getActivity()).clearForm();

                        }

                    }
            }
            }
        });

        return view;
    }
}
