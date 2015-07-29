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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports_bore, null);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        tvDate = (TextView)view.findViewById(R.id.TextView2);
        tvTotalDepth = (TextView)view.findViewById(R.id.TextView4);
        tvCastingDepth = (TextView)view.findViewById(R.id.TextView6);
        TextView tvEngineHrsStart = (TextView)view.findViewById(R.id.TextView8);
        TextView tvEngineHrsEnd = (TextView)view.findViewById(R.id.TextView10);
        TextView tvBoreType = (TextView)view.findViewById(R.id.TextView18);
        tvBillAmount = (TextView)view.findViewById(R.id.TextView20);
        tvCommission = (TextView)view.findViewById(R.id.TextView22);
        tvTotalAmount = (TextView)view.findViewById(R.id.TextView24);
        tvDieselUsed = (TextView)view.findViewById(R.id.TextView26);
        tvDieselInHand = (TextView)view.findViewById(R.id.TextView28);
        tvCasingPipeInHand = (TextView)view.findViewById(R.id.TextView30);
        retrieveDetailsFromDB();

        // TODO: Back to Home listener

        return view;
    }

    public void retrieveDetailsFromDB(){
        HashMap<String, Double> reportsMap = new HashMap<String, Double>();
        ReportsMapper reportsMapper = new ReportsMapper(getActivity());

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        String dateFroms = CommonUtils.FINANCIAL_YEAR_START + "/" + (year-1);
        String dateTos = CommonUtils.FINANCIAL_YEAR_END + "/" + year;
        String[] test=dateFroms.split("/");
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
        Integer dateTo= Integer.parseInt(date1);
        reportsMap = reportsMapper.mapBoreDetails(dateFrom, dateTo);

        tvDate.setText(dateFroms + " - " + dateTos);
        tvTotalDepth.setText(reportsMap.get("total_depth").toString());
        tvCastingDepth.setText(reportsMap.get("casting_depth").toString());
        tvCommission.setText(reportsMap.get("bill_amount").toString());
        tvTotalDepth.setText(reportsMap.get("commission").toString());
        tvTotalAmount.setText(reportsMap.get("total_amount").toString());
        tvDieselUsed.setText(reportsMap.get("diesel_used").toString());
        tvDieselInHand.setText(reportsMap.get("diesel_in_hand").toString());
        tvCasingPipeInHand.setText(reportsMap.get("casing_pipe_in_hand").toString());
    }
}
