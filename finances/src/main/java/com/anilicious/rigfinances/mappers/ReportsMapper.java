package com.anilicious.rigfinances.mappers;

import android.content.Context;

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

    public ReportsMapper(Context context){
        this.context = context.getApplicationContext();
    }

    public HashMap<String, Double> mapExpenses(String dateFrom, String dateTo, List<String> selectedExpenses){

        HashMap<String, Double> chartMap = new HashMap<String, Double>();
        DBAdapter dbAdapter = DBAdapter.getInstance(context);
        chartMap = dbAdapter.retrieveExpenseAmount(dateFrom, dateTo, selectedExpenses);
        /*);
        chartMap.put(CommonUtils.CONSTANTS.COOK, dbAdapter.retrieveCookAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.MAINTENANCE, dbAdapter.retrieveMaintenanceAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.PIPE, dbAdapter.retrievePipeAmount(dateFrom, dateTo));
            chartMap.put(CommonUtils.CONSTANTS.ROAD, dbAdapter.retrieveRoadAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.SITE, dbAdapter.retrieveSiteAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.SALARY, dbAdapter.retrieveSalaryAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.TOOLS, dbAdapter.retrieveToolAmount(dateFrom, dateTo));*/

        return chartMap;
    }

    public HashMap<String, String> mapSalary(int employeeNumber){

        HashMap<String, String> salaryMap = new HashMap<String, String>();

        DBAdapter dbAdapter = DBAdapter.getInstance(context);
        salaryMap = dbAdapter.retrieveSalaryReportsDetails(employeeNumber);

        return salaryMap;
    }



}
