package com.anilicious.rigfinances.mappers;

import android.content.Context;

import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.HashMap;

/**
 * Created by ANBARASI on 6/4/15.
 */
public class SalaryReportsMapper {

    Context context;

    public SalaryReportsMapper(Context context){
        this.context = context.getApplicationContext();
    }

    public HashMap<String, String> mapSalaryReport(String employeeNumber, String yearFrom, String yearTo){
        HashMap<String, String> salaryMap = new HashMap<String, String>();

        DBAdapter dbAdapter = DBAdapter.getInstance(context);
        // TODO Decide how to reuse the bean here.. no point letting everything stray
        return salaryMap;
    }
}
