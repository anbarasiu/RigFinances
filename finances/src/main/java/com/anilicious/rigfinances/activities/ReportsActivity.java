package com.anilicious.rigfinances.activities;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.fragments.ReportBoreFragment;
import com.anilicious.rigfinances.fragments.ReportDieselFragment;
import com.anilicious.rigfinances.fragments.ReportExpensesFragment;
import com.anilicious.rigfinances.fragments.ReportSalaryFragment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class ReportsActivity extends ActionBarActivity implements ActionBar.TabListener{

    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText(R.string.reports_tab1).setTabListener(this);
        actionBar.addTab(tab1);

        ActionBar.Tab tab3 = actionBar.newTab();
        tab3.setText(R.string.reports_tab3).setTabListener(this);
        actionBar.addTab(tab3);

        ActionBar.Tab tab4 = actionBar.newTab();
        tab4.setText(R.string.reports_tab4).setTabListener(this);
        actionBar.addTab(tab4);

        sharedPrefs = this.getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        // Decide if Fragments are required/Just paint the graphs

        int fragmentContainer = R.id.chart_container;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        switch(tab.getPosition()){
            case 0:
                ReportExpensesFragment reportExpensesFragment = new ReportExpensesFragment();
                ft.replace(fragmentContainer, reportExpensesFragment);
                ft.commit();
                break;
            case 1:
                ReportSalaryFragment reportSalaryFragment = new ReportSalaryFragment();
                ft.replace(fragmentContainer, reportSalaryFragment);
                ft.commit();
                break;
            case 2:
                ReportBoreFragment reportBoreFragment = new ReportBoreFragment();
                ft.replace(fragmentContainer, reportBoreFragment);
                ft.commit();
                break;
        }
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
        else if(item.getItemId() == android.R.id.action_download){

        }
        else{
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    private class ExportDatabaseCSVTask extends AsyncTask<String, String, Boolean>{
        private final ProgressDialog progressDialog = new ProgressDialog(this);
        boolean memoryErr = false;

        @Override
        protected void onPreExecute() {
            Toast.makeText(this, "Downloading Reports into a CSV...", Toast.LENGTH_LONG);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            int rowCount = 0;
            int colCount = 0;

            DBAdapter dbAdapter = DBAdapter.getInstance(this);
            Map<String, HashMap<String, String>> csvData = dbAdapter.retrieveAll();

            File sdCardDir = Environment.getExternalStorageDirectory();
            String fileName = "SivagamiBorewells.csv";
            File saveFile = new File(sdCardDir, fileName);
            FileWriter fw = new FileWriter(saveFile);
            BufferedWriter bw = new BufferedWriter(fw);


        }
    }
}