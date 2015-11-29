package com.anilicious.rigfinances.mappers;

import android.content.Context;
import android.util.Log;

import com.anilicious.rigfinances.activities.ReportsActivity;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ANBARASI on 24/1/15.
 * Mapper Class to fetch details from the DB and return to Fragment to render Charts
 */
public class ReportsMapper {

    Context context;
    DBAdapter dbAdapter;

    public ReportsMapper(Context context){
        this.context = context.getApplicationContext();
        dbAdapter = DBAdapter.getInstance(context);
    }

    public HashMap<String, Double> mapExpenses(int dateFrom, int dateTo, List<String> selectedExpenses){
        HashMap<String, Double> chartMap = new HashMap<String, Double>();
        chartMap = dbAdapter.retrieveExpenseAmount(dateFrom, dateTo, selectedExpenses);
        return chartMap;
    }

    public HashMap<String, String> mapSalary(int employeeNumber){
        HashMap<String, String> salaryMap = new HashMap<String, String>();
        salaryMap = dbAdapter.retrieveSalaryReportsDetails(employeeNumber);
        return salaryMap;
    }

    public HashMap<String, Double> mapBoreDetails(Integer dateFrom, Integer dateTo){
        HashMap<String, Double> boreDetailsMap = new HashMap<String, Double>();
        boreDetailsMap = dbAdapter.retrieveBoreReportsDetails(dateFrom, dateTo);

        try{
            double mileage = 100.0; // TODO : Figure out where Mileage comes from
            double diesel_in_hand = 0;//boreDetailsMap.get("diesel_used") * ((boreDetailsMap.get("engine_hrs_end") - boreDetailsMap.get("engine_hrs_start"))/mileage);
            double casing_pipe_in_hand = 0;//boreDetailsMap.get("total_pipe_length") - boreDetailsMap.get("casting_depth");
            boreDetailsMap.put("diesel_in_hand", diesel_in_hand);
            boreDetailsMap.put("casing_pipe_in_hand", casing_pipe_in_hand);
        }
        catch(Exception e){
            Log.e("Error : ", "Records not available");
        }
        return boreDetailsMap;
    }

}
