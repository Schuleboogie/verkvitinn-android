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
        group.put("workers", convertWorkersToString(newGroup.getWorkers()));
        writeDB.insert("groups", null, group);
        return null;
    }

    private static String convertWorkersToString(ArrayList<User> workers){
        String s="";
        for(int n=0;n<workers.size();n++){
            s=s+workers.get(n).getUsername()+";";
        }
        return s;
    }

    private static ArrayList<User> convertStringToWorkers(String workerString, Context context){
        String[] arr = workerString.split(";");
        UserRepository userRep = new UserRepository();
        ArrayList<User> users = new ArrayList<>();
        for(int n=0;n<arr.length;n++){
            users.add(userRep.findByUsername(arr[n], context));
        }
        return users;
    }

    public static Group findByName(String name, Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        String selection = "name = ?";
        String[] selectionArgs = { name };
        Cursor results = readDB.query("groups", null, selection, selectionArgs, null, null, null);
        if (results.getCount() > 0) {
            Group foundGroup = null;
            results.moveToFirst();
            while (results.isAfterLast() == false) {
                foundGroup = new Group(results.getString(1), convertStringToWorkers(results.getString(2), context));
                results.moveToNext();
            }
            results.close();
            return foundGroup;
        }
        else return null;
    }

    public static List<Group> findAll(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        Cursor results = readDB.query("groups", null, null, null, null, null, null);
        if (results.getCount() > 0) {
            List<Group> groupList = new ArrayList<Group>();
            results.moveToFirst();
            while (results.isAfterLast() == false) {
                groupList.add(new Group(results.getString(1), convertStringToWorkers(results.getString(2), context)));
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
