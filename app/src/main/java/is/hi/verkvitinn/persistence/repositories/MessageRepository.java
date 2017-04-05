package is.hi.verkvitinn.persistence.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import is.hi.verkvitinn.persistence.entities.Message;
import is.hi.verkvitinn.persistence.entities.Message;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MessageRepository  {
	public static Message save(Message newMessage, Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase writeDB = dbHelper.getWritableDatabase();

		// Date conversion
		String timestamp = "";
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS.SSS");
		if(newMessage.getTimestamp()!=null){
			timestamp = formatter.format(newMessage.getTimestamp());
		}

		ContentValues message = new ContentValues();
		message.put("projectId", newMessage.getProjectId());
		message.put("timestamp", timestamp);
		message.put("author", newMessage.getAuthor());
		message.put("admin", newMessage.getAdmin());
		message.put("headworker", newMessage.getHeadWorker());
		message.put("content", newMessage.getContent());
		writeDB.insert("messages", null, message);

		return newMessage;
	}

	public static List<Message> findByProjectId(Long projectId, Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase readDB = dbHelper.getReadableDatabase();
		String selection = "projectId = ?";
		String[] selectionArgs = { String.valueOf(projectId) };
		Cursor results = readDB.query("messages", null, selection, selectionArgs, null, null, null);
		List<Message> foundMessages = new ArrayList<Message>();
		if (results.getCount() > 0) {
			Message foundMessage = null;
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
				foundMessage = new Message(Long.valueOf(results.getString(1)), timestamp, results.getString(3), (results.getInt(4) != 0), (results.getInt(5) != 0), results.getString(6));
				foundMessage.setId(Long.valueOf(results.getInt(0)));
				foundMessages.add(foundMessage);
				results.moveToNext();
			}
			results.close();
			return foundMessages;
		}
		else return null;
	}
}
