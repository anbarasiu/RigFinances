package com.anilicious.rigfinances.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.anilicious.rigfinances.beans.Bore;
import com.anilicious.rigfinances.beans.Employee;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.Calendar;

public class EmployeeDetailsActivity extends ActionBarActivity {

    EditText etDate;
    EditText etDoj;
    EditText etDol;

    static final int DATE_DIALOG_ID = 1;
    private int pHour;
    private int pMinute;
    private int currentYear;
    private int currentMonth;
    private int currentDate;

    SharedPreferences sharedPrefs;

    private static final int DATE_FIELD_ID = 1;
    private static final int DOJ_FIELD_ID = 2;
    private static final int DOL_FIELD_ID = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeedetails);

        // UI Object References
        Button btnSubmit = (Button)findViewById(R.id.btn_submit);
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
                                Integer Employee_date=Integer.parseInt(date1);
                                int employeeNumber = Integer.parseInt(etEmployeeNumber.getText().toString());
                                String employeeName = etEmployeeName.getText().toString();
                                String date2 = etDoj.getText().toString();
                                String date3 = etDol.getText().toString();
                                test=date2.split("/");
                                if(test[1].length()<=1)
                                {
                                    test[1] = "0"+test[1];
                                }
                                if(test[0].length()<=1)
                                {
                                    test[0] = "0"+test[0];
                                }
                                date1 =(test[2]+test[1]+test[0]);
                                Integer dateOfJoining=Integer.parseInt(date1);
                                test=date3.split("/");
                                if(test[1].length()<=1)
                                {
                                    test[1] = "0"+test[1];
                                }
                                if(test[0].length()<=1)
                                {
                                    test[0] = "0"+test[0];
                                }
                                date1 =(test[2]+test[1]+test[0]);
                                Integer dateOfLeaving=Integer.parseInt(date1);
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

        sharedPrefs = this.getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);

        // On 'Back to Home' button click
        Button home_btn = (Button)findViewById(R.id.btn_home);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
     * Setup the Time Picker Dialog
     */
    private void setupPickers(){

        /** Get the current time */
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

    /** Callback received when the user "picks" a date in the dialog */
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

    /*
     *  Reset form once Submitted button is clicked
     */
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
        // For now, we've only Settings
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);

        return true;
    }

}
