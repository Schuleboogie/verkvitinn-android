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

public abstract class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "database_name";
    private static final String DATABASE_TABLE = "table_name";
    private static final int DATABASE_VERSION = 1;

    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}