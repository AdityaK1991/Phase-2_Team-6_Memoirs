package cs442.team6.memoirs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class EventSQLiteOpenHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME="event_db";
	public static final String TABLE_NAME="day_event";
	public static final String COL_EVENT_ID = "event_id";
	public static final String COL_EVENT_DATE = "event_date";
	public static final String COL_EVENT_TITLE = "event_title";
	public static final String COL_EVENT_DESCRIPTION = "event_description";

	public EventSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Query to create events table.
		String query = "CREATE TABLE "+TABLE_NAME+"("+COL_EVENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
													 +COL_EVENT_DATE+" TEXT, "
												     +COL_EVENT_TITLE+" TEXT, "
												     +COL_EVENT_DESCRIPTION+" TEXT)";
		
		//CREATE TABLE day_event(event_id INTEGER PRIMARY KEY AUTOINCREMENT, event_title TEXT, event_description TEXT);
		
		db.execSQL(query);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
