package cs442.team6.memoirs;

import static cs442.team6.memoirs.EventSQLiteOpenHelper.TABLE_NAME;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_DATE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_TITLE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_DESCRIPTION;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditDayActivity extends Activity {
	
	EditText edtTitle, edtDescription;
	Button btnSave, btnBack, btnLocation, btnCamera, btnGallery;
	String eventDay, eTitle, eDescription; 
	SQLiteDatabase eventDB;
	String eventTitle=""; 
	String eventDescription="";
	int flag;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_day_view);
        
       // CalendarAdapter.flag = true;
        edtTitle = (EditText)findViewById(R.id.edtTitle);
        edtDescription = (EditText)findViewById(R.id.edtDescription);
        
        Intent i = getIntent();
        
        flag = i.getIntExtra("flag",1);
        eventDay = i.getStringExtra("day");
        
        if(flag == 0)
        {
        	edtTitle.setText("");
            edtDescription.setText("");
        }
        
        else if(flag == 1)
        {
        	eTitle = i.getStringExtra("eTitle");
            eDescription = i.getStringExtra("eDescription");
           
            
            edtTitle.setHint(eventDay);
            edtTitle.setText(eTitle);
            edtDescription.setText(eDescription);
        }
        
		
        btnSave = (Button)findViewById(R.id.btnSave);
        btnBack = (Button)findViewById(R.id.btnBack1);
        btnLocation = (Button)findViewById(R.id.btnLocation);
        btnCamera = (Button)findViewById(R.id.btnCamera);
        btnGallery = (Button)findViewById(R.id.btnGallery);
        
        //Getting Instance of Database to store orders
        eventDB = new EventSQLiteOpenHelper(EditDayActivity.this, null, null, 1).getWritableDatabase();
        
        btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				edtTitle.setText("");
	            edtDescription.setText("");
	            
				EditDayActivity.this.finish();
			}
		});
        
        btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(EditDayActivity.this, CameraActivity.class);
				startActivity(i);
			}
		});
        
        btnGallery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
				startActivity(i);
			}
		});
        
        btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(flag==1)
				{
					eventTitle = edtTitle.getText().toString();
				    eventDescription = edtDescription.getText().toString();
				    
					int eventStatus = addEvent(eventDay, eventTitle, eventDescription);	
					showDBResultToast(eventStatus);
				    
					Log.d("Date", eventDay);
					Log.d("Title", eventTitle);
					Log.d("Description", eventDescription);
					
					EditDayActivity.this.finish();
					
					Intent i = new Intent(EditDayActivity.this, ListViewActivity.class);
					startActivity(i);
				}
				
			}
			
			
			
			//Preparing a Content Value/Row for an event & inserting it in event_day table
			int addEvent(String date, String title, String description){
				ContentValues cv = new ContentValues();
				
				cv.put(COL_EVENT_DATE, date);
				cv.put(COL_EVENT_TITLE, title);
				cv.put(COL_EVENT_DESCRIPTION, description);
				
				try{
					eventDB.insert(TABLE_NAME, null, cv);
				}catch(Exception e){
					return 0;
				}
				return 1;
			}
			
			void showDBResultToast(int eventStatus){
				Toast.makeText(EditDayActivity.this, eventStatus==1?"Event Added Successfully":"Event Not Accepted", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
