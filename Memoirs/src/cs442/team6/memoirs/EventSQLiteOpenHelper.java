package cs442.team6.memoirs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class EventSQLiteOpenHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "event_db";
	public static final String TABLE_NAME = "day_event";
	public static final String TABLE_LOCATION = "event_location";	
	public static final String COL_EVENT_ID = "event_id";
	public static final String COL_EVENT_DATE = "event_date";
	public static final String COL_EVENT_TIME = "event_time";
	public static final String COL_EVENT_TITLE = "event_title";
	public static final String COL_EVENT_DESCRIPTION = "event_description";

	public static final String LOCATION_ADDRESS = "location_address";
	public static final String LOCATION_LATITUDE = "location_latitude";
	public static final String LOCATION_LONGITUDE = "location_longitude";

	public EventSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Query to create events table.
		String query = "CREATE TABLE "+TABLE_NAME+"("+COL_EVENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
													 +COL_EVENT_DATE+" TEXT, "
													 +COL_EVENT_TIME+" TEXT, "
												     +COL_EVENT_TITLE+" TEXT, "
													 +COL_EVENT_DESCRIPTION+" TEXT)";
		
		String query_loc = "CREATE TABLE "+TABLE_LOCATION+"("+COL_EVENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				 										     +COL_EVENT_DATE+" TEXT, "
				 										     +LOCATION_ADDRESS+" TEXT, "
				 										     +LOCATION_LATITUDE+" DOUBLE, "
				 										     +LOCATION_LONGITUDE+" DOUBLE)";
		
		db.execSQL(query);
		db.execSQL(query_loc);
		
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
