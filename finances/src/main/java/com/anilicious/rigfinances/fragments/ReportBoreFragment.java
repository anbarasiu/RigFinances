package com.anilicious.rigfinances.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;

/**
 * Created by ANBARASI on 3/2/15.
 * Bore Details extracted from DB - Reports
 */
public class ReportBoreFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports_bore, null);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        final TextView tvDate = (TextView)view.findViewById(R.id.TextView2);
        final TextView tvTotalDepth = (TextView)view.findViewById(R.id.TextView4);
        final TextView tvCastingDepth = (TextView)view.findViewById(R.id.TextView6);
        final TextView tvEngineHrsStart = (TextView)view.findViewById(R.id.TextView8);
        final TextView tvEngineHrsEnd = (TextView)view.findViewById(R.id.TextView10);
        final TextView tvCustomerName = (TextView)view.findViewById(R.id.TextView12);
        final TextView tvPlace = (TextView)view.findViewById(R.id.TextView14);
        final TextView tvAgentName = (TextView)view.findViewById(R.id.TextView16);
        final TextView tvBoreType = (TextView)view.findViewById(R.id.TextView18);
        final TextView tvBillAmount = (TextView)view.findViewById(R.id.TextView20);
        final TextView tvCommission = (TextView)view.findViewById(R.id.TextView22);
        final TextView tvTotalAmount = (TextView)view.findViewById(R.id.TextView24);

        retrieveDetailsFromDB();

        return view;
    }

    public void retrieveDetailsFromDB(){
        // Insert to DB
        //DBAdapter dbAdapter = DBAdapter.getInstance(getApplicationContext());
    }
}
