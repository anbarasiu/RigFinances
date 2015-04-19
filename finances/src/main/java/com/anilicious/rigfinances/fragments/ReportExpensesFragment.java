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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.mappers.ReportsMapper;
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
public class ReportExpensesFragment extends Fragment implements View.OnClickListener{

    TextView tvFrom;
    TextView tvTo;
    View view;

    private String entryDateFrom;
    private String entryDateTo;

    private static final int DIALOG_DATE_FROM = 1;
    private static final int DIALOG_DATE_TO = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_expenses, null);

        // Attach listeners
        tvFrom =(TextView)view.findViewById(R.id.report_expense_from);
        tvTo =(TextView)view.findViewById(R.id.report_expense_to);
        Button btnViewReports = (Button)view.findViewById(R.id.reports_view);
        tvFrom.setOnClickListener(this);
        tvTo.setOnClickListener(this);
        btnViewReports.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.reports_view && entryDateTo != null && entryDateFrom != null){
            renderCharts(entryDateFrom, entryDateTo);
        }
        else{
            DialogFragment picker = new PickerFragment(CommonUtils.DIALOG_DATE);
            if(view.getId() == R.id.report_expense_from)
                picker.setTargetFragment(ReportExpensesFragment.this, DIALOG_DATE_FROM);
            else if(view.getId() == R.id.report_expense_to)
                picker.setTargetFragment(ReportExpensesFragment.this, DIALOG_DATE_TO);
            picker.show(getFragmentManager(), "datePicker");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 101){
            if(requestCode == DIALOG_DATE_FROM){
                entryDateFrom = data.getStringExtra("entryDate");
                tvFrom.setText(entryDateFrom);
            }
            else if(requestCode == DIALOG_DATE_TO){
                entryDateTo = data.getStringExtra("entryDate");
                tvTo.setText(entryDateTo);
            }
        }
    }

    public void renderCharts(String dateFrom, String dateTo){
        // Pie Chart init
        int[] colours = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK, Color.GRAY, Color.CYAN, Color.MAGENTA};
        int i = 0;
        CategorySeries mSeries = new CategorySeries("Pie Chart");
        DefaultRenderer mRenderer = new DefaultRenderer();
        mRenderer.setStartAngle(0);
        mRenderer.setDisplayValues(true);

        //Bar Chart init
        XYSeries xySeries = new XYSeries("Bar Chart");
        double bar_index = 100; // Indices interval
        XYMultipleSeriesRenderer xyRenderer = new XYMultipleSeriesRenderer();
        XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        ReportsMapper reportsMapper = new ReportsMapper(getActivity());
        HashMap<String, Double> expenseMap = reportsMapper.mapExpenses(dateFrom, dateTo);

        //TODO: Add legends & labels to Bar Chart
        for(Map.Entry entry : expenseMap.entrySet()){
            mSeries.add(entry.getKey().toString(), Double.parseDouble(entry.getValue().toString()));
            xySeries.add(bar_index, Double.parseDouble(entry.getValue().toString()));
            bar_index = bar_index + 100;

            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(colours[i++]);
            mRenderer.addSeriesRenderer(renderer);
        }

        // Pie Chart Rendering
        LinearLayout layout = (LinearLayout)view.findViewById(R.id.pie_chart);
        GraphicalView chart = ChartFactory.getPieChartView(getActivity().getApplicationContext(), mSeries, mRenderer);
        layout.addView(chart);

        // Bar Chart Rendering
        xyRenderer.addSeriesRenderer(xySeriesRenderer);
        dataset.addSeries(xySeries);
        LinearLayout bar_layout = (LinearLayout)view.findViewById(R.id.bar_chart);
        GraphicalView bar_chart = ChartFactory.getBarChartView(getActivity().getApplicationContext(), dataset, xyRenderer, null);
        bar_layout.addView(bar_chart);
    }
}
