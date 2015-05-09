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
                if(validForm()){
                    String date = etDate.getText().toString();
                    int employeeNumber = Integer.parseInt(etEmployeeNumber.getText().toString());
                    String employeeName = etEmployeeName.getText().toString();
                    String dateOfJoining = etDoj.getText().toString();
                    String dateOfLeaving = etDol.getText().toString();
                    double currentBalance = Double.parseDouble(etCurrentBalance.getText().toString());
                    String remarks = etRemarks.getText().toString();
                    double salary = Double.parseDouble(etSalary.getText().toString());

                    Employee employee = new Employee();
                    employee.setDate(date);
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

                    // Clear the Form
                    clearForm();
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
                showDialog(DATE_DIALOG_ID);
            }
        });

        etDoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        etDol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, mDateSetListener, currentYear, currentMonth, currentDate);
    }

    /** Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    etDate.setText(new StringBuilder().append(day).append("/")
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
