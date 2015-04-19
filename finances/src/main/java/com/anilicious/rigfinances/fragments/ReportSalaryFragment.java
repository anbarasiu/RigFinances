package com.anilicious.rigfinances.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.mappers.ChartMapper;
import com.anilicious.rigfinances.utils.CommonUtils;
import com.anilicious.rigfinances.utils.PickerFragment;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ANBARASI on 25/1/15.
 */
public class ReportSalaryFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, null);

        // Attach listeners
        EditText etEmpNumber =(EditText)view.findViewById(R.id.editText);
        //TODO Financial Year
        TextView tvEmpName =(TextView)view.findViewById(R.id.TextView_empName);
        TextView tvJoiningDate =(TextView)view.findViewById(R.id.TextView_joiningDate1);
        TextView tvLeavingDate1 =(TextView)view.findViewById(R.id.TextView_leavingDate1);
        TextView tvJoiningDate2 =(TextView)view.findViewById(R.id.TextView_joiningDate2);
        TextView tvLeavingDate2 =(TextView)view.findViewById(R.id.TextView_leavingDate2);
        TextView tvJoiningDate3 =(TextView)view.findViewById(R.id.TextView_joiningDate3);
        TextView tvLeavingDate3 =(TextView)view.findViewById(R.id.TextView_leavingDate3);
        TextView tvTotalWorked =(TextView)view.findViewById(R.id.TextView_totalWorked);
        TextView tvTotalAmount =(TextView)view.findViewById(R.id.TextView_totalAmount);
        TextView tvSalaryGiven =(TextView)view.findViewById(R.id.TextView_salaryGiven);
        TextView tvSalaryWithCompany =(TextView)view.findViewById(R.id.TextView_salaryWithCompany);
        TextView tvTotalAmountSpent =(TextView)view.findViewById(R.id.TextView_totalAmountSpent);
        Button btnGenerate = (Button)view.findViewById(R.id.button);
        btnGenerate.setOnClickListener(this);
        
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button){
            renderReport();
        }
    }

    public void renderReport(){

    }
}
