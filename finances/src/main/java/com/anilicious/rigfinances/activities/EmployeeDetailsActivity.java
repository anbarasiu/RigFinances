package com.anilicious.rigfinances.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.anilicious.rigfinances.fragments.EmployeeDetailsDateFragment;
import com.anilicious.rigfinances.fragments.EmployeeDetailsRegistrationFragment;
import com.anilicious.rigfinances.fragments.ReportBoreFragment;
import com.anilicious.rigfinances.fragments.ReportExpensesFragment;
import com.anilicious.rigfinances.fragments.ReportSalaryFragment;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.Calendar;

public class EmployeeDetailsActivity extends ActionBarActivity implements ActionBar.TabListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeedetails);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText(R.string.employeedetails_tab1).setTabListener(this);
        actionBar.addTab(tab1);

        ActionBar.Tab tab3 = actionBar.newTab();
        tab3.setText(R.string.employeedetails_tab2).setTabListener(this);
        actionBar.addTab(tab3);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        int fragmentContainer = R.id.employee_details_container;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch(tab.getPosition()){
            case 0:
                EmployeeDetailsRegistrationFragment employeeDetailsRegistrationFragment = new EmployeeDetailsRegistrationFragment();
                ft.replace(fragmentContainer, employeeDetailsRegistrationFragment);
                ft.commit();
                break;
            case 1:
                EmployeeDetailsDateFragment employeeDetailsDateFragment = new EmployeeDetailsDateFragment();
                ft.replace(fragmentContainer, employeeDetailsDateFragment);
                ft.commit();
                break;
        }
    }

    /*
     *  Reset form once Submitted button is clicked
     */
    public void clearForm(){
        ViewGroup group = (ViewGroup)findViewById(R.id.employee_details_container);
        CommonUtils.clearForm(group);
    }

    public boolean validForm(){
        ViewGroup group = (ViewGroup)findViewById(R.id.employee_details_container);
        return CommonUtils.validForm(group);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}