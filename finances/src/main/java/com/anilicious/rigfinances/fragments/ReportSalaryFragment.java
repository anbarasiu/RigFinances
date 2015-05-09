package com.anilicious.rigfinances.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.mappers.ReportsMapper;

import java.util.HashMap;

/**
 * Created by ANBARASI on 25/1/15.
 */
public class ReportSalaryFragment extends Fragment implements View.OnClickListener{

    EditText etEmpNumber;
    TextView tvEmpName;
    TextView tvJoiningDate1;
    TextView tvLeavingDate1;
    TextView tvJoiningDate2;
    TextView tvLeavingDate2;
    TextView tvJoiningDate3;
    TextView tvLeavingDate3;
    TextView tvTotalWorked;
    TextView tvTotalAmount;
    TextView tvSalaryGiven;
    TextView tvSalaryWithCompany;
    TextView tvTotalAmountSpent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports_salary, null);

        // Attach listeners
        etEmpNumber =(EditText)view.findViewById(R.id.editText);
        //TODO Financial Year
        tvEmpName =(TextView)view.findViewById(R.id.TextView_empName);
        tvJoiningDate1 =(TextView)view.findViewById(R.id.TextView_joiningDate1);
        tvLeavingDate1 =(TextView)view.findViewById(R.id.TextView_leavingDate1);
        tvJoiningDate2 =(TextView)view.findViewById(R.id.TextView_joiningDate2);
        tvLeavingDate2 =(TextView)view.findViewById(R.id.TextView_leavingDate2);
        tvJoiningDate3 =(TextView)view.findViewById(R.id.TextView_joiningDate3);
        tvLeavingDate3 =(TextView)view.findViewById(R.id.TextView_leavingDate3);
        TextView tvTotalWorked =(TextView)view.findViewById(R.id.TextView_totalWorked);
        TextView tvTotalAmount =(TextView)view.findViewById(R.id.TextView_totalAmount);
        TextView tvSalaryGiven =(TextView)view.findViewById(R.id.TextView_salaryGiven);
        TextView tvSalaryWithCompany =(TextView)view.findViewById(R.id.TextView_salaryWithCompany);
        TextView tvTotalAmountSpent =(TextView)view.findViewById(R.id.TextView_totalAmountSpent);
        Button btnGenerate = (Button)view.findViewById(R.id.button);
        btnGenerate.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button){
            renderReport();
        }
    }

    public void renderReport(){
        HashMap<String, String> salaryMap = new HashMap<String, String>();
        ReportsMapper reportsMapper = new ReportsMapper(getActivity());
        salaryMap = reportsMapper.mapSalary(Integer.parseInt(etEmpNumber.getText().toString()));

        tvEmpName.setText(salaryMap.get("employee_name"));
        tvJoiningDate1.setText(salaryMap.get("joining_date"));
        tvLeavingDate1.setText(salaryMap.get("leaving_date"));
    }
}
