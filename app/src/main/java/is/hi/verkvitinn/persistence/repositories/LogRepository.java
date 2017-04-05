package is.hi.verkvitinn.persistence.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import is.hi.verkvitinn.persistence.entities.Log;
import is.hi.verkvitinn.persistence.entities.Milestone;
import is.hi.verkvitinn.persistence.entities.User;

/**
 * Created by sunna on 4.4.2017.
 */

public class LogRepository {

    public static Log save(Log newLog, Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase writeDB = dbHelper.getWritableDatabase();

        // Date conversion
        String timestampin = "";
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS.SSS");

        if(newLog.getTimeIn()!=null){
            timestampin = formatter.format(newLog.getTimeIn());
        }


        ContentValues log = new ContentValues();
        log.put("timeIn", timestampin);
        log.put("timeOut", "");
        log.put("projectId", newLog.getProjectId());
        log.put("username", newLog.getUsername());
        writeDB.insert("log", null, log);

        return newLog;
    }

    public static void update(Log updateLog, Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase writeDB = dbHelper.getWritableDatabase();


        String timestampout = "";
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS.SSS");


        if(updateLog.getTimeOut()!=null){
            timestampout = formatter.format(updateLog.getTimeOut());
        }

        String update = "Update log set timeOut='"+timestampout+"' where projectId="+updateLog.getProjectId()+" and username='"+updateLog.getUsername()+"' and timeOut=''";
        writeDB.execSQL(update);
    }

    public static Boolean sholdCheckout(String username, Long projectId, Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        String select = "Select * from log where username='"+username+"' and projectId="+projectId+" and timeOut=''";
        Cursor cursor = readDB.rawQuery(select, null);
        if(cursor.getCount()>0)
            return true;
        return false;
    }

    public static ArrayList<String> getOnCall(Long projectId, Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        String select = "Select * from log where projectId="+projectId+" and timeOut=''";
        Cursor cursor = readDB.rawQuery(select, null);
        ArrayList<String> onCall=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            onCall.add(cursor.getString(3));
            cursor.moveToNext();
        }
        return onCall;
    }

    public static ArrayList<Log> getForAdmin(Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        String select = "Select * from log where timeOut<>''";
        Cursor cursor = readDB.rawQuery(select, null);
        ArrayList<Log> forAdmin=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS.SSS");
            Date timeIn = null;
            try {
                timeIn = formatter.parse(cursor.getString(1));
            }
            catch (ParseException e) {
                System.out.println("villa í timeIn conversion");
            }
            Date timeOut = null;
            try {
                timeOut = formatter.parse(cursor.getString(1));
            }
            catch (ParseException e) {
                System.out.println("villa í timeOut conversion");
            }

            Log templog = new Log(cursor.getLong(4), timeIn, timeOut, cursor.getString(3));
            forAdmin.add(templog);
            cursor.moveToNext();
        }
        return forAdmin;
    }
}
