package com.anilicious.rigfinances.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.anilicious.rigfinances.activities.MainActivity;
import com.anilicious.rigfinances.activities.VouchersActivity;
import com.anilicious.rigfinances.beans.Credit;
import com.anilicious.rigfinances.beans.Diesel;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;
import com.anilicious.rigfinances.utils.PickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ANBARASI on 11/11/14.
 */
public class CreditFragment extends Fragment {

    EditText date_tv;
    String entryDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vouchers_credit, null);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        date_tv = (EditText)view.findViewById(R.id.date_tv);
        final EditText etAmountReceived = (EditText)view.findViewById(R.id.editText);
        final EditText etAmountReceivedFrom = (EditText)view.findViewById(R.id.editText3);
        final EditText etReceivedBy = (EditText)view.findViewById(R.id.editText4);
        final EditText etRemarks = (EditText)view.findViewById(R.id.editText5);

        // Setup Date Picker
        setupDatePicker();

        // On Form Submission
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Calendar inserted_date_c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                String formattedDate = df.format(inserted_date_c.getTime());
                int inserted_date = Integer.parseInt(formattedDate);
                if(((VouchersActivity)getActivity()).validForm()){ // TODO: Test validation
                    int amountReceived = Integer.parseInt(etAmountReceived.getText().toString());
                    String amountReceivedFrom = etAmountReceivedFrom.getText().toString();
                    String receivedBy = etReceivedBy.getText().toString();
                    String remarks = etRemarks.getText().toString();

                    Credit credit = new Credit();
                    credit.setAmountReceived(amountReceived);
                    credit.setFrom(amountReceivedFrom);
                    credit.setReceivedBy(receivedBy);
                    credit.setRemarks(remarks);
                    credit.setDate(entryDate);
                    credit.setInsertedDate(inserted_date);
                    // Insert to DB
                    DBAdapter dbAdapter = DBAdapter.getInstance(getActivity());
                    dbAdapter.insertCredit(credit);

                    // Clear the Form
                    ((VouchersActivity)getActivity()).clearForm();
                }
            }
        });
        return view;
    }

    // Begin - DatePicker Methods
    private void setupDatePicker(){
        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment picker = new PickerFragment(CommonUtils.DIALOG_DATE);
                picker.setTargetFragment(CreditFragment.this, 1); // todo: Declare Constant
                picker.show(getFragmentManager(), "datePicker");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 101){
            entryDate = data.getStringExtra("entryDate");
            date_tv.setText(entryDate);
        }
    }
    // End - DatePicker Methods
}