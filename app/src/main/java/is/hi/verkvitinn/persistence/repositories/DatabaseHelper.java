package is.hi.verkvitinn.persistence.repositories;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
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
    private static final int DATABASE_VERSION = 1;
    private static final String USERS_TABLE_CREATE = "CREATE TABLE users(id integer,"
            + "username varchar(255),"
            + "password varchar(255),"
            + "role varchar(255),"
            + "name varchar(255),"
            + "headworker boolean"
            + ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(USERS_TABLE_CREATE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}