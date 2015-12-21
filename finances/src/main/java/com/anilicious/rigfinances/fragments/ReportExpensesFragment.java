package com.anilicious.rigfinances.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.anilicious.rigfinances.finances.R;
import com.anilicious.rigfinances.mappers.ReportsMapper;
import com.anilicious.rigfinances.utils.CommonUtils;
import com.anilicious.rigfinances.utils.PickerFragment;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ANBARASI on 25/1/15.
 */
public class ReportExpensesFragment extends Fragment implements View.OnClickListener{

    TextView tvFrom;
    TextView tvTo;
    View view;
    CheckBox diesel;
    CheckBox maintenance;
    CheckBox cook;
    CheckBox road_expenses;
    CheckBox tools;
    CheckBox salary;
    CheckBox pipes;
    CheckBox site_expenses;
    TableLayout tableLayout;

    ReportsMapper reportsMapper;

    private int entryDateFrom;
    private int entryDateTo;

    private static final int DIALOG_DATE_FROM = 1;
    private static final int DIALOG_DATE_TO = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_expenses, null);

        // Attach listeners
        tvFrom =(TextView)view.findViewById(R.id.report_expense_from);
        tvTo =(TextView)view.findViewById(R.id.report_expense_to);
        diesel =(CheckBox)view.findViewById(R.id.report_expense_checkBox);
        maintenance =(CheckBox)view.findViewById(R.id.report_expense_checkBox2);
        cook =(CheckBox)view.findViewById(R.id.report_expense_checkBox3);
        road_expenses =(CheckBox)view.findViewById(R.id.report_expense_checkBox4);
        tools =(CheckBox)view.findViewById(R.id.report_expense_checkBox5);
        salary =(CheckBox)view.findViewById(R.id.report_expense_checkBox6);
        pipes =(CheckBox)view.findViewById(R.id.report_expense_checkBox7);
        site_expenses =(CheckBox)view.findViewById(R.id.report_expense_checkBox8);
        Button btnViewReports = (Button)view.findViewById(R.id.reports_view);

        tableLayout = (TableLayout)view.findViewById(R.id.table_details);

        tvFrom.setOnClickListener(this);
        tvTo.setOnClickListener(this);
        btnViewReports.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.reports_view && entryDateTo > 0 && entryDateFrom >0){
            List<String> selectedExpenses = new ArrayList<String>();

            // TODO : Code smell, I know.. <Revisit>
            if(diesel.isChecked()){
                selectedExpenses.add(CommonUtils.CONSTANTS.DIESEL);
            }
            if(maintenance.isChecked()){
                selectedExpenses.add(CommonUtils.CONSTANTS.MAINTENANCE);
            }
            if(cook.isChecked()){
                selectedExpenses.add(CommonUtils.CONSTANTS.COOK);
            }
            if(road_expenses.isChecked()){
                selectedExpenses.add(CommonUtils.CONSTANTS.ROAD);
            }
            if(tools.isChecked()){
                selectedExpenses.add(CommonUtils.CONSTANTS.TOOLS);
            }
            if(salary.isChecked()){
                selectedExpenses.add(CommonUtils.CONSTANTS.SALARY);
            }
            if(pipes.isChecked()){
                selectedExpenses.add(CommonUtils.CONSTANTS.PIPE);
            }
            if(site_expenses.isChecked()){
                selectedExpenses.add("SITE_EXPENSES");
            }
            renderCharts(entryDateFrom, entryDateTo, selectedExpenses);
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
                String date = data.getStringExtra("entryDate");
                String[] test=date.split("/");
                if(test[1].length()<=1)
                {
                    test[1] = "0"+test[1];
                }
                if(test[0].length()<=1)
                {
                    test[0] = "0"+test[0];
                }
                String date1 =(test[2]+test[1]+test[0]);
                Integer date_num=Integer.parseInt(date1);
                entryDateFrom = date_num;
                tvFrom.setText(data.getStringExtra("entryDate"));
            }
            else if(requestCode == DIALOG_DATE_TO){
                String date = data.getStringExtra("entryDate");
                String[] test=date.split("/");
                if(test[1].length()<=1)
                {
                    test[1] = "0"+test[1];
                }
                if(test[0].length()<=1)
                {
                    test[0] = "0"+test[0];
                }
                String date1 =(test[2]+test[1]+test[0]);
                Integer date_num=Integer.parseInt(date1);
                entryDateTo = date_num;
                tvTo.setText(data.getStringExtra("entryDate"));
            }
        }
    }

    public void renderCharts(final int dateFrom, final int dateTo, final List selectedExpenses){
        tableLayout.removeAllViews();

        // Pie Chart init
        int[] colours = {Color.parseColor("#4D4D4D"), Color.parseColor("#5DA5DA"), Color.parseColor("#FAA43A"), Color.parseColor("#60BD68"), Color.parseColor("#B2912F"), Color.parseColor("#B276B2"), Color.parseColor("#DECF3F"), Color.parseColor("#F15854")};
        int i = 0;
        CategorySeries mSeries = new CategorySeries("Pie Chart");
        DefaultRenderer mRenderer = new DefaultRenderer();
        mRenderer.setStartAngle(0);
        mRenderer.setDisplayValues(true);
        mRenderer.setLabelsTextSize(36);
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setLegendTextSize(36);
        mRenderer.setClickEnabled(true);

        //Bar Chart init
        double bar_index = 0; // Indices interval
        XYMultipleSeriesRenderer xyRenderer = new XYMultipleSeriesRenderer();
        XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        reportsMapper = new ReportsMapper(getActivity());
        HashMap<String, Double> expenseMap = reportsMapper.mapExpenses(dateFrom, dateTo, selectedExpenses);

        //TODO: Add legends & labels to Bar Chart
        for(Map.Entry entry : expenseMap.entrySet()){
            XYSeries xySeries = new XYSeries(entry.getKey().toString());
            mSeries.add(entry.getKey().toString(), Double.parseDouble(entry.getValue().toString()));
            xySeries.add(bar_index, Double.parseDouble(entry.getValue().toString()));
            bar_index = bar_index + 20;

            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(colours[i++]);

            mRenderer.addSeriesRenderer(renderer);
            xyRenderer.addSeriesRenderer(renderer);
            xyRenderer.setBarSpacing(0.1);
            xySeriesRenderer.setLineWidth(75);

            dataset.addSeries(xySeries);
        }

        // Pie Chart Rendering
        LinearLayout layout = (LinearLayout)view.findViewById(R.id.pie_chart);
        layout.removeAllViews();
        final GraphicalView chart = ChartFactory.getPieChartView(getActivity().getApplicationContext(), mSeries, mRenderer);
        layout.addView(chart);

        // On click of wedges, display details
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeriesSelection seriesSelection = chart.getCurrentSeriesAndPoint();
                if(seriesSelection != null){
                    // Get name of the clicked slice
                    int seriesIndex = seriesSelection.getPointIndex();
                    String selectedSeries = "";
                    selectedSeries = selectedExpenses.get(seriesIndex).toString();

                    // Get value of clicked slice
                    double value = seriesSelection.getXValue();
                    java.text.DecimalFormat dFormat = new java.text.DecimalFormat("#.#");

                    // Display details table
                    displayDetails(dateFrom, dateTo, selectedSeries);
                }
            }
        });

        // Bar Chart Rendering
        LinearLayout bar_layout = (LinearLayout)view.findViewById(R.id.bar_chart);
        bar_layout.removeAllViews();
        GraphicalView bar_chart = ChartFactory.getBarChartView(getActivity().getApplicationContext(), dataset, xyRenderer, BarChart.Type.DEFAULT);
        bar_layout.addView(bar_chart);
    }

    private void displayDetails(int dateFrom, int dateTo, String selectedSeries){
        tableLayout.removeAllViews();
        // TODO: For the selectedSeries, get complete info
        Cursor cResults = reportsMapper.mapExpenseDetails(dateFrom, dateTo, selectedSeries);

        if(cResults != null){
            cResults.moveToFirst();
        }

        int rows = cResults.getCount();
        int columns = cResults.getColumnCount();

        // Table Headers
        String[] columnNames = cResults.getColumnNames();
        TableRow tableRowHeader = new TableRow(this.getActivity().getApplicationContext());
        tableRowHeader.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        tableRowHeader.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        for(int j=0; j<columnNames.length; j++){
            TextView tableColumn = new TextView(this.getActivity().getApplicationContext());
            tableColumn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            tableColumn.setText(columnNames[j].replace('_', ' ').toUpperCase());
            tableColumn.setTextColor(Color.BLACK);
            tableRowHeader.addView(tableColumn);
        }
        tableLayout.addView(tableRowHeader);

        // Rendering the Table with values
        for(int i=0; i<rows; i++){
            TableRow tableRow = new TableRow(this.getActivity().getApplicationContext());
            tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            tableRow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            for(int j=0; j<columns; j++){
                TextView tableColumn = new TextView(this.getActivity().getApplicationContext());
                tableColumn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                tableColumn.setText(cResults.getString(j));
                tableColumn.setTextColor(Color.BLACK);
                tableRow.addView(tableColumn);
            }
            cResults.moveToNext();
            tableLayout.addView(tableRow);
        }

        reportsMapper.closeDBConn();
    }
}