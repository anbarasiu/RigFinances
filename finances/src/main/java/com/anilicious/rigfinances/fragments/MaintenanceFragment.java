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
                if(((VouchersActivity)getActivity()).validForm()){ // TODO: Test validation
                    boolean service = swService.isChecked();
                    float totalAmount = Float.parseFloat(etTotalAmount.getText().toString());
                    String remarks = eRemarks.getText().toString();
                    String spentBy = etSpentBy.getText().toString();
                    RadioButton rbWorkType = (RadioButton)rgWorkType.findViewById(rgWorkType.getCheckedRadioButtonId());
                    String workType = rbWorkType.getText().toString();
                    double engineHrs = Double.parseDouble(etEngineHrs1.getText().toString());

                    if(etEngineHrs1.getText().toString().equals("") && etEngineHrs2.getText().toString().equals(""))
                    {
                        etEngineHrs1.setError("Please enter engine hours at the time of service, please enter min also, if min is 0 enter 0 and if it is not service please enter 0 in both");
                    }
                    else
                    {
                        if(swService.isChecked() && workType.equals("Compressor"))
                        {
                            double min = Double.parseDouble(etEngineHrs2.getText().toString());
                            if(min >= 60)
                            {
                                Double rem = min%60;
                                min = min - rem;
                                engineHrs = (engineHrs +(min/60)) + (rem * 1.6666667/100);
                            }
                            else {
                                engineHrs = engineHrs + (min*1.666667/100);
                            }
                        }
                        else {
                            engineHrs = 0;
                        }

                        DebitFragment parent = (DebitFragment)getParentFragment();
                        Maintenance maintenance = new Maintenance();
                        maintenance.setService(service);
                        maintenance.setEngineHrs(engineHrs);
                        maintenance.setAmount(totalAmount);
                        maintenance.setReason(remarks);
                        maintenance.setSpentBy(spentBy);
                        maintenance.setWorkType(workType);
                        String date = parent.getEntryDate().toString();
                        String[] test=date.split("/");

                        if(test[1].length()<=1) {
                            test[1] = "0" + test[1];
                        }
                        if(test[0].length() <= 1) {
                            test[0] = "0" + test[0];
                        }
                        String date1 =(test[2] + test[1] + test[0]);
                        Integer Maintenance_date=Integer.parseInt(date1);
                        maintenance.setDate(Maintenance_date);
                        // Insert to DB
                        DBAdapter dbAdapter = DBAdapter.getInstance(getActivity());
                        dbAdapter.insertMaintenance(maintenance);

                        // Clear the Form
                        ((VouchersActivity)getActivity()).clearForm();
                    }
                }
            }
        });

        return view;
    }
}