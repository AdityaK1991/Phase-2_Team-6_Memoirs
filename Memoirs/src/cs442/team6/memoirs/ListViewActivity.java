package cs442.team6.memoirs;

import static cs442.team6.memoirs.EventSQLiteOpenHelper.TABLE_NAME;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_DATE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_TITLE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_DESCRIPTION;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewActivity extends Activity {
	
	ListView lstView;
	Button btnCalendar, btnAdd, btnExit;
	TextView txtView;
	
	SQLiteDatabase eventDB;
	Cursor cursor;
	ArrayList<Event> events;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.list_view_main);

	        btnCalendar = (Button)findViewById(R.id.btnCalendar);
	        btnAdd = (Button)findViewById(R.id.btnAdd);
	        btnExit = (Button)findViewById(R.id.btnExit1);
	        txtView = (TextView)findViewById(R.id.txtView);
	        
	        eventDB = new EventSQLiteOpenHelper(ListViewActivity.this, null, null, 1).getReadableDatabase();
			
			events = new ArrayList<Event>();
			
			cursor = eventDB.query(TABLE_NAME, new String[]{COL_EVENT_DATE, COL_EVENT_TITLE, COL_EVENT_DESCRIPTION}, null, null, null, null, null);
			  
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
	    			events.add(0, new Event(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
	    			cursor.moveToNext();
	    		}while(!cursor.isAfterLast());
	        }
	        catch(Exception e)
	        {
	        	Log.d("Cursor error", "No data");
	        }
	    		EventAdapter adapter = new EventAdapter(getApplicationContext(), events);
	    		
	    		lstView.setAdapter(adapter);
	    	
	    		
	    		lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    			@Override
	    			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {	
	    				
	    				Toast.makeText(getApplicationContext(),"Clicked "+position, Toast.LENGTH_LONG).show();
	    				
	    				Intent i = new Intent(ListViewActivity.this, EventViewActivity.class);
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
	        
	        btnAdd.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(ListViewActivity.this, CalenderViewActivity.class);
					startActivity(i);
					
				}
			});
	        
	 }
}
