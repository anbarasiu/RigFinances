package com.anilicious.rigfinances.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.anilicious.rigfinances.finances.R;

/*
 *  Activity of the Initial Menu Screen
 */

public class MainActivity extends ActionBarActivity{

    static final String[] views = new String[]{"Vouchers", "Bore Entry", "Reports", "Employee Details"};
    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loadListeners();

        //Load the Application Preferences
        sharedPrefs = this.getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);

        TextView tvWelcome = (TextView)findViewById(R.id.main_username);
        tvWelcome.setText(sharedPrefs.getString(getString(R.string.sp_username), "User"));

        GridView gridView = (GridView)findViewById(R.id.gridView);

        gridView.setAdapter(new GridImageAdapter(this, views));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position){
                    case 0: // Vouchers
                        Intent intent1 = new Intent(getApplicationContext(), VouchersActivity.class);
                        startActivity(intent1);
                        break;
                    case 1: // Bore Entry
                        Intent intent2 = new Intent(getApplicationContext(), BoreEntryActivity.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(getApplicationContext(), ReportsActivity.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(getApplicationContext(), EmployeeDetailsActivity.class);
                        startActivity(intent4);
                        break;
                }
            }
        });

    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            /* Inflate the menu items for use in the action bar
            if(sharedPrefs.getString(getString(R.string.user_role), "USER").equals("ADMIN")){
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_actions, menu);
            }*/
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // For now, we've only Settings
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);

            return true;
        }

    /*
     * Attach the page's button listeners
     */
    /*private void loadListeners(){
        Button vouchers_btn = (Button)findViewById(R.id.button);
        vouchers_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VouchersActivity.class);
                startActivity(intent);
            }
        });

        Button boreEntry_btn = (Button)findViewById(R.id.btn_boreentry);
        boreEntry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoreEntryActivity.class);
                startActivity(intent);
            }
        });
    }*/

}
