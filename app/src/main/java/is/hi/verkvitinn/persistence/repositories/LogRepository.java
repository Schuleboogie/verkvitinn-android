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
        String timestampout = "";
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS.SSS");

        if(newLog.getTimeIn()!=null){
            timestampin = formatter.format(newLog.getTimeIn());
        }

        if(newLog.getTimeOut()!=null){
            timestampout = formatter.format(newLog.getTimeOut());
        }

        ContentValues log = new ContentValues();
        log.put("timeIn", timestampin);
        log.put("timeOut", timestampout);
        log.put("projectId", newLog.getProjectId());
        log.put("username", newLog.getUsername());
        writeDB.insert("log", null, log);

        return newLog;
    }

    public static Log update(Log updateLog, Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        SQLiteDatabase writeDB = dbHelper.getWritableDatabase();
        ContentValues log = new ContentValues();


        String timestampin = "";
        String timestampout = "";
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS.SSS");

        if(updateLog.getTimeIn()!=null){
            timestampin = formatter.format(updateLog.getTimeIn());
        }

        if(updateLog.getTimeOut()!=null){
            timestampout = formatter.format(updateLog.getTimeOut());
        }

        log.put("timeIn", timestampin);
        log.put("timeOut", timestampout);
        log.put("projectId", updateLog.getProjectId());
        log.put("username", updateLog.getUsername());

        boolean projectExists = false;
        String selection = "id = ?";
        String[] selectionArgs = { "" + updateLog.getId() };
        Cursor results = readDB.query("log", null, selection, selectionArgs, null, null, null);
        if (results.getCount() > 0) {
            projectExists = true;
        }

        if (projectExists) {
            writeDB.update("log", log, selection, selectionArgs);
        }
        return updateLog;
    }
}
