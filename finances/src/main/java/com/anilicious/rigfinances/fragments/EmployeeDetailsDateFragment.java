package com.anilicious.rigfinances.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.anilicious.rigfinances.activities.EmployeeDetailsActivity;
import com.anilicious.rigfinances.activities.MainActivity;
import com.anilicious.rigfinances.activities.SettingsActivity;
import com.anilicious.rigfinances.activities.VouchersActivity;
import com.anilicious.rigfinances.beans.Employee;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by ANBARASI on 12/29/15.
 */
public class EmployeeDetailsDateFragment extends Fragment {

    EditText etDate;
    EditText etDoj;
    EditText etDol;

    static final int DATE_DIALOG_ID = 1;
    private int pHour;
    private int pMinute;
    private int currentYear;
    private int currentMonth;
    private int currentDate;
    Spinner employee;
    int employeeNumber;
    String employeeName;

    SharedPreferences sharedPrefs;
    DBAdapter dbAdapter;

    private static final int DATE_FIELD_ID = 1;
    private static final int DOJ_FIELD_ID = 2;
    private static final int DOL_FIELD_ID = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_details, null);

        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        employee = (Spinner)view.findViewById(R.id.spinner);
        final EditText etEmployeeNumber = (EditText)view.findViewById(R.id.editText1);
        etDoj = (EditText)view.findViewById(R.id.editText4);
        etDol = (EditText)view.findViewById(R.id.editText5);
        etDate = (EditText)view.findViewById(R.id.editText6);

        dbAdapter = DBAdapter.getInstance(getActivity().getApplicationContext());

        // Setup Date Picker
        setupPickers();

        // Load Existing Employees
        loadEmployees();

        // On Form Submission
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Calendar inserted_date_c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                String formattedDate = df.format(inserted_date_c.getTime());
                int inserted_date = Integer.parseInt(formattedDate);
                if(((EmployeeDetailsActivity)getActivity()).validForm()){
                    String date2 = etDoj.getText().toString();
                    String date3 = etDol.getText().toString();
                    Integer dateOfJoining = Integer.parseInt(CommonUtils.formatDateEntry(date2));
                    Integer dateOfLeaving = Integer.parseInt(CommonUtils.formatDateEntry(date3));

                    Employee employee = new Employee();
                    employee.setNumber(employeeNumber);
                    employee.setName(employeeName);
                    employee.setDateOfJoining(dateOfJoining);
                    employee.setDateOfLeaving(dateOfLeaving);
                    employee.setInsertedDate(inserted_date);

                    if(employee.isExEmployee()){
                        dbAdapter.insertEmployee(employee);
                    } else{
                        dbAdapter.updateEmployee(employee);
                    }

                    // Re-load Existing Employees
                    loadEmployees();
                }
            }
        });

        sharedPrefs = this.getActivity().getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);

        return view;
    }

    /*
    * Setup the Time Picker Dialog
    */
    private void setupPickers(){

        /* Get the current time */
        final Calendar cal = Calendar.getInstance();
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH) + 1;
        currentDate = cal.get(Calendar.DAY_OF_MONTH);

        etDoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), mDateOfJoiningSetListener, currentYear, currentMonth, currentDate).show();
            }
        });

        etDol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), mDateOfLeavingSetListener, currentYear, currentMonth, currentDate).show();
            }
        });
    }

   // TODO: Is there a better way instead of these multiple callbacks for different datepicker fields?

   /* Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener mDateOfJoiningSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    etDoj.setText(new StringBuilder().append(day).append("/")
                            .append(month + 1).append("/").append(year).toString());
                }
            };

    private DatePickerDialog.OnDateSetListener mDateOfLeavingSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    etDol.setText(new StringBuilder().append(day).append("/")
                            .append(month + 1).append("/").append(year).toString());
                }
            };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent homeIntent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        else{
            Intent intent = new Intent(getActivity().getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadEmployees(){
        String[] employees = dbAdapter.retrieveExistingEmployees();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, employees);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employee.setAdapter(arrayAdapter);

        employee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] selectedEmp = adapterView.getSelectedItem().toString().split(" : ");
                employeeNumber = Integer.parseInt(selectedEmp[0]);
                employeeName = selectedEmp[1];

                HashMap<String, String> employeeDetails = dbAdapter.retrieveEmployeeDetails(employeeNumber);
                etDoj.setText(CommonUtils.reverseFormatDateEntry(employeeDetails.get("joining_date")));
                etDol.setText(CommonUtils.reverseFormatDateEntry(employeeDetails.get("leaving_date")));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public boolean employeeExists(int employeeNumber){
        if(dbAdapter.retrieveEmployeeDetails(employeeNumber).size() > 0){
            return true;
        }
        return false;
    }
}