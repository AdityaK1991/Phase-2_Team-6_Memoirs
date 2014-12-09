package cs442.team6.memoirs;

import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_DATE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_TIME;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_DESCRIPTION;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_TITLE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.TABLE_NAME;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.rtp.RtpStream;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarAdapter extends BaseAdapter implements OnClickListener {
	
	private final Context _context;
	private final List<String> list;
	private static final String tag = "CalendarAdapter";	
	private static final int DAY_OFFSET = 1;
	private final String[] weekdays = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	private final String[] months = { "January", "February", "March", "April", "May", "June", "July", 
									  "August", "September", "October", "November", "December" };
	private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private int daysInMonth;
	private int currentDayOfMonth;
	private int currentWeekDay;
	private Button gridCell;
	private TextView num_events_per_day;
	private final HashMap<String, Integer> eventsPerMonthMap;
	public static int flag = 0;
	SQLiteDatabase eventDB;
	Cursor cursor;
	
	// Days in Current Month
			public CalendarAdapter(Context context, int textViewResourceId, int month, int year) {
				super();
				this._context = context;
				this.list = new ArrayList<String>();
				
				// Get calendar instance
				Calendar calendar = Calendar.getInstance();
				setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
				setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
				
				Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
				Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
				Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

				// Print Month
				printMonth(month, year);

				// Find Number of Events
				eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
			}
			
			
			private void printMonth(int mm, int yy) {
				Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
				int trailingSpaces = 0;
				int daysInPrevMonth = 0;
				int prevMonth = 0;
				int prevYear = 0;
				int nextMonth = 0;
				int nextYear = 0;

				int currentMonth = mm - 1;
				String currentMonthName = getMonthAsString(currentMonth);
				daysInMonth = getNumberOfDaysOfMonth(currentMonth);

				Log.d(tag, "Current Month: " + " " + currentMonthName + " having " + daysInMonth + " days.");

				GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
				Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

				if (currentMonth == 11) {
					prevMonth = currentMonth - 1;
					daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
					nextMonth = 0;
					prevYear = yy;
					nextYear = yy + 1;
					Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + nextMonth + " NextYear: " + nextYear);
				
				} else if (currentMonth == 0) {
					prevMonth = 11;
					prevYear = yy - 1;
					nextYear = yy;
					daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
					nextMonth = 1;
					Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + nextMonth + " NextYear: " + nextYear);
				
				} else {
					prevMonth = currentMonth - 1;
					nextMonth = currentMonth + 1;
					nextYear = yy;
					prevYear = yy;
					daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
					Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:"
							+ prevMonth + " NextMonth: " + nextMonth
							+ " NextYear: " + nextYear);
				}

				int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
				trailingSpaces = currentWeekDay;

				if (cal.isLeapYear(cal.get(Calendar.YEAR)))
					if (mm == 2)
						++daysInMonth;
					else if (mm == 3)
						++daysInPrevMonth;

				// Trailing Month days
				for (int i = 0; i < trailingSpaces; i++) {
					Log.d(tag, "PREV MONTH:= " + prevMonth + " => " + getMonthAsString(prevMonth) + " " 
											   + String.valueOf((daysInPrevMonth
											   - trailingSpaces + DAY_OFFSET)
											   + i));
					list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i)
							+ "-:"
							+ "-"
							+ getMonthAsString(prevMonth)
							+ "-"
							+ prevYear);
				}

				// Current Month Days
				for (int i = 1; i <= daysInMonth; i++) {
					Log.d(currentMonthName, String.valueOf(i) + " " + getMonthAsString(currentMonth) + " " + yy);
					
					if (i == getCurrentDayOfMonth()) {
						list.add(String.valueOf(i) + "->" + "-" + getMonthAsString(currentMonth) + "-" + yy);
					} else {
						list.add(String.valueOf(i) + "--" + "-" + getMonthAsString(currentMonth) + "-" + yy);
					}
				}

				// Leading Month days
				for (int i = 0; i < list.size() % 7; i++) {
					Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
					list.add(String.valueOf(i + 1) + "-:" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
				}
			}

			
			private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
					int month) {
				HashMap<String, Integer> map = new HashMap<String, Integer>();

				return map;
			}

			String eTime="";
			String eTitle="";
		    String eDescription="";
			
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				View row = convertView;
				if (row == null) {
					
					LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					row = inflater.inflate(R.layout.grid_cell_calendar, parent, false);
				      
					gridCell = (Button) row.findViewById(R.id.calendar_day_gridcell);
					
					gridCell.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
								// Read from database
								eventDB = new EventSQLiteOpenHelper(v.getContext(), null, null, 1).getReadableDatabase();
								
								// Query the database
								cursor = eventDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_EVENT_DATE + " =?", new String[]{getItem(position)});
								if(cursor.getCount()>0)
								{
									flag = 1;
									
										Toast.makeText(v.getContext() ,"Clicked "+v.getTag(), Toast.LENGTH_SHORT).show();
										
									    eventDB = new EventSQLiteOpenHelper(v.getContext(), null, null, 1).getReadableDatabase();
										
										cursor = eventDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_EVENT_DATE + " =?", new String[]{getItem(position)});
										
										Log.d("Cursor f=1", String.valueOf(cursor));
										
										 try{
									        	if(!cursor.isAfterLast())
									    			cursor.moveToFirst();
									    		
									    		do{
									    			eTime = cursor.getString(cursor.getColumnIndex(COL_EVENT_TIME));
									    			eTitle =  cursor.getString(cursor.getColumnIndex(COL_EVENT_TITLE));
									    			eDescription =  cursor.getString(cursor.getColumnIndex(COL_EVENT_DESCRIPTION));
									            	Toast.makeText(v.getContext(), "Title: "+eTitle, Toast.LENGTH_SHORT).show();

									    			cursor.moveToNext();
									    		}while(!cursor.isAfterLast());
									    		
									    		//cursor.close();  
										 }
										 catch(Exception e)
									        {
									        	Log.d("Cursor error", "No data received");
									        }
										Intent i = new Intent(v.getContext(), EditDayActivity.class);
										i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										
										i.putExtra("flag", flag);
										i.putExtra("day", getItem(position));
										i.putExtra("time", eTime);
										i.putExtra("eTitle", eTitle);
										i.putExtra("eDescription", eDescription);
										v.getContext().startActivity(i);	
									}
								
								
								else{
									flag = 0;
									Intent i = new Intent(v.getContext(), EditDayActivity.class);
									i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									
									i.putExtra("flag", flag);
									i.putExtra("day", getItem(position));
									v.getContext().startActivity(i);
									Log.d("Flag", String.valueOf(flag));
								}
						}
				});
			}
					
				Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
				String[] day_color = list.get(position).split("-");
				String day = day_color[0];
				String themonth = day_color[2];
				String theyear = day_color[3];
				if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
					if (eventsPerMonthMap.containsKey(day)) {
						num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
						Integer numEvents = (Integer) eventsPerMonthMap.get(day);
						num_events_per_day.setText(numEvents.toString());
					}
				}

				// Set the Day GridCell
				gridCell.setText(day);
				gridCell.setTag(day + "-" + themonth + "-" + theyear);
				Log.d(tag, "Setting GridCell " + day + "-" + themonth + "-"
						+ theyear);

				if (day_color[1].equals(":")) {
					gridCell.setTextColor(_context.getResources().getColor(android.R.color.darker_gray));
				}
				if (day_color[1].equals(">")) {
					gridCell.setTextColor(_context.getResources().getColor(android.R.color.holo_blue_dark));
				}
				if (day_color[1].equals("-")) {
					gridCell.setTextColor(_context.getResources().getColor(android.R.color.black));
				}
				return row;
			}

			
			private String getMonthAsString(int i) {
				return months[i];
			}

			private String getWeekDayAsString(int i) {
				return weekdays[i];
			}

			private int getNumberOfDaysOfMonth(int i) {
				return daysOfMonth[i];
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}
			
			public String getItem(int position) {
				return list.get(position);
			}

			@Override
			public int getCount() {
				return list.size();
			}
			public int getCurrentDayOfMonth() {
				return currentDayOfMonth;
			}

			private void setCurrentDayOfMonth(int currentDayOfMonth) {
				this.currentDayOfMonth = currentDayOfMonth;
			}

			public void setCurrentWeekDay(int currentWeekDay) {
				this.currentWeekDay = currentWeekDay;
			}

			public int getCurrentWeekDay() {
				return currentWeekDay;
			}

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
	
		}
