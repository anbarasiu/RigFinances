package com.anilicious.rigfinances.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.anilicious.rigfinances.activities.MainActivity;
import com.anilicious.rigfinances.activities.SettingsActivity;
import com.anilicious.rigfinances.beans.Employee;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.Calendar;

/**
 * Created by ANBARASI on 12/29/15.
 */
public class EmployeeDetailsRegistrationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_registration, null);

        // UI Object References
        /*Button btnSubmit = (Button)findViewById(R.id.btn_submit);
        etDate = (EditText)findViewById(R.id.editText);
        final EditText etEmployeeNumber = (EditText)findViewById(R.id.editText4);
        final EditText etEmployeeName = (EditText)findViewById(R.id.editText3);
        etDoj = (EditText)findViewById(R.id.editText5);
        etDol = (EditText)findViewById(R.id.editText2);
        final EditText etCurrentBalance = (EditText)findViewById(R.id.editText6);
        final EditText etRemarks = (EditText)findViewById(R.id.editText7);
        final EditText etSalary = (EditText)findViewById(R.id.editText8);

        // Setup Date Picker
        setupPickers();

        // On Form Submission
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(etEmployeeNumber.getText().toString().equals("")){
                    etEmployeeNumber.setError("Please enter employee number");
                }
                else
                {
                    if(etEmployeeName.getText().toString().equals(""))
                    {
                        etEmployeeName.setError("Please enter employee name");
                    }
                    else
                    {
                        if(etCurrentBalance.getText().toString().equals(""))
                        {
                            etCurrentBalance.setError("Please enter the balance, if balance is 0 enter zero");
                        }
                        else
                        {
                            if (etSalary.getText().toString().equals(""))
                            {
                                etSalary.setError("Please enter salary or amount given");
                            }
                            else
                            {
                                String date = etDate.getText().toString();
                                Integer Employee_date = Integer.parseInt(CommonUtils.formatDateEntry(date));
                                int employeeNumber = Integer.parseInt(etEmployeeNumber.getText().toString());
                                String employeeName = etEmployeeName.getText().toString();
                                String date2 = etDoj.getText().toString();
                                String date3 = etDol.getText().toString();
                                Integer dateOfJoining = Integer.parseInt(CommonUtils.formatDateEntry(date2));
                                Integer dateOfLeaving=Integer.parseInt(CommonUtils.formatDateEntry(date3));
                                double currentBalance = Double.parseDouble(etCurrentBalance.getText().toString());
                                String remarks = etRemarks.getText().toString();
                                double salary = Double.parseDouble(etSalary.getText().toString());

                                Employee employee = new Employee();
                                employee.setDate(Employee_date);
                                employee.setName(employeeName);
                                employee.setNumber(employeeNumber);
                                employee.setDateOfJoining(dateOfJoining);
                                employee.setDateOfLeaving(dateOfLeaving);
                                employee.setCurrentBalance(currentBalance);
                                employee.setRemarks(remarks);
                                employee.setSalary(salary);

                                // Insert to DB
                                DBAdapter dbAdapter = DBAdapter.getInstance(getApplicationContext());
                                dbAdapter.insertEmployee(employee);
                                //etEmployeeNumber.setError(""+employeeNumber); WHY??!?!?!
                                // Clear the Form
                                clearForm();
                            }
                        }
                    }

                }
            }
        });

        sharedPrefs = this.getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);*/
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    /*
    * Setup the Time Picker Dialog
    */
    /*private void setupPickers(){

        *//** Get the current time *//*
        final Calendar cal = Calendar.getInstance();
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH);
        currentDate = cal.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_FIELD_ID);
            }
        });

        etDoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DOJ_FIELD_ID);
            }
        });

        etDol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DOL_FIELD_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case DATE_FIELD_ID:
                return new DatePickerDialog(this, mDateSetListener, currentYear, currentMonth, currentDate);
            case DOJ_FIELD_ID:
                return new DatePickerDialog(this, mDateOfJoiningSetListener, currentYear, currentMonth, currentDate);
            case DOL_FIELD_ID:
                return new DatePickerDialog(this, mDateOfLeavingSetListener, currentYear, currentMonth, currentDate);
        }
        return null;
    }

    // TODO: Is there a better way instead of these multiple callbacks for different datepicker fields?

    *//** Callback received when the user "picks" a date in the dialog *//*
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    etDate.setText(new StringBuilder().append(day).append("/")
                            .append(month).append("/").append(year).toString());
                }
            };

    private DatePickerDialog.OnDateSetListener mDateOfJoiningSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    etDoj.setText(new StringBuilder().append(day).append("/")
                            .append(month).append("/").append(year).toString());
                }
            };

    private DatePickerDialog.OnDateSetListener mDateOfLeavingSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    etDol.setText(new StringBuilder().append(day).append("/")
                            .append(month).append("/").append(year).toString());
                }
            };

    *//*
     *  Reset form once Submitted button is clicked
     *//*
    public void clearForm(){
        ViewGroup group = (ViewGroup)findViewById(R.id.employeeDetails_parent);
        CommonUtils.clearForm(group);
    }

    public boolean validForm(){
        ViewGroup group = (ViewGroup)findViewById(R.id.employeeDetails_parent);
        return CommonUtils.validForm(group);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        if(sharedPrefs.getString(getString(R.string.user_role), "USER").equals("ADMIN")){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_actions, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        else{
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }*/
}
