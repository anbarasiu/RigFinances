package com.anilicious.rigfinances.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
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
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;
import com.anilicious.rigfinances.utils.PickerFragment;

import java.util.Calendar;

public class BoreEntryActivity extends ActionBarActivity implements LocationListener {

    private EditText etEngineHrsStart;
    private EditText etEngineHrsEnd;
    private EditText etDate;
    private EditText etPlace;

    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    private int pHour;
    private int pMinute;
    private int currentYear;
    private int currentMonth;
    private int currentDate;
    private EditText currentTimeField;

    private LocationManager locationManager;
    private String provider;
    private String currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bore_entry);

        // UI Object References
        Button btnSubmit = (Button)findViewById(R.id.btn_submit);
        etDate = (EditText)findViewById(R.id.editText);
        final EditText etTotalDepth = (EditText)findViewById(R.id.editText4);
        final EditText etCastingDepth = (EditText)findViewById(R.id.editText3);
        etEngineHrsStart = (EditText)findViewById(R.id.editText5);
        etEngineHrsEnd = (EditText)findViewById(R.id.editText2);
        final EditText etCustomerName = (EditText)findViewById(R.id.editText6);
        etPlace = (EditText)findViewById(R.id.editText7);
        final EditText etAgentName = (EditText)findViewById(R.id.editText8);
        final Spinner etBoreType = (Spinner)findViewById(R.id.spinner_boreType);
        final EditText etBillAmount = (EditText)findViewById(R.id.editText10);
        final EditText etCommission = (EditText)findViewById(R.id.editText11);
        final EditText etTotalAmount = (EditText)findViewById(R.id.editText12);

        // Setup Date Picker
        setupPickers();
        // Setup Bore Type Spinner
        setupSpinner();
        // Setup Location Listener
        setupLocationListener();

        // On Form Submission
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(validForm()){
                    String date = etDate.getText().toString();
                    float totalDepth = Float.parseFloat(etTotalDepth.getText().toString());
                    float castingDepth = Float.parseFloat(etCastingDepth.getText().toString());
                    String engineHrsStart = etEngineHrsStart.getText().toString();
                    String engineHrsEnd = etEngineHrsEnd.getText().toString();
                    String customerName = etCustomerName.getText().toString();
                    String place = etPlace.getText().toString();
                    String agentName = etAgentName.getText().toString();
                    String boreType = (String)etBoreType.getSelectedItem();
                    int billAmount = Integer.parseInt(etBillAmount.getText().toString());
                    int commission = Integer.parseInt(etCommission.getText().toString());
                    int totalAmount = Integer.parseInt(etTotalAmount.getText().toString());

                    Bore bore = new Bore();
                    bore.setDate(date);
                    bore.setTotalDepth(totalDepth);
                    bore.setCastingDepth(castingDepth);
                    bore.setEngineHrsStart(engineHrsStart);
                    bore.setEngineHrsEnd(engineHrsEnd);
                    bore.setCustomerName(customerName);
                    bore.setPlace(place);
                    bore.setAgentName(agentName);
                    bore.setBoreType(boreType);
                    bore.setBillAmount(billAmount);
                    bore.setCommission(commission);
                    bore.setTotalAmount(totalAmount);

                    // Insert to DB
                    DBAdapter dbAdapter = DBAdapter.getInstance(getApplicationContext());
                    dbAdapter.insertBore(bore);

                    // Clear the Form
                    clearForm();
                }
            }
        });

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
     * Setup the Bore Type Spinner
     */
    private void setupSpinner(){
        Spinner spinner_boreType = (Spinner) findViewById(R.id.spinner_boreType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.boreType_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_boreType.setAdapter(adapter);
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

        etEngineHrsStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTimeField = etEngineHrsStart;
                pHour = cal.get(Calendar.HOUR_OF_DAY);
                pMinute = cal.get(Calendar.MINUTE);
                showDialog(TIME_DIALOG_ID);
            }
        });

        etEngineHrsEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTimeField = etEngineHrsEnd;
                pHour = cal.get(Calendar.HOUR_OF_DAY);
                pMinute = cal.get(Calendar.MINUTE);
                showDialog(TIME_DIALOG_ID);
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    // Setup the Location - Based Services
    private void setupLocationListener(){
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        boolean enabled = locationManager.isProviderEnabled(provider);
        if(!enabled){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            onLocationChanged(location);
        } else {
            etPlace.setText("Location not available");
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, mTimeSetListener, pHour, pMinute, false);
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, currentYear, currentMonth, currentDate);
        }
        return null;
    }

    /** Callback received when the user "picks" a time in the dialog */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMinute = minute;

                    currentTimeField.setText(new StringBuilder()
                            .append(String.format("%02d", pHour)).append(":")
                            .append(String.format("%02d", pMinute)));
                }
            };

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
        ViewGroup group = (ViewGroup)findViewById(R.id.bore_parent);
        CommonUtils.clearForm(group);
    }

    public boolean validForm(){
        ViewGroup group = (ViewGroup)findViewById(R.id.bore_parent);
        return CommonUtils.validForm(group);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = (int) (location.getLatitude()) + "LAT" + (int) (location.getLongitude()) + "LNG";
        etPlace.setText(currentLocation);
    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onProviderEnabled(String s) {
        //setupLocationListener();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}