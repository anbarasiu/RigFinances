package com.anilicious.rigfinances.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.anilicious.rigfinances.activities.MainActivity;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.utils.CommonUtils;
import com.anilicious.rigfinances.utils.PickerFragment;

import java.util.Calendar;

/**
 * Created by ANBARASI on 7/12/14.
 */
public class DebitFragment extends Fragment{

    private DatePicker date_dp;
    private TextView date_tv;
    private Calendar calendar_cal;
    private int date, month, year;
    private String entryDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vouchers_debit, null);

        // Common input fields for all sub-views
        date_tv = (TextView)view.findViewById(R.id.date_tv);
        calendar_cal = Calendar.getInstance();
        year = calendar_cal.get(Calendar.YEAR);
        month = calendar_cal.get(Calendar.MONTH);
        date = calendar_cal.get(Calendar.DAY_OF_MONTH);

        // Setup the Spinner containing the Voucher Types
        setupSpinner(view);

        // Setup Date Picker
        setupDatePicker(view);

        // Return to Home Button
        Button home_btn = (Button)view.findViewById(R.id.btn_home);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setupSpinner(View view){
        Spinner spinner_transaction = (Spinner) view.findViewById(R.id.spinner_transaction);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.vouchers_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_transaction.setAdapter(adapter);

        /*
         *  Render fragment based on the Voucher Type selected
         */
        spinner_transaction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
                int fragmentContainer = R.id.vouchers_fragments_container;
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                switch(pos){
                    case 0 : //"Diesel":
                        DieselFragment dieselFragment = new DieselFragment();
                        ft.replace(fragmentContainer, dieselFragment);
                        ft.commit();
                        break;
                    case 1 : // "Maintenance":
                        MaintenanceFragment maintenanceFragment = new MaintenanceFragment();
                        ft.replace(fragmentContainer, maintenanceFragment);
                        ft.commit();
                        break;
                    case 2 : // "Cook":
                        CookFragment cookFragment = new CookFragment();
                        ft.replace(fragmentContainer, cookFragment);
                        ft.commit();
                        break;
                    case 3 : // "Road Expenses":
                        RoadFragment roadFragment = new RoadFragment();
                        ft.replace(fragmentContainer, roadFragment);
                        ft.commit();
                        break;
                    case 4 : // "Tools":
                        ToolFragment toolFragment = new ToolFragment();
                        ft.replace(fragmentContainer, toolFragment);
                        ft.commit();
                        break;
                    case 5 : // "Salary":
                        SalaryFragment salaryFragment = new SalaryFragment();
                        ft.replace(fragmentContainer, salaryFragment);
                        ft.commit();
                        break;
                    case 6 : // "Pipes":
                        PipeFragment pipeFragment = new PipeFragment();
                        ft.replace(fragmentContainer, pipeFragment);
                        ft.commit();
                        break;
                    case 7 : // "Site Expenses":
                        SiteFragment siteFragment = new SiteFragment();
                        ft.replace(fragmentContainer, siteFragment);
                        ft.commit();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    // Begin - DatePicker Methods

    private void setupDatePicker(View view){
        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment picker = new PickerFragment(CommonUtils.DIALOG_DATE);
                picker.setTargetFragment(DebitFragment.this, 1); // todo: Declare Constant
                picker.show(getFragmentManager(), "datePicker");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 101){
            entryDate = data.getStringExtra("entryDate");
            date_tv.setText(entryDate);
        }
    }

    // Exposed to access Date in child Fragments
    public String getEntryDate() {
        return entryDate;
    }

    // End - DatePicker Methods

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