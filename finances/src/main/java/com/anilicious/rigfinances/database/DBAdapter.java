package com.anilicious.rigfinances.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.anilicious.rigfinances.beans.Bore;
import com.anilicious.rigfinances.beans.Cook;
import com.anilicious.rigfinances.beans.CookItem;
import com.anilicious.rigfinances.beans.Credit;
import com.anilicious.rigfinances.beans.Diesel;
import com.anilicious.rigfinances.beans.Employee;
import com.anilicious.rigfinances.beans.Maintenance;
import com.anilicious.rigfinances.beans.Pipe;
import com.anilicious.rigfinances.beans.Road;
import com.anilicious.rigfinances.beans.Salary;
import com.anilicious.rigfinances.beans.Site;
import com.anilicious.rigfinances.beans.ToolItem;
import com.anilicious.rigfinances.beans.Tools;
import com.anilicious.rigfinances.beans.User;
import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ANBARASI on 14/11/14.
 */
public class DBAdapter extends SQLiteOpenHelper{

    private static DBAdapter dbInstance;

    private static final String DATABASE_NAME = "sivagamiborewells.db";
    private static final int DATABASE_VERSION = 1;

    /* BEGIN : Tables - Create Statements */

    private static final String DIESEL_DATABASE_NAME = "DIESEL";
    private static final String DIESEL_DATABASE_CREATE = "CREATE TABLE " + DIESEL_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'D' + number
            "Diesel_Date" + " integer, " +
            "diesel_for" + " text, " +
            "litres" + " number, " +
            "amount" + " number, " +
            "spent_by" + " text, " +
            "diesel_in_hand" + " number);"; // DIH = DIH+litres - find out how to implement formulae

    private static final String COOK_DATABASE_NAME = "COOK";
    private static final String COOK_DATABASE_CREATE = "CREATE TABLE " + COOK_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'C' + number
            "Cook_Date" + " integer, " +
            "item" + " text, " +
            "quantity" + " number, " +
            "amount" + " number, " +
            "spent_by" + " text);";

    /*private static final String COOKITEM_DATABASE_NAME = "COOKITEM";
    private static final String COOKITEM_DATABASE_CREATE = "CREATE TABLE " + COOKITEM_DATABASE_NAME + "(" +
            "cook_key" + " INTEGER, " + // 'C' + number
            "item_key" + " number, " +
            "item" + " text, " +
            "quantity" + " number, " +
            "amount" + " number);";*/

    private static final String ROAD_DATABASE_NAME = "ROAD";
    private static final String ROAD_DATABASE_CREATE = "CREATE TABLE " + ROAD_DATABASE_NAME + "(" +
            "_id " + "INTEGER, " + // 'R' + number
            "Road_Date" + " integer, " +
            "expense" + " text, " +
            "amount" + " number, " +
            "spent_by" + " text);";

    private static final String MAINTENANCE_DATABASE_NAME = "MAINTENANCE";
    private static final String MAINTENANCE_DATABASE_CREATE = "CREATE TABLE " + MAINTENANCE_DATABASE_NAME + "(" +
            "_id " + "INTEGER, " + // 'M' + number
            "Maintenance_Date" + " integer, " +
            "work_type" + " text, " +
            "service" + " boolean, " +
            "engine_hours" + " real, " +
            "amount" + " number, " +
            "reason" + " text, " +
            "spent_by" + " text);";

    private static final String TOOL_DATABASE_NAME = "TOOL";
    private static final String TOOL_DATABASE_CREATE = "CREATE TABLE " + TOOL_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'T' + number
            "Tool_Date" + " Number, " +
            "item" + " text, " +
            "detail" + " text, " +
            "quantity" + " number, " +
            "amount" + " number, " +
            "spent_by" + " text);";

    /*private static final String TOOLITEM_DATABASE_NAME = "TOOLITEM";
    private static final String TOOLITEM_DATABASE_CREATE = "CREATE TABLE " + TOOLITEM_DATABASE_NAME + "(" +
            "tool_key" + "INTEGER, " + // 'C' + number
            "item_key" + " number, " +
            "item" + " text, " +
            "details" + " text, " +
            "company" + " text, " +
            "quantity" + " number, " +
            "amount" + " number);";*/

    private static final String PIPE_DATABASE_NAME = "PIPE";
    private static final String PIPE_DATABASE_CREATE = "CREATE TABLE " + PIPE_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'P' + number
            "Pipe_Date" + " integer, " +
            "work_type" + " text, " +
            "length" + " real, " +
            "type" + " text, " +
            "amount" + " real, " +
            "spent_by" + " text, " +
            "remarks" + " text);";

    private static final String SALARY_DATABASE_NAME = "SALARY";
    private static final String SALARY_DATABASE_CREATE = "CREATE TABLE " + SALARY_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'S' + number
            "Salary_Date" + " integer, " +
            "employee_number" + " INTEGER, " +
            "employee_name" + " text, " +
            "amount" + " number, " +
            "spent_by" + " text, " +
            "reason" + " text);";

    private static final String SITE_DATABASE_NAME = "SITE_EXPENSES";
    private static final String SITE_DATABASE_CREATE = "CREATE TABLE " + SITE_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'SE' + number
            "Site_Date" + " Integer, " +
            "work_type" + " text, " +
            "amount" + " number, " +
            "remarks" + " text, " +
            "spent_by" + " text);";

    private static final String CREDIT_DATABASE_NAME = "CREDIT";
    private static final String CREDIT_DATABASE_CREATE = "CREATE TABLE " + CREDIT_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'CR' + number
            "Credit_Date" + " integer, " +
            "amount_received" + " number, " +
            "received_from" + " text, " +
            "received_by" + " text, " +
            "remarks" + " text) ;" ;
            /*"amount_in_credit" + " text)*/

    private static final String BORE_DATABASE_NAME = "BORE_DETAILS";
    private static final String BORE_DATABASE_CREATE = "CREATE TABLE " + BORE_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'B' + number
            "Bore_Date" + " integer, " +
            "total_depth" + " number, " +
            "casting_depth" + " text, " +
            "engine_hrs_start" + " number, " +
            "engine_hrs_end" + " number, " +
            "customer_name" + " text, " +
            "place" + " text, " +
            "agent_name" + " text, " +
            "bore_type" + " text, " +
            "bill_amount" + " text, " +
            "commission" + " text, " +
            "engine_running_time" + " text, " +
            /*"total_amount" + " text, " +*/
            "diesel_used" + " text);"
            /*"last_engine_hr_end" + " text);"*/;


    private static final String EMPLOYEE_DATABASE_NAME = "EMPLOYEE_DETAILS";
    private static final String USER_DATABASE_NAME = "USER_DETAILS";

    private static final String EMPLOYEE_DATABASE_CREATE = "CREATE TABLE " + EMPLOYEE_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'B' + number
            "Emp_Date" + " integer, " +
            "employee_name" + " text, " +
            "employee_number" + " INTEGER, " +
            "date_of_joining" + " integer, " +
            "date_of_leaving" + " integer, " +
            "current_balance" + " REAL, " +
            "remarks" + " text, " +
            "salary" + " REAL);";
    private static final String USER_DATABASE_CREATE = "CREATE TABLE " + USER_DATABASE_NAME + "(" +
            "_id " + "INTEGER primary key AUTOINCREMENT, " + // 'U' + number
            "user_name" + " text, " +
            "password" + " text, " +
            "role" + " text);";

    /* END : Tables - Create Statements */

    private static final String[] tables = new String[] {USER_DATABASE_CREATE,
                                            DIESEL_DATABASE_CREATE,
                                            COOK_DATABASE_CREATE,
                                            /*COOKITEM_DATABASE_CREATE,*/
                                            ROAD_DATABASE_CREATE,
                                            MAINTENANCE_DATABASE_CREATE,
                                            TOOL_DATABASE_CREATE,
                                            /*TOOLITEM_DATABASE_CREATE,*/
                                            PIPE_DATABASE_CREATE,
                                            SALARY_DATABASE_CREATE,
                                            SITE_DATABASE_CREATE,
                                            CREDIT_DATABASE_CREATE,
                                            BORE_DATABASE_CREATE,
                                            EMPLOYEE_DATABASE_CREATE};

    public static DBAdapter getInstance(Context context){
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if(dbInstance == null){
            dbInstance = new DBAdapter(context.getApplicationContext());
        }
        return dbInstance;
    }

    private DBAdapter(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for(String table : tables){
            sqLiteDatabase.execSQL(table);
            Log.d("Created! ", table);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(DBAdapter.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        for(String table : tables){
            //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            sqLiteDatabase.execSQL(table);
            Log.d("Created! ", table);
        }
    }

    /* BEGIN : Tables - Insert Methods */

    public void insertUser(User user){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name", user.getUsername());
        values.put("password", user.getPassword());
        values.put("role", user.getRole());

        database.insert(USER_DATABASE_NAME, null, values);
        database.close();
    }

    public List<User> retrieveUsers(){
        List<User> usersList = new ArrayList<User>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(USER_DATABASE_NAME, new String[] {"user_name", "password", "role"}, null, null, null, null, null);

        while(cursor.moveToNext()){
            String userName = cursor.getString(cursor.getColumnIndex("user_name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String role = cursor.getString(cursor.getColumnIndex("role"));

            User user = new User();
            user.setUsername(userName);
            user.setPassword(password);
            user.setRole(role);
            usersList.add(user);
        }

        database.close();

        return usersList;
    }


    public void insertDiesel(Diesel diesel){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Diesel_Date", diesel.getDate());
        values.put("diesel_for", diesel.getDieselFor());
        values.put("litres", diesel.getLitres());
        values.put("amount", diesel.getTotalAmount());
        values.put("spent_by", diesel.getSpentBy());
        values.put("diesel_in_hand", diesel.getDieselInHand());

        database.insert(DIESEL_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertCook(Cook cook){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item", cook.getItem());
        values.put("Cook_Date", cook.getDate());
        values.put("quantity", cook.getQuantity());
        values.put("amount", cook.getPrice());
        values.put("spent_by", cook.getSpentBy());
        /*values.put("amount", cook.getAmount());*/

        database.insert(COOK_DATABASE_NAME, null, values);
        database.close();
    }

    /*public void insertCookItem(CookItem cookItem){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        database.insert(COOKITEM_DATABASE_NAME, null, values);
        database.close();
    }*/

    public void insertRoad(Road road){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Road_Date", road.getDate());
        values.put("expense", road.getExpenseDetails());
        values.put("amount", road.getTotalAmount());
        values.put("spent_by", road.getSpentBy());

        database.insert(ROAD_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertMaintenance(Maintenance maintenance){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Maintenance_Date", maintenance.getDate());
        values.put("work_type", maintenance.getWorkType());
        values.put("service", maintenance.isService());
        values.put("engine_hours", maintenance.getEngineHrs());
        values.put("amount", maintenance.getAmount());
        values.put("reason", maintenance.getReason());
        values.put("spent_by", maintenance.getSpentBy());

        database.insert(MAINTENANCE_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertTool(Tools tool){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Tool_Date", tool.getDate());
        values.put("item", tool.getItem());
        values.put("detail", tool.getDetails());
        values.put("quantity", tool.getQuantity());
        values.put("amount", tool.getPrice());
        values.put("spent_by", tool.getSpentBy());
        /*values.put("company", tool.getCompany());
        values.put("total_amount", tool.getTotalAmount());*/

        database.insert(TOOL_DATABASE_NAME, null, values);
        database.close();
    }

    /*public void insertToolItem(ToolItem toolItem){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item", toolItem.getItem());
        values.put("details", toolItem.getDetails());
        values.put("company", toolItem.getCompany());
        values.put("quantity", toolItem.getQuantity());
        values.put("amount", toolItem.getAmount());
        values.put("amount", toolItem.getTotalAmount());

        database.insert(TOOLITEM_DATABASE_NAME, null, values);
        database.close();
    }*/

    public void insertPipe(Pipe pipe){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Pipe_Date", pipe.getDate());
        values.put("work_type", pipe.getWorkType());
        values.put("length", pipe.getLength());
        values.put("type", pipe.getType());
        values.put("amount", pipe.getAmount());
        values.put("spent_by", pipe.getSpentBy());
        values.put("remarks", pipe.getRemarks());

        database.insert(PIPE_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertSalary(Salary salary){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Salary_Date", salary.getDate());
        values.put("employee_number", salary.getEmployeeNumber());
        values.put("employee_name", salary.getEmployeeName());
        values.put("amount", salary.getAmount());
        values.put("spent_by", salary.getSpentBy());
        values.put("reason", salary.getReason());

        database.insert(SALARY_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertCredit(Credit credit){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Credit_Date", credit.getDate());
        values.put("amount_received", credit.getAmountReceived());
        values.put("received_from", credit.getFrom());
        values.put("received_by", credit.getReceivedBy());
        values.put("remarks", credit.getRemarks());
        /*values.put("amount_in_credit", credit.getAmountInCredit());*/

        database.insert(CREDIT_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertSite(Site site){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Site_Date", site.getDate());
        values.put("work_type", site.getWorkType());
        values.put("remarks", site.getRemarks());
        values.put("amount", site.getTotalAmount());
        values.put("spent_by", site.getSpentBy());

        database.insert(SITE_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertBore(Bore bore){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Bore_Date", bore.getDate());
        values.put("total_depth", bore.getTotalDepth());
        values.put("casting_depth", bore.getCastingDepth());
        values.put("engine_hrs_start", bore.getEngineHrsStart());
        values.put("engine_hrs_end", bore.getEngineHrsEnd());
        values.put("customer_name", bore.getCustomerName());
        values.put("place", bore.getPlace());
        values.put("agent_name", bore.getAgentName());
        values.put("bore_type", bore.getBoreType());
        values.put("bill_amount", bore.getBillAmount());
        values.put("commission", bore.getCommission());
        /*values.put("total_amount", bore.getTotalAmount());*/

        database.insert(BORE_DATABASE_NAME, null, values);
        database.close();
    }

    public void insertEmployee(Employee employee){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Emp_Date", employee.getDate());
        values.put("employee_name", employee.getName());
        values.put("employee_number", employee.getNumber());
        values.put("date_of_joining", employee.getDateOfJoining());
        values.put("date_of_leaving", employee.getDateOfLeaving());
        values.put("current_balance", employee.getCurrentBalance());
        values.put("remarks", employee.getRemarks());
        values.put("salary", employee.getSalary());

        database.insert(EMPLOYEE_DATABASE_NAME, null, values);
        database.close();
    }

    /*public double retrieveDieselAmount(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(DIESEL_DATABASE_NAME,
                new String[] {"SUM(amount)"},
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        double totalAmount = 0.0;

        while(cursor.moveToNext()){
           totalAmount = cursor.getDouble(0);
        }

        database.close();
        return totalAmount;
    }

    public double retrieveCookAmount(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(COOK_DATABASE_NAME,
                new String[] {"SUM(price)"},
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        double totalAmount = 0.0;

        while(cursor.moveToNext()){
            totalAmount = cursor.getDouble(0);
        }

        database.close();
        return totalAmount;
    }

    public double retrieveMaintenanceAmount(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(MAINTENANCE_DATABASE_NAME,
                new String[] {"SUM(amount)"},
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        double totalAmount = 0.0;

        while(cursor.moveToNext()){
            totalAmount = cursor.getDouble(0);
        }

        database.close();
        return totalAmount;
    }

    public double retrievePipeAmount(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(PIPE_DATABASE_NAME,
                new String[] {"SUM(amount)"},
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        double totalAmount = 0.0;

        while(cursor.moveToNext()){
            totalAmount = cursor.getDouble(0);
        }

        database.close();
        return totalAmount;
    }

    public double retrieveRoadAmount(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(ROAD_DATABASE_NAME,
                new String[] {"SUM(amount)"},
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        double totalAmount = 0.0;

        while(cursor.moveToNext()){
            totalAmount = cursor.getDouble(0);
        }

        database.close();
        return totalAmount;
    }

    public double retrieveSalaryAmount(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(SALARY_DATABASE_NAME,
                new String[] {"SUM(amount)"},
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        double totalAmount = 0.0;

        while(cursor.moveToNext()){
            totalAmount = cursor.getDouble(0);
        }

        database.close();
        return totalAmount;
    }

    public double retrieveSiteAmount(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(SITE_DATABASE_NAME,
                new String[] {"SUM(amount)"},
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        double totalAmount = 0.0;

        while(cursor.moveToNext()){
            totalAmount = cursor.getDouble(0);
        }

        database.close();
        return totalAmount;
    }

    public double retrieveToolAmount(String dateFrom, String dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TOOL_DATABASE_NAME,
                new String[] {"SUM(price)"},
                "date BETWEEN ? AND ?",
                new String[] {dateFrom, dateTo},
                null, null, null);
        double totalAmount = 0.0;

        while(cursor.moveToNext()){
            totalAmount = cursor.getDouble(0);
        }

        database.close();
        return totalAmount;
    }*/

    public HashMap<String, Double> retrieveExpenseAmount(String dateFrom, String dateTo, List<String> selectedExpenses){
        HashMap<String, Double> expenseReport = new HashMap<String, Double>();
        List<String> columnRetriever = new ArrayList<String>();

        //String queryColumns = "SELECT";
        //String queryTables = "";
        //String columnName = "";

        String query = "";
        SQLiteDatabase database = this.getReadableDatabase();
        for(String type : selectedExpenses){
            query = "SELECT SUM(amount) from " + type;
            /* switch(type){
                case CommonUtils.CONSTANTS.DIESEL :
                    columnName = "d";
                    queryTables += " " + DIESEL_DATABASE_NAME + " d";
                    break;
                case CommonUtils.CONSTANTS.MAINTENANCE :
                    columnName = "m";
                    queryTables += " " + MAINTENANCE_DATABASE_NAME + " m";
                    break;
                case CommonUtils.CONSTANTS.COOK :
                    columnName = "c";
                    queryTables += " " + COOK_DATABASE_NAME + " c";
                    break;
                case CommonUtils.CONSTANTS.PIPE :
                    columnName = "p";
                    queryTables += " " + PIPE_DATABASE_NAME + " p";
                    break;
                case CommonUtils.CONSTANTS.ROAD :
                    columnName = "r";
                    queryTables += " " + ROAD_DATABASE_NAME + " r";
                    break;
                case CommonUtils.CONSTANTS.SITE :
                    columnName = "si";
                    queryTables += " " + SITE_DATABASE_NAME + " si";
                    break;
                case CommonUtils.CONSTANTS.SALARY :
                    columnName = "sa";
                    queryTables += " " + SALARY_DATABASE_NAME + " sa";
                    break;
                case CommonUtils.CONSTANTS.TOOLS :
                    columnName = "t";
                    queryTables += " " + TOOL_DATABASE_NAME + " t";
                    break;
            }
            queryColumns += " SUM(" + columnName + ".amount)";
            if(selectedExpenses.indexOf(type) != selectedExpenses.size() - 1){
                queryColumns += ",";
                queryTables += " OUTER JOIN";
            }
            columnRetriever.add(type); */

            Cursor cursor = database.rawQuery(query, null);

            while(cursor.moveToNext()){
                //    for(String entry : columnRetriever){
                expenseReport.put(type, cursor.getDouble(0));
                //  }
            }
        }
        // TODO: Error handling : If no entry made, just return 0

        //String query = queryColumns + " FROM" + queryTables + ";";// " WHERE " + columnName + ".date BETWEEN '" + dateFrom + "' AND '" + dateTo +"';";

        database.close();
        return expenseReport;
    }



    public HashMap<String, String> retrieveSalaryReportsDetails(int employeeNumber){
        SQLiteDatabase database = this.getReadableDatabase();
        HashMap<String, String> salaryReport = new HashMap<String, String>();

        String query = "SELECT employee_name,employee_number,sum(date_of_joining),sum(date_of_leaving),sum(salary) FROM "+ EMPLOYEE_DATABASE_NAME
                + " WHERE employee_number = " + employeeNumber + ";";
                /*"SELECT E.employee_name, E.employee_number, E.date_of_joining, E.date_of_leaving, SUM(S.amount) " +
                "FROM "+ EMPLOYEE_DATABASE_NAME+" E INNER JOIN "+ SALARY_DATABASE_NAME +" S "+
                "ON E.employee_number = S.employee_number " +
                "WHERE E.employee_number = " + employeeNumber + " GROUP BY E.employee_number;";*/

        Cursor cursor = database.rawQuery(query, null);

        while(cursor.moveToNext()){
            salaryReport.put("employee_name", cursor.getString(0));
            salaryReport.put("joining_date", cursor.getString(2));
            salaryReport.put("leaving_date", cursor.getString(3));
            salaryReport.put("salary_given", cursor.getString(4));
        }

        database.close();
        return salaryReport;
    }

    public HashMap<String, Double> retrieveBoreReportsDetails(Integer dateFrom, Integer dateTo){
        SQLiteDatabase database = this.getReadableDatabase();
        HashMap<java.lang.String, Double> boreDetailsReport = new HashMap<java.lang.String, Double>();

        String query1 = "SELECT group_concat(employee_name),employee_number,sum(date_of_joining),sum(date_of_leaving),sum(salary) FROM "+ EMPLOYEE_DATABASE_NAME
                + " WHERE employee_number = 1001;";
                /*"SELECT SUM(total_depth), SUM(casting_depth), SUM(bill_amount), SUM(commission),MIN(engine_hrs_start), MAX(engine_hrs_end) " +
                "FROM "+ BORE_DATABASE_NAME +
                " WHERE Bore_Date BETWEEN " + dateFrom + " AND " + dateTo + ";";*/

        Cursor cursor1 = database.rawQuery(query1, null);

        String query2 = "SELECT SUM(length) FROM " + PIPE_DATABASE_NAME + " WHERE Pipe_Date BETWEEN " + dateFrom + " AND " + dateTo + ";";

        Cursor cursor2 = database.rawQuery(query2, null);

        while(cursor1.moveToNext()){
            boreDetailsReport.put("total_depth", cursor1.getDouble(0));
            boreDetailsReport.put("casting_depth", cursor1.getDouble(1));
            boreDetailsReport.put("bill_amount", cursor1.getDouble(2));
            boreDetailsReport.put("commission", cursor1.getDouble(3));
            boreDetailsReport.put("engine_hrs_start", cursor1.getDouble(4));
            //boreDetailsReport.put("engine_hrs_end", cursor1.getDouble(5));
        }

        while(cursor2.moveToNext()){
            boreDetailsReport.put("total_pipe_length", cursor2.getDouble(0));
        }

        database.close();
        return boreDetailsReport;
    }

    /* BEGIN : Tables - Insert Methods */

    public void retrieveBoreReports(){ //TODO: Change return type
        SQLiteDatabase database = this.getReadableDatabase();
        /*Cursor cursor = database.rawQuery("SELECT SUM(amount), SUM(amount) FROM " +
                COOK_DATABASE_NAME + ", " +
                CREDIT_DATABASE_NAME + ", " +
                DIESEL_DATABASE_NAME + ", " +
                MAINTENANCE_DATABASE_NAME + ", " +
                PIPE_DATABASE_NAME + ", " +
                ROAD_DATABASE_NAME + ", " +
                SALARY_DATABASE_NAME + ", " +
                SITE_DATABASE_NAME + ", " +
                TOOL_DATABASE_NAME);
                */
    }

}
