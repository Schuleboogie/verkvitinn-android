package is.hi.verkvitinn.persistence.repositories;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
/*import java.util.List;

public abstract class DB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String USERS_TABLE_CREATE = "CREATE TABLE users("
            + "id int NOT NULL AUTO_INCREMENT,"
            + "username varchar(255),"
            + "password varchar(255),"
            + "role varchar(255),"
            + "name varchar(255),"
            + "headworker boolean"
            + ");";

    DB(Context context) {
        super(context, "Verkvitinn", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS_TABLE_CREATE);
    }
}*/

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Verkvitinn.db";
    private static final int DATABASE_VERSION = 4;
    private static final String USERS_TABLE_CREATE = "CREATE TABLE users(id integer primary key,"
            + "username varchar(255),"
            + "password varchar(255),"
            + "role varchar(255),"
            + "name varchar(255),"
            + "headworker boolean"
            + ");";
    private static final String GROUPS_TABLE_CREATE = "CREATE TABLE groups(id integer primary key,"
            + "name varchar(255),"
            + "workers varchar(255)"
            + ");";
    private static final String MILESTONES_TABLE_CREATE = "CREATE TABLE milestones(id integer primary key,"
            + "projectId integer,"
            + "timestamp text,"
            + "title varchar(255)"
            + ");";
    private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS users;";
    private static final String SQL_DELETE_PROJECTS = "DROP TABLE IF EXISTS projects;";
    private static final String SQL_DELETE_GROUPS = "DROP TABLE IF EXISTS groups;";
    private static final String SQL_DELETE_MILESTONES = "DROP TABLE IF EXISTS milestones;";

    private static final String PROJECTS_TABLE_CREATE = "CREATE TABLE projects(id integer primary key,"
            + "name varchar(255),"
            + "admin varchar(255),"
            + "description varchar(255),"
            + "location varchar(255),"
            + "tools varchar(255),"
            + "estTime varchar(255),"
            + "startTime text,"
            + "finishTime text,"
            + "workers text,"
            + "headWorkers text,"
            + "status varchar(255)"
            + ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS_TABLE_CREATE);
        db.execSQL(PROJECTS_TABLE_CREATE);
        db.execSQL(GROUPS_TABLE_CREATE);
        db.execSQL(MILESTONES_TABLE_CREATE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USERS);
        db.execSQL(SQL_DELETE_PROJECTS);
        db.execSQL(SQL_DELETE_GROUPS);
        db.execSQL(SQL_DELETE_MILESTONES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }
}