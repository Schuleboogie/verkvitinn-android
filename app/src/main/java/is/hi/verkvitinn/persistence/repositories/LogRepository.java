package is.hi.verkvitinn.persistence.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.text.Format;
import java.text.SimpleDateFormat;

import is.hi.verkvitinn.persistence.entities.Log;
import is.hi.verkvitinn.persistence.entities.Milestone;

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
}
