package is.hi.verkvitinn.persistence.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import is.hi.verkvitinn.persistence.entities.Group;
import is.hi.verkvitinn.persistence.entities.User;
import is.hi.verkvitinn.persistence.repositories.DatabaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunna on 20.3.2017.
 */

public class WorkerGroupRepository {

    public static Group save(Group newGroup, Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase writeDB = dbHelper.getWritableDatabase();

        ContentValues group = new ContentValues();
        group.put("name", newGroup.getName());
        group.put("workers", newGroup.getWorkers().toString());
        writeDB.insert("groups", null, group);
        return null;
    }

    public static List<Group> findAll(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        Cursor results = readDB.query("groups", null, null, null, null, null, null);
        if (results.getCount() > 0) {
            List<Group> groupList = new ArrayList<Group>();
            results.moveToFirst();
            while (results.isAfterLast() == false) {
                Log.d(results.getString(2),"þarf að verða users");
                //groupList.add(new Group(results.getString(1), results.getString(2)));
                results.moveToNext();
            }
            results.close();
            return groupList;
        }
        else {
            return null;
        }
    }

    public static void delete(Group group) {
    }
}
