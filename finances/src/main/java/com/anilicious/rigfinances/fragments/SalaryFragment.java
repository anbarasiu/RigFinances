package com.anilicious.rigfinances.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anilicious.rigfinances.activities.VouchersActivity;
import com.anilicious.rigfinances.beans.Road;
import com.anilicious.rigfinances.beans.Salary;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by ANBARASI on 11/11/14.
 */
public class SalaryFragment extends Fragment {

    DBAdapter dbAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vouchers_salary, null);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        //final EditText etEmployeeName = (EditText)view.findViewById(R.id.editText);
        final EditText etEmployeeNumber = (EditText)view.findViewById(R.id.editText2);
        final EditText etRemarks = (EditText)view.findViewById(R.id.editText4);
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
                if(((VouchersActivity)getActivity()).validForm()){
                    //String employeeName = etEmployeeName.getText().toString();
                    int employeeNumber = Integer.parseInt(etEmployeeNumber.getText().toString());
                    String remarks = etRemarks.getText().toString();
                    int totalAmount = Integer.parseInt(etTotalAmount.getText().toString());
                    String spentBy = etSpentBy.getText().toString();

                    dbAdapter = DBAdapter.getInstance(getActivity());
                    if(!employeeExists(employeeNumber)){
                        Toast.makeText(getActivity().getApplicationContext(), "Employee not found. Please check if Employee Number valid / Employee Details has been entered.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    DebitFragment parent = (DebitFragment)getParentFragment();
                    Salary salary = new Salary();
                    //salary.setEmployeeName(employeeName);
                    salary.setEmployeeNumber(employeeNumber);
                    salary.setReason(remarks);
                    salary.setAmount(totalAmount);
                    salary.setSpentBy(spentBy);
                    salary.setInsertedDate(inserted_date);
                    String date = parent.getEntryDate().toString();
                    Integer salary_date = Integer.parseInt(CommonUtils.formatDateEntry(date));
                    salary.setDate(salary_date);

                    // Insert to DB
                    dbAdapter.insertSalary(salary);

                    // Clear the Form
                    ((VouchersActivity)getActivity()).clearForm();
                }
            }
        });

        return view;
    }

    public boolean employeeExists(int employeeNumber){
        if(dbAdapter.retrieveEmployeeDetails(employeeNumber).size() > 0){
            return true;
        }
        return false;
    }
}
