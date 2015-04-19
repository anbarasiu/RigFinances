package com.anilicious.rigfinances.mappers;

import android.content.Context;

import com.anilicious.rigfinances.activities.ReportsActivity;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.HashMap;

/**
 * Created by ANBARASI on 24/1/15.
 * Mapper Class to fetch details from the DB and return to Fragment to render Charts
 */
public class ReportsMapper {

    Context context;

    public ReportsMapper(Context context){
        this.context = context.getApplicationContext();
    }

    public HashMap<String, Double> mapExpenses(String dateFrom, String dateTo){

        HashMap<String, Double> chartMap = new HashMap<String, Double>();

        // TODO Vouchers could be an object comprising of these Objects instead of constants
        // TODO Gosh! So many DB calls! Change the query already!
        DBAdapter dbAdapter = DBAdapter.getInstance(context);
        chartMap.put(CommonUtils.CONSTANTS.DIESEL, dbAdapter.retrieveDieselAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.COOK, dbAdapter.retrieveCookAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.MAINTENANCE, dbAdapter.retrieveMaintenanceAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.PIPE, dbAdapter.retrievePipeAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.ROAD, dbAdapter.retrieveRoadAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.SITE, dbAdapter.retrieveSiteAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.SALARY, dbAdapter.retrieveSalaryAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.TOOLS, dbAdapter.retrieveToolAmount(dateFrom, dateTo));

        return chartMap;
    }

    public HashMap<String, Double> mapSalary(String dateFrom, String dateTo){

        HashMap<String, Double> salaryMap = new HashMap<String, Double>();

        DBAdapter dbAdapter = DBAdapter.getInstance(context);
        chartMap.put(CommonUtils.CONSTANTS.DIESEL, dbAdapter.retrieveDieselAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.COOK, dbAdapter.retrieveCookAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.MAINTENANCE, dbAdapter.retrieveMaintenanceAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.PIPE, dbAdapter.retrievePipeAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.ROAD, dbAdapter.retrieveRoadAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.SITE, dbAdapter.retrieveSiteAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.SALARY, dbAdapter.retrieveSalaryAmount(dateFrom, dateTo));
        chartMap.put(CommonUtils.CONSTANTS.TOOLS, dbAdapter.retrieveToolAmount(dateFrom, dateTo));

        return salaryMap;
    }



}
