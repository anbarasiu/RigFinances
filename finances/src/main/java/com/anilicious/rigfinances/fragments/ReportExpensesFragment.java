package com.anilicious.rigfinances.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
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
public class ReportExpensesFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

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
        Switch select_all = (Switch)view.findViewById(R.id.reports_expenses_selectall);
        Button btnViewReports = (Button)view.findViewById(R.id.reports_view);

        tableLayout = (TableLayout)view.findViewById(R.id.table_details);

        tvFrom.setOnClickListener(this);
        tvTo.setOnClickListener(this);
        btnViewReports.setOnClickListener(this);
        select_all.setOnCheckedChangeListener(this);

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
                Integer date_num = Integer.parseInt(CommonUtils.formatDateEntry(date));
                entryDateFrom = date_num;
                tvFrom.setText(data.getStringExtra("entryDate"));
            }
            else if(requestCode == DIALOG_DATE_TO){
                String date = data.getStringExtra("entryDate");
                Integer date_num=Integer.parseInt(CommonUtils.formatDateEntry(date));
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

        final List<String> mappedExpenses = new ArrayList<String>();
        //TODO: Add legends & labels to Bar Chart
        for(Map.Entry entry : expenseMap.entrySet()){
            mSeries.add(entry.getKey().toString(), Double.parseDouble(entry.getValue().toString()));
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(colours[i++]);
            mRenderer.addSeriesRenderer(renderer);

            if(selectedExpenses.contains(entry.getKey().toString())){
                mappedExpenses.add(entry.getKey().toString());
            }
            /*
            XYSeries xySeries = new XYSeries(entry.getKey().toString());
            xySeries.add(bar_index, Double.parseDouble(entry.getValue().toString()));
            bar_index = bar_index + 20;

            xyRenderer.addSeriesRenderer(renderer);
            xyRenderer.setBarSpacing(0.1);
            xySeriesRenderer.setLineWidth(75);

            dataset.addSeries(xySeries);*/
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
                    selectedSeries = mappedExpenses.get(seriesIndex).toString();

                    // Get value of clicked slice
                    double value = seriesSelection.getXValue();
                    java.text.DecimalFormat dFormat = new java.text.DecimalFormat("#.#");

                    // Display details table
                    displayDetails(dateFrom, dateTo, selectedSeries);
                }
            }
        });

        // Bar Chart Rendering
        /*LinearLayout bar_layout = (LinearLayout)view.findViewById(R.id.bar_chart);
        bar_layout.removeAllViews();
        GraphicalView bar_chart = ChartFactory.getBarChartView(getActivity().getApplicationContext(), dataset, xyRenderer, BarChart.Type.DEFAULT);
        bar_layout.addView(bar_chart);*/
    }

    private void displayDetails(int dateFrom, int dateTo, String selectedSeries){
        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(2, Color.BLACK);

        tableLayout.removeAllViews();
        // TODO: For the selectedSeries, get complete info
        Cursor cResults = reportsMapper.mapExpenseDetails(dateFrom, dateTo, selectedSeries);

        if(cResults != null){
            cResults.moveToFirst();
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
                tableColumn.setPadding(2, 0, 2, 0);
                tableColumn.setBackground(gd);
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
                    tableColumn.setPadding(2, 0, 2, 0);
                    tableColumn.setBackground(gd);
                    tableRow.addView(tableColumn);
                }
                cResults.moveToNext();

                tableRow.setBackground(gd);
                tableLayout.addView(tableRow);
            }
            tableLayout.setBackground(gd);
        }
        reportsMapper.closeDBConn();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b == true){
            diesel.setChecked(true);
            maintenance.setChecked(true);
            cook.setChecked(true);
            salary.setChecked(true);
            tools.setChecked(true);
            pipes.setChecked(true);
            site_expenses.setChecked(true);
            road_expenses.setChecked(true);
        }
        else if(b == false){
            diesel.setChecked(false);
            maintenance.setChecked(false);
            cook.setChecked(false);
            salary.setChecked(false);
            tools.setChecked(false);
            pipes.setChecked(false);
            site_expenses.setChecked(false);
            road_expenses.setChecked(false);
        }
    }
}