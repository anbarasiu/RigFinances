package com.anilicious.rigfinances.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.fragments.CookFragment;
import com.anilicious.rigfinances.fragments.CreditFragment;
import com.anilicious.rigfinances.fragments.DebitFragment;
import com.anilicious.rigfinances.fragments.DieselFragment;
import com.anilicious.rigfinances.fragments.MaintenanceFragment;
import com.anilicious.rigfinances.fragments.PipeFragment;
import com.anilicious.rigfinances.fragments.RoadFragment;
import com.anilicious.rigfinances.fragments.SalaryFragment;
import com.anilicious.rigfinances.fragments.SiteFragment;
import com.anilicious.rigfinances.fragments.ToolFragment;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.Calendar;

/*
 * Vouchers Activity with dynamic sub-views for the different types
 */

public class VouchersActivity extends ActionBarActivity implements TabListener{

    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vouchers);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Tab tab1 = actionBar.newTab();
        tab1.setText("Debit").setTabListener(this);
        actionBar.addTab(tab1);

        Tab tab2 = actionBar.newTab();
        tab2.setText("Credit").setTabListener(this);
        actionBar.addTab(tab2);

        sharedPrefs = this.getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {

        int fragmentContainer = R.id.vouchers_parent;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if(tab.getPosition() == 0){     //Debit
            DebitFragment debitFragment = new DebitFragment();
            ft.replace(fragmentContainer, debitFragment);
            ft.commit();
        }
        else if(tab.getPosition() == 1){     //Credit
            CreditFragment creditFragment = new CreditFragment();
            ft.replace(fragmentContainer, creditFragment);
            ft.commit();
        }

    }

    /*
     *  Reset form once Submitted button is clicked
     */
    public void clearForm(){
        ViewGroup group = (ViewGroup)findViewById(R.id.vouchers_parent);
        CommonUtils.clearForm(group);
    }

    public boolean validForm(){
        ViewGroup group = (ViewGroup)findViewById(R.id.vouchers_parent);
        return CommonUtils.validForm(group);
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {

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
    }
}