package is.hi.verkvitinn.persistence.repositories;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

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
}

