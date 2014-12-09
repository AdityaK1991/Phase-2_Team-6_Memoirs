package cs442.team6.memoirs;

import java.util.Locale;  
  
import android.app.ActionBar;
import android.app.Activity;  
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;  
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CalendarView;
import android.widget.GridView;  
import android.widget.TextView;  
import android.widget.Toast;

import java.util.Calendar;
import android.widget.Button;
import android.widget.ImageView;

public class CalenderViewActivity extends Activity implements OnClickListener {
	
	TextView currentMonth, txtCalendarView;
	ImageView prevMonth;
	ImageView nextMonth;
	GridView calendarView;
	Calendar _calendar;
	CalendarAdapter adapter;
	Button btnBack;
	Typeface font;

	private static final String dateTemplate = "MMMM yyyy";
	int month, year;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.calender_view);
	        
	        ActionBar actionBar = getActionBar();
			actionBar.hide();
	        
			txtCalendarView = (TextView)findViewById(R.id.txtCalendarView);
			font = Typeface.createFromAsset(getAssets(), "MP.ttf");
			txtCalendarView.setTypeface(font);
			
	        _calendar = Calendar.getInstance(Locale.getDefault());
			month = _calendar.get(Calendar.MONTH) + 1;
			year = _calendar.get(Calendar.YEAR);
			Log.d("Calendar", "Calendar Instance:= " + "Month: " + month + " " + "Year: "
					+ year);

			prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
			prevMonth.setOnClickListener(this);

			currentMonth = (TextView) this.findViewById(R.id.currentMonth);
			currentMonth.setText(DateFormat.format(dateTemplate,_calendar.getTime()));

			nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
			nextMonth.setOnClickListener(this);

			calendarView = (GridView) this.findViewById(R.id.calendar);
			
			adapter = new CalendarAdapter(getApplicationContext(),
					R.id.calendar_day_gridcell, month, year);
			adapter.notifyDataSetChanged();
			
			calendarView.setAdapter(adapter);
			
			btnBack = (Button)findViewById(R.id.btnBack);
			
			btnBack.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					CalenderViewActivity.this.finish();
				}
			});
	 }
	 
	
	 private void setCalendarAdapterToDate(int month, int year) {
			adapter = new CalendarAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year);
			_calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
			currentMonth.setText(DateFormat.format(dateTemplate,
					_calendar.getTime()));
			adapter.notifyDataSetChanged();
			calendarView.setAdapter(adapter);
			
		}
	 
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == prevMonth) {
			if (month <= 1) {
				month = 12;
				year--;
			} else {
				month--;
			}
			Log.d("Calendar", "Setting Prev Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
			setCalendarAdapterToDate(month, year);
		}
		if (v == nextMonth) {
			if (month > 11) {
				month = 1;
				year++;
			} else {
				month++;
			}
			Log.d("Calendar", "Setting Next Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
			setCalendarAdapterToDate(month, year);
		}
	}

}


/*cal = (CalendarView) findViewById(R.id.calendarView);

cal.setOnDateChangeListener(new OnDateChangeListener() {
	
public void onSelectedDayChange(CalendarView view, int year, int month,
		int dayOfMonth) {
	// TODO Auto-generated method stub
	
	Toast.makeText(getBaseContext(),"Selected Date is\n\n"
		+dayOfMonth+" : "+month+" : "+year , 
		Toast.LENGTH_LONG).show();
	
	Intent i = new Intent(CalenderViewActivity.this, EditDayActivity.class);
	startActivity(i);
}
});


long startMillis = 0;
long endMillis = 0;
Calendar beginTime = Calendar.getInstance();
beginTime.set(2012, 9, 14, 7, 30);
startMillis = beginTime.getTimeInMillis();
Calendar endTime = Calendar.getInstance();
endTime.set(2012, 9, 14, 8, 45);
endMillis = endTime.getTimeInMillis();

// Insert Event
ContentResolver cr = getContentResolver();
ContentValues values = new ContentValues();
TimeZone timeZone = TimeZone.getDefault();
values.put(CalendarContract.Events.DTSTART, startMillis);
values.put(CalendarContract.Events.DTEND, endMillis);
values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
values.put(CalendarContract.Events.TITLE, "Walk The Dog");
values.put(CalendarContract.Events.DESCRIPTION, "My dog is bored, so we're going on a really long walk!");
values.put(CalendarContract.Events.CALENDAR_ID, 3);
Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

// Retrieve ID for new event
String eventID = uri.getLastPathSegment(); */

