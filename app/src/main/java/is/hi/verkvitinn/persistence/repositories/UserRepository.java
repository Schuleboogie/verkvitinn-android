package is.hi.verkvitinn.persistence.repositories;

import is.hi.verkvitinn.persistence.entities.User;
import is.hi.verkvitinn.persistence.repositories.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.view.View;

import java.util.List;

public class UserRepository {
    public static User save(User newUser, Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase writeDB = dbHelper.getWritableDatabase();

		ContentValues user = new ContentValues();
		user.put("username", newUser.getUsername());
		user.put("password", newUser.getPassword());
		user.put("role", newUser.getRole());
		user.put("name", newUser.getName());
		user.put("headworker", newUser.getHeadWorker());
		writeDB.insert("users", null, user);
		return null;
	}

	public static String getPasswordByUsername(String username, Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase readDB = dbHelper.getReadableDatabase();
		String[] projection = {"password"};
		String selection = "username = ?";
		String[] selectionArgs = { username };
		Cursor results = readDB.query("users", projection, selection, selectionArgs, null, null, null);
		if (results.getCount() > 0) {
			String password = "";
			results.moveToFirst();
			while (results.isAfterLast() == false) {
				password = results.getString(0);
				results.moveToNext();
			}
			results.close();
			return password;
		}
		else return null;
	}

	public static User findByUsername(String username, Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase readDB = dbHelper.getReadableDatabase();
		String selection = "username = ?";
		String[] selectionArgs = { username };
		Cursor results = readDB.query("users", null, selection, selectionArgs, null, null, null);
		if (results.getCount() > 0) {
			User foundUser = null;
			results.moveToFirst();
			while (results.isAfterLast() == false) {
				foundUser = new User(results.getString(1), results.getString(2), results.getString(3), results.getString(4), (results.getInt(5) != 0));
				results.moveToNext();
			}
			results.close();
			return foundUser;
		}
		else return null;
	}
	public static List<User> findByRole(String role) {
		return null;
	}
}
