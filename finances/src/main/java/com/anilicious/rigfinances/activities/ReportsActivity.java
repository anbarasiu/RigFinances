package com.anilicious.rigfinances.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.fragments.CreditFragment;
import com.anilicious.rigfinances.fragments.DebitFragment;
import com.anilicious.rigfinances.fragments.ReportBoreFragment;
import com.anilicious.rigfinances.fragments.ReportDieselFragment;
import com.anilicious.rigfinances.fragments.ReportExpensesFragment;
import com.anilicious.rigfinances.mappers.ChartMapper;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

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

        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText(R.string.reports_tab2).setTabListener(this);
        actionBar.addTab(tab2);

        ActionBar.Tab tab3 = actionBar.newTab();
        tab3.setText(R.string.reports_tab3).setTabListener(this);
        actionBar.addTab(tab3);

        ActionBar.Tab tab4 = actionBar.newTab();
        tab4.setText(R.string.reports_tab4).setTabListener(this);
        actionBar.addTab(tab4);

        ActionBar.Tab tab5 = actionBar.newTab();
        tab5.setText(R.string.reports_tab5).setTabListener(this);
        actionBar.addTab(tab5);

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
                ReportDieselFragment reportDieselFragment = new ReportDieselFragment();
                ft.replace(fragmentContainer, reportDieselFragment);
                ft.commit();
                break;
            case 3:
                ReportBoreFragment reportBoreFragment = new ReportBoreFragment();
                ft.replace(fragmentContainer, reportBoreFragment);
                ft.commit();
                break;
            /*case 4:
                ReportDieselFragment reportDieselFragment = new ReportDieselFragment();
                ft.replace(fragmentContainer, reportDieselFragment);
                ft.commit();
                break;
            case 5:
                ReportDieselFragment reportDieselFragment = new ReportDieselFragment();
                ft.replace(fragmentContainer, reportDieselFragment);
                ft.commit();
                break;*/
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
        // For now, we've only Settings
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);

        return true;
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
