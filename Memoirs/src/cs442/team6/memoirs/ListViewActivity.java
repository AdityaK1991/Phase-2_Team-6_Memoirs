package cs442.team6.memoirs;

import static cs442.team6.memoirs.EventSQLiteOpenHelper.TABLE_NAME;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.TABLE_LOCATION;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_ID;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_DATE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_TIME;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_TITLE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_DESCRIPTION;

import static cs442.team6.memoirs.EventSQLiteOpenHelper.LOCATION_ADDRESS;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.LOCATION_LATITUDE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.LOCATION_LONGITUDE;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewActivity extends Activity {
	
	ListView lstView;
	Button btnCalendar, btnAdd, btnExit;
	TextView txtView, txtMyMemories;
	int flag = 2;
	SQLiteDatabase eventDB;
	Cursor cursor;
	ArrayList<Event> events;
	Typeface font;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.list_view_main);

	        ActionBar actionBar = getActionBar();
			actionBar.hide();
			
	        btnCalendar = (Button)findViewById(R.id.btnCalendar);
	        btnExit = (Button)findViewById(R.id.btnExit1);
	        txtView = (TextView)findViewById(R.id.txtView);
	        txtMyMemories = (TextView)findViewById(R.id.txtMyMermories);
	       
	        font = Typeface.createFromAsset(getAssets(), "MP.ttf");
			txtMyMemories.setTypeface(font);
	        
			eventDB = new EventSQLiteOpenHelper(ListViewActivity.this, null, null, 1).getReadableDatabase();
			
			events = new ArrayList<Event>();
			
			cursor = eventDB.query(TABLE_NAME, new String[]{COL_EVENT_DATE, COL_EVENT_TIME, COL_EVENT_TITLE, COL_EVENT_DESCRIPTION}, null, null, null, null, null);
			  
	        lstView = (ListView)findViewById(R.id.lstView);
	        
	        if(lstView.getCount()==0)
	        {
	        	txtView.setVisibility(View.VISIBLE);
	        }
	        
	        txtView.setVisibility(View.INVISIBLE);	  
	        
	        try{
	        	if(!cursor.isAfterLast())
	    			cursor.moveToFirst();
	    		
	    		do{
	    			events.add(new Event(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
	    			cursor.moveToNext();
	    		}while(!cursor.isAfterLast());
	        }
	        catch(Exception e)
	        {
	        	Log.d("Cursor error", "No data");
	        }
	    		final EventAdapter adapter = new EventAdapter(getApplicationContext(), events);
	    		
	    		lstView.setAdapter(adapter);
	    	
	    		
	    		lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    			@Override
	    			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {	
	    				
	    				Toast.makeText(getApplicationContext(),"Clicked "+position, Toast.LENGTH_LONG).show();
	    				
	    				 eventDB = new EventSQLiteOpenHelper(ListViewActivity.this, null, null, 1).getReadableDatabase();

	    				Cursor cursor = eventDB.query(TABLE_NAME, new String[] {COL_EVENT_DATE, COL_EVENT_TIME, COL_EVENT_TITLE, COL_EVENT_DESCRIPTION}, null, null, null, null, null);
	    				cursor.moveToPosition(position);
	    				
	    				Cursor cursorLoc = eventDB.query(TABLE_LOCATION, new String[] {COL_EVENT_DATE, LOCATION_ADDRESS, LOCATION_LATITUDE, LOCATION_LONGITUDE}, null, null, null, null, null);
	    				cursorLoc.moveToPosition(position);
	    				
	    				Intent i = new Intent(ListViewActivity.this, EventViewActivity.class);
	    				i.putExtra(COL_EVENT_ID, position);
	    				i.putExtra(COL_EVENT_DATE, cursor.getString((cursor.getColumnIndexOrThrow(COL_EVENT_DATE))));
	    				i.putExtra(COL_EVENT_TIME, cursor.getString((cursor.getColumnIndexOrThrow(COL_EVENT_TIME))));
	    				i.putExtra(COL_EVENT_TITLE, cursor.getString((cursor.getColumnIndexOrThrow(COL_EVENT_TITLE))));
	    				i.putExtra(COL_EVENT_DESCRIPTION, cursor.getString((cursor.getColumnIndexOrThrow(COL_EVENT_DESCRIPTION))));

	    				i.putExtra(LOCATION_ADDRESS, cursorLoc.getString((cursorLoc.getColumnIndexOrThrow(LOCATION_ADDRESS))));
	    				i.putExtra(LOCATION_LATITUDE, cursorLoc.getString((cursorLoc.getColumnIndexOrThrow(LOCATION_LATITUDE))));
	    				i.putExtra(LOCATION_LONGITUDE, cursorLoc.getString((cursorLoc.getColumnIndexOrThrow(LOCATION_LONGITUDE))));

	    				startActivity(i);
	    			       
	    			}
	    		}); 
	    		
	        btnCalendar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(ListViewActivity.this, CalenderViewActivity.class);
					startActivity(i);
				}
			});
	        
	        
	        lstView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View v,
						final int position, long arg3) {
					// TODO Auto-generated method stub
					
					final AlertDialog.Builder b = new AlertDialog.Builder(
                            ListViewActivity.this);
                    b.setIcon(android.R.drawable.ic_dialog_alert);
                    b.setMessage("Are you sure you want to remove this event?");
                    b.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int button) {
                                	eventDB.delete(TABLE_NAME, COL_EVENT_DATE + "=?" , new String[]{events.get(position).getEventDate()});
                                	events.remove(position);
                                	adapter.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(),
                                            "Yes", Toast.LENGTH_LONG)
                                            .show();
                                }
                            });
                    b.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int whichButton) {
                                    dialog.cancel();
                                }
                            });

                    b.show();
					
					return true;
				}
			});
	        

	        btnExit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ListViewActivity.this.finish();	
				}
			});
	        
	 }
	 
	 @Override 
		public void onBackPressed() { 
		 	System.exit(0);
			//super.onBackPressed(); 
			} 
}
