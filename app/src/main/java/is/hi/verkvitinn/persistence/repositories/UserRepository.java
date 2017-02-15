package is.hi.verkvitinn.persistence.repositories;

import is.hi.verkvitinn.persistence.entities.User;
//import is.hi.verkvitinn.persistence.repositories.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class UserRepository {
	//private static SQLiteOpenHelper db = DB();

	public User save(User newUser) {
		/*
		SQLiteDatabase writeDB = db.getWritableDatabase();
		ContentValues user = new ContentValues();
		user.put("username", newUser.getUsername());
		user.put("password", newUser.getPassword());
		user.put("role", newUser.getRole());
		user.put("name", newUser.getName());
		user.put("headworker", newUser.getHeadWorker());
		writeDB.insert("users", null, user);
		*/
		return null;
	}

	public String getPasswordByUsername(String username) {
		/*SQLiteDatabase readDB = db.getReadableDatabase();
		Cursor results = readDB.query(true, ["users"], "password", "username=" + username, null, null, null, null, null);
		if (results.getCount() > 0) {
			return results.getString(0);
		}
		else return null;*/
		return "";
	}

	public User findByUsername(String username) { return null; }
	public List<User> findByRole(String role) {
		return null;
	}
}
