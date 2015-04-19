package com.anilicious.rigfinances.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.anilicious.rigfinances.activities.VouchersActivity;
import com.anilicious.rigfinances.beans.Credit;
import com.anilicious.rigfinances.beans.Maintenance;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;

/**
 * Created by ANBARASI on 11/11/14.
 */
public class MaintenanceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vouchers_maintenance, null);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        final RadioGroup rgWorkType = (RadioGroup)view.findViewById(R.id.maintenance_workType);
        final Switch swService = (Switch)view.findViewById(R.id.switch1);
        final EditText etEngineHrs1 = (EditText)view.findViewById(R.id.editText);
        final EditText etEngineHrs2 = (EditText)view.findViewById(R.id.editText2);
        final EditText etTotalAmount = (EditText)view.findViewById(R.id.editText3);
        final EditText eRemarks = (EditText)view.findViewById(R.id.editText4);
        final EditText etSpentBy = (EditText)view.findViewById(R.id.editText5);

        // On Form Submission
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean service = swService.isChecked();
                double engineHrs = (double)(Integer.parseInt(etEngineHrs1.getText().toString()) + Integer.parseInt(etEngineHrs2.getText().toString())/60);
                float totalAmount = Float.parseFloat(etTotalAmount.getText().toString());
                String remarks = eRemarks.getText().toString();
                String spentBy = etSpentBy.getText().toString();
                RadioButton rbWorkType = (RadioButton)rgWorkType.findViewById(rgWorkType.getCheckedRadioButtonId());
                String workType = rbWorkType.getText().toString();

                DebitFragment parent = (DebitFragment)getParentFragment();
                
                Maintenance maintenance = new Maintenance();
                maintenance.setService(service);
                maintenance.setEngineHrs(engineHrs);
                maintenance.setAmount(totalAmount);
                maintenance.setReason(remarks);
                maintenance.setSpentBy(spentBy);
                maintenance.setWorkType(workType);
                maintenance.setDate(parent.getEntryDate());

                // Insert to DB
                DBAdapter dbAdapter = DBAdapter.getInstance(getActivity());
                dbAdapter.insertMaintenance(maintenance);

                // Clear the Form
                ((VouchersActivity)getActivity()).clearForm();
            }
        });

        return view;
    }
}
