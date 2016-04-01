package com.anilicious.rigfinances.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.mappers.ReportsMapper;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by ANBARASI on 3/2/15.
 * Bore Details extracted from DB - Reports
 */
public class ReportBoreFragment extends Fragment {

    TextView tvTotalDepth;
    TextView tvCastingDepth;
    TextView tvBillAmount;
    TextView tvCommission;
    TextView tvTotalAmount;
    TextView tvDieselUsed;
    TextView tvDate;
    TextView tvDieselInHand;
    TextView tvCasingPipeInHand;
    TextView tvEngineHrsStart;
    TextView tvEngineHrsEnd;
    EditText etDateFrom;
    EditText etDateTo;

    private int currentYear;
    private int currentMonth;
    private int currentDate;

    static final int DATE_DIALOG_ID = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports_bore, null);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        etDateFrom = (EditText)view.findViewById(R.id.date_from);
        etDateTo = (EditText)view.findViewById(R.id.date_to);
        tvTotalDepth = (TextView)view.findViewById(R.id.TextView4);
        tvCastingDepth = (TextView)view.findViewById(R.id.TextView6);
        tvEngineHrsStart = (TextView)view.findViewById(R.id.TextView8);
        tvEngineHrsEnd = (TextView)view.findViewById(R.id.TextView10);
        TextView tvBoreType = (TextView)view.findViewById(R.id.TextView18);
        tvBillAmount = (TextView)view.findViewById(R.id.TextView20);
        tvCommission = (TextView)view.findViewById(R.id.TextView22);
        tvTotalAmount = (TextView)view.findViewById(R.id.TextView24);
        tvDieselUsed = (TextView)view.findViewById(R.id.TextView26);
        tvDieselInHand = (TextView)view.findViewById(R.id.TextView28);
        tvCasingPipeInHand = (TextView)view.findViewById(R.id.TextView30);
        final Button btnGenerate = (Button)view.findViewById(R.id.btn_generateBoreReport);

        // Setup Date Picker
        setupPickers();

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveDetailsFromDB();
            }
        });

        return view;
    }

    public void retrieveDetailsFromDB(){
        HashMap<String, Double> reportsMap = new HashMap<String, Double>();
        ReportsMapper reportsMapper = new ReportsMapper(getActivity());

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        //String dateFroms = CommonUtils.FINANCIAL_YEAR_START + "/" + (year-1);
        //String dateTos = CommonUtils.FINANCIAL_YEAR_END + "/" + year;

        int dateFrom = Integer.parseInt(CommonUtils.formatDateEntry(etDateFrom.getText().toString()));
        int dateTo = Integer.parseInt(CommonUtils.formatDateEntry(etDateTo.getText().toString()));

        /*String[] test=dateFroms.split("/");
        if(test[1].length()<=1)
        {
            test[1] = "0"+test[1];
        }
        if(test[0].length()<=1)
        {
            test[0] = "0"+test[0];
        }
        String date1 =(test[2]+test[1]+test[0]);
        Integer dateFrom= Integer.parseInt(date1);
        test=dateTos.split("/");
        if(test[1].length()<=1)
        {
            test[1] = "0"+test[1];
        }
        if(test[0].length()<=1)
        {
            test[0] = "0"+test[0];
        }
        date1 =(test[2]+test[1]+test[0]);
        Integer dateTo= Integer.parseInt(date1);*/
        reportsMap = reportsMapper.mapBoreDetails(dateFrom, dateTo);

        //tvDate.setText(dateFroms + " - " + dateTos);
        tvTotalDepth.setText(reportsMap.get("total_depth").toString());
        tvCastingDepth.setText(reportsMap.get("casting_depth").toString());
        tvCommission.setText(reportsMap.get("commission").toString());
        tvBillAmount.setText(reportsMap.get("bill_amount").toString());
        tvDieselUsed.setText(reportsMap.get("diesel_used").toString());
        tvDieselInHand.setText(reportsMap.get("diesel_in_hand").toString());
        tvCasingPipeInHand.setText(reportsMap.get("casing_pipe_in_hand").toString());
        tvEngineHrsStart.setText(reportsMap.get("engine_hrs_start").toString());
        tvEngineHrsEnd.setText(reportsMap.get("engine_hrs_end").toString());
    }

    private void setupPickers(){
        /** Get the current date */
        final Calendar cal = Calendar.getInstance();
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH);
        currentDate = cal.get(Calendar.DAY_OF_MONTH);

        etDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), mDateFromSetListener, currentYear, currentMonth, currentDate).show();
            }
        });

        etDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), mDateToSetListener, currentYear, currentMonth, currentDate).show();
            }
        });
    }

    /* Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener mDateFromSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    etDateFrom.setText(new StringBuilder().append(day).append("/")
                            .append(month + 1).append("/").append(year).toString());
                }
            };

    private DatePickerDialog.OnDateSetListener mDateToSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    etDateTo.setText(new StringBuilder().append(day).append("/")
                            .append(month + 1).append("/").append(year).toString());
                }
            };
}
