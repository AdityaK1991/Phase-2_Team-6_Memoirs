package cs442.team6.memoirs;

import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_DATE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_TIME;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_DESCRIPTION;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_ID;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_TITLE;

import static cs442.team6.memoirs.EventSQLiteOpenHelper.LOCATION_ADDRESS;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.LOCATION_LATITUDE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.LOCATION_LONGITUDE;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventViewActivity extends Activity {
	
	TextView txtTitle, txtEDate, txtETime, txtDescription, txtLocAddress, txtAboutEvent;
	Button btnBack2, btnEdit;
	String eventDay, eventTime, eventTitle, eventDescription, locAddress, locLatitude, locLongitude;
	Typeface font;
	int flag;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_text_view);
	
        ActionBar actionBar = getActionBar();
		actionBar.hide();
		
        txtEDate = (TextView)findViewById(R.id.txtEDate);
        //txtETime = (TextView)findViewById(R.id.txtETime);
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtAboutEvent = (TextView)findViewById(R.id.txtAboutEvent);
        
        font = Typeface.createFromAsset(getAssets(), "MP.ttf");
		txtAboutEvent.setTypeface(font);
        
        txtDescription = (TextView)findViewById(R.id.txtDescription);
        //txtDescription.setTypeface(font);
        
        txtLocAddress = (TextView)findViewById(R.id.txtLocAddress);
        
        btnBack2 = (Button)findViewById(R.id.btnBack2);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        
       
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString(COL_EVENT_ID);
           // eventTime = extras.getString(COL_EVENT_TIME);
            eventDay = extras.getString(COL_EVENT_DATE);
            eventTitle = extras.getString(COL_EVENT_TITLE);
            eventDescription = extras.getString(COL_EVENT_DESCRIPTION);
            
            locAddress = extras.getString(LOCATION_ADDRESS);
            locLatitude = extras.getString(LOCATION_LATITUDE);
            locLongitude = extras.getString(LOCATION_LONGITUDE);
            
            Toast.makeText(getApplicationContext(), "Lat:"+locLatitude + " Long:" +locLongitude + "Address:"+locAddress, Toast.LENGTH_SHORT).show();
            //mRowId = extras.getLong(DiaryDbAdapter.KEY_ROWID);
            if (eventDay != null) {
                txtEDate.setText(eventDay);
              }
            
            if (eventTitle != null) {
              txtTitle.setText(eventTitle);
            }
            
            if (eventDescription != null) {
              txtDescription.setText(eventDescription);
            }
            
            if (locAddress != null) {
            	txtLocAddress.setVisibility(View.VISIBLE);
                txtLocAddress.setText(txtLocAddress.getText()+locAddress);
              }
            
          }

         flag = 1;
         
		 btnEdit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
						Intent i = new Intent(EventViewActivity.this, EditDayActivity.class);
						i.putExtra("flag", flag);
						i.putExtra("day", eventDay);
						i.putExtra("eTitle", eventTitle);
						i.putExtra("eDescription", eventDescription);
						i.putExtra("locAddress", locAddress);
						startActivity(i);
						
						EventViewActivity.this.finish();
					}
				});
        
        btnBack2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EventViewActivity.this.finish();
			}
		});

	}
}
