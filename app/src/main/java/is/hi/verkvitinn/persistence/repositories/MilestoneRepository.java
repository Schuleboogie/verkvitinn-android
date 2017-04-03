package is.hi.verkvitinn.persistence.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import is.hi.verkvitinn.persistence.entities.Milestone;
import is.hi.verkvitinn.persistence.entities.Project;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MilestoneRepository {
	public static Milestone save(Milestone newMilestone, Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase writeDB = dbHelper.getWritableDatabase();

		// Date conversion
		String timestamp = "";
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS.SSS");
		if(newMilestone.getTimestamp()!=null){
			timestamp = formatter.format(newMilestone.getTimestamp());
		}

		ContentValues milestone = new ContentValues();
		milestone.put("projectId", newMilestone.getProjectId());
		milestone.put("timestamp", timestamp);
		milestone.put("title", newMilestone.getTitle());
		writeDB.insert("milestones", null, milestone);

		return newMilestone;
	}

	public static List<Milestone> findByProjectId(Long projectId, Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase readDB = dbHelper.getReadableDatabase();
		String selection = "projectId = ?";
		String[] selectionArgs = { String.valueOf(projectId) };
		Cursor results = readDB.query("milestones", null, selection, selectionArgs, null, null, null);
		List<Milestone> foundMilestones = new ArrayList<Milestone>();
		if (results.getCount() > 0) {
			Milestone foundMilestone = null;
			results.moveToFirst();
			while (results.isAfterLast() == false) {
				// Conversions
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS.SSS");
				Date timestamp = null;
				try {
					timestamp = formatter.parse(results.getString(2));
				}
				catch (ParseException e) {
					// He he
				}
				foundMilestone = new Milestone(Long.valueOf(results.getString(1)), timestamp, results.getString(3));
				foundMilestone.setId(Long.valueOf(results.getInt(0)));
				foundMilestones.add(foundMilestone);
				results.moveToNext();
			}
			results.close();
			return foundMilestones;
		}
		else return null;
	}
}
