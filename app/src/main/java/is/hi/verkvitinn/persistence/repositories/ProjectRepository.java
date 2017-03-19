package is.hi.verkvitinn.persistence.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.entities.User;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectRepository {
	public static Project save(Project newProject, Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase writeDB = dbHelper.getWritableDatabase();
		SQLiteDatabase readDB = dbHelper.getReadableDatabase();

		// Conversions
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS.SSS");
		String startTime = formatter.format(newProject.getStartTime());
		String finishTime = formatter.format(newProject.getFinishTime());
		String workers = convertArrayToString(newProject.getWorkers());
		String headWorkers = convertArrayToString(newProject.getHeadWorkers());

		// Set project info
		ContentValues project = new ContentValues();
		project.put("name", newProject.getName());
		project.put("admin", newProject.getAdmin());
		project.put("description", newProject.getDescription());
		project.put("location", newProject.getLocation());
		project.put("tools", newProject.getTools());
		project.put("estTime", newProject.getEstTime());
		project.put("startTime", startTime);
		project.put("finishTime", finishTime);
		project.put("workers", workers);
		project.put("headWorkers", headWorkers);
		project.put("status", newProject.getStatus());

		// Update if project exists but create otherwise
		boolean projectExists = false;
		String selection = "id = ?";
		String[] selectionArgs = { "" + newProject.getId() };
		Cursor results = readDB.query("projects", null, selection, selectionArgs, null, null, null);
		if (results.getCount() > 0) {
			projectExists = true;
		}

		if (projectExists) {
			writeDB.update("projects", project, selection, selectionArgs);
		}
		else writeDB.insert("projects", null, project);

		return newProject;
	}
	private static String convertArrayToString(String[] array) {
		String str = "";
		for (int i = 0;i<array.length; i++) {
			str = str+array[i];
			// Do not append comma at the end of last element
			if(i<array.length-1){
				str = str+',';
			}
		}
		return str;
	}
	private static String[] convertStringToArray(String str) {
		String[] arr = str.split(",");
		return arr;
	}
	public static Project findOne(Long id, Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase readDB = dbHelper.getReadableDatabase();
		String selection = "id = ?";
		String[] selectionArgs = { "" + id };
		Cursor results = readDB.query("projects", null, selection, selectionArgs, null, null, null);
		if (results.getCount() > 0) {
			Project foundProject = null;
			results.moveToFirst();
			while (results.isAfterLast() == false) {
				// Conversions
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS.SSS");
				Date startTime = null;
				Date finishTime = null;
				try {
					startTime = formatter.parse(results.getString(7));
					finishTime = formatter.parse(results.getString(8));
				}
				catch (ParseException e) {
					// He he
				}
				String[] workers = convertStringToArray(results.getString(9));
				String[] headWorkers = convertStringToArray(results.getString(10));
				Log.d(results.getString(1), "fann þetta með þessu id");
				foundProject = new Project(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), startTime, finishTime, workers, headWorkers, results.getString(11));
				foundProject.setId(Long.valueOf(results.getInt(0)));
				results.moveToNext();
			}
			results.close();
			return foundProject;
		}
		else return null;
	}

	public static List<Project> findByAdmin(String admin, Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase readDB = dbHelper.getReadableDatabase();
		String selection = "admin = ?";
		String[] selectionArgs = { admin };
		Cursor results = readDB.query("projects", null, selection, selectionArgs, null, null, null);
		List<Project> foundProjects = new ArrayList<Project>();
		if (results.getCount() > 0) {
			Project foundProject = null;
			results.moveToFirst();
			while (results.isAfterLast() == false) {
				// Conversions
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS.SSS");
				Date startTime = null;
				Date finishTime = null;
				try {
					startTime = formatter.parse(results.getString(7));
					finishTime = formatter.parse(results.getString(8));
				}
				catch (ParseException e) {
					// He he
				}
				String[] workers = convertStringToArray(results.getString(9));
				String[] headWorkers = convertStringToArray(results.getString(10));
				Log.d(results.getString(0), "--id");
				foundProject = new Project(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getString(6), startTime, finishTime, workers, headWorkers, results.getString(11));
				foundProject.setId(Long.valueOf(results.getInt(0)));
				foundProjects.add(foundProject);
				results.moveToNext();
			}
			results.close();
			return foundProjects;
		}
		else return null;
	}

	public static List<Project> findAll() {
		return null;
	}

	public static void delete(Project project) {
	}
}
