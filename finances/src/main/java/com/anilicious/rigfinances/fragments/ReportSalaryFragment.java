package com.anilicious.rigfinances.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anilicious.rigfinances.activities.ReportsActivity;
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
    LinearLayout reportSection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports_salary, null);

        // Attach listeners
        reportSection = (LinearLayout) view.findViewById(R.id.salary_section);
        etEmpNumber =(EditText)view.findViewById(R.id.editText);
        //TODO Financial Year
        tvEmpName =(TextView)view.findViewById(R.id.TextView_empName);
        tvJoiningDate1 =(TextView)view.findViewById(R.id.TextView_joiningDate1);
        tvLeavingDate1 =(TextView)view.findViewById(R.id.TextView_leavingDate1);
        tvJoiningDate2 =(TextView)view.findViewById(R.id.TextView_joiningDate2);
        tvLeavingDate2 =(TextView)view.findViewById(R.id.TextView_leavingDate2);
        tvJoiningDate3 =(TextView)view.findViewById(R.id.TextView_joiningDate3);
        tvLeavingDate3 =(TextView)view.findViewById(R.id.TextView_leavingDate3);
        tvTotalWorked =(TextView)view.findViewById(R.id.TextView_totalWorked);
        tvTotalAmount =(TextView)view.findViewById(R.id.TextView_totalAmount);
        tvSalaryGiven =(TextView)view.findViewById(R.id.TextView_salaryGiven);
        tvSalaryWithCompany =(TextView)view.findViewById(R.id.TextView_salaryWithCompany);
        tvTotalAmountSpent =(TextView)view.findViewById(R.id.TextView_totalAmountSpent);
        Button btnGenerate = (Button)view.findViewById(R.id.button_report_salary);
        btnGenerate.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_report_salary){
            renderReport();
        }
    }

    public void renderReport(){
        HashMap<String, String> salaryMap = new HashMap<String, String>();
        ReportsMapper reportsMapper = new ReportsMapper(getActivity());
        salaryMap = reportsMapper.mapSalary(Integer.parseInt(etEmpNumber.getText().toString()));

        String empName = salaryMap.get("employee_name");
        if(empName == null){
            Toast.makeText(getActivity().getApplicationContext(), "Details not found. Please enter a valid Employee Number.", Toast.LENGTH_LONG);
        }
        else{
            reportSection.setVisibility(View.VISIBLE);
        }

        tvEmpName.setText(empName);
        tvJoiningDate1.setText(salaryMap.get("joining_date"));
        tvLeavingDate1.setText(salaryMap.get("leaving_date"));
        tvSalaryGiven.setText(salaryMap.get("salary_given"));
        /*tvTotalWorked.setText(salaryMap.get("salary_given")); // TODO : What goes here?
        tvTotalAmount.setText(salaryMap.get("salary_given")); // TODO : What goes here?
        tvSalaryWithCompany.setText(salaryMap.get("salary_given")); // TODO : What goes here?
        tvTotalAmountSpent.setText(salaryMap.get("salary_given")); */// TODO : What goes here?
    }
}
