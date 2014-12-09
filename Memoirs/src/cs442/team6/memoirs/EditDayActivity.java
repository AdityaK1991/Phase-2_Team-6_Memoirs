package cs442.team6.memoirs;

import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_ID;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.TABLE_NAME;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.TABLE_LOCATION;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_DATE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_TIME;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_TITLE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.COL_EVENT_DESCRIPTION;

import static cs442.team6.memoirs.EventSQLiteOpenHelper.LOCATION_ADDRESS;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.LOCATION_LATITUDE;
import static cs442.team6.memoirs.EventSQLiteOpenHelper.LOCATION_LONGITUDE;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditDayActivity extends Activity {
	
	TextView txtDayOverview, txtTime, txtLocation;
	EditText edtTitle, edtDescription;
	Button btnSave, btnBack, btnLocation, btnCamera, btnGallery, btnMapLocation;
	String eventDay, eventTime, eTitle, eDescription; 
	SQLiteDatabase eventDB;
	String eventTitle=""; 
	String eventDescription="";
	String locAddress = "";
	Typeface font;
	int flag;
	
	//String locationLatitude = Double.toString(LOCATION_LATITUDE);
	//String locationLongitude = Double.toString(LOCATION_LONGITUDE);

	static double latitude;
	static double longitude;
	static String address = "";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_day_view);
        
        ActionBar actionBar = getActionBar();
		actionBar.hide();
        
       // CalendarAdapter.flag = true;
        txtLocation = (TextView)findViewById(R.id.txtLocation);
        txtDayOverview = (TextView)findViewById(R.id.txtDayOverview);
        txtTime = (TextView)findViewById(R.id.txtTime);
        
        edtTitle = (EditText)findViewById(R.id.edtTitle);
        edtDescription = (EditText)findViewById(R.id.edtDescription);
        
        font = Typeface.createFromAsset(getAssets(), "MP.ttf");
		txtDayOverview.setTypeface(font);
		
        Bundle extras = getIntent().getExtras();
        
        
        if (extras != null) {
        	
        	flag = extras.getInt("flag");
        	Toast.makeText(getApplicationContext(), "Title: "+eventTitle, Toast.LENGTH_SHORT).show();
        	eventDay = extras.getString("day");
        	
        	if(eventTime != null)
        	eventTime = extras.getString("time");

        	eventTitle = extras.getString("eTitle");
        	eventDescription = extras.getString("eDescription");
        	locAddress = extras.getString("locAddress");
        }

        
        if(flag == 1){
            	try{
	                edtTitle.setText(eventTitle);
	                edtDescription.setText(eventDescription);
	                txtLocation.setText(locAddress);
	            	} catch(Exception e) {
	            	e.printStackTrace();
	            }
        }
        
       /* else if(flag == 2){
        	eventDay = extras.getString("currentDay");
        	edtTitle.setText("");
        	edtDescription.setText("");
        }*/
        
        else if(flag == 0){
        	edtTitle.setText("");
        	edtDescription.setText("");
        	txtLocation.setText("");
        }
		
        
        btnSave = (Button)findViewById(R.id.btnSave);
        btnLocation = (Button)findViewById(R.id.btnLocation);
        btnCamera = (Button)findViewById(R.id.btnCamera);
        btnGallery = (Button)findViewById(R.id.btnGallery);
        btnMapLocation = (Button)findViewById(R.id.btnMapLocation);
        
        //Getting Instance of Database to store orders
        eventDB = new EventSQLiteOpenHelper(EditDayActivity.this, null, null, 1).getWritableDatabase();
        
        
        btnMapLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent i = new Intent(EditDayActivity.this, GoogleMapsActivity.class);
			startActivity(i);
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
				
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/images/media")); 
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(intent);
			}
		});
        
        
        btnLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				GPSService mGPSService = new GPSService(getApplicationContext());
				mGPSService.getLocation();

				if (mGPSService.isLocationAvailable == false) {

					// Here you can ask the user to try again, using return; for that
					Toast.makeText(getApplicationContext(), "Your location is not available, please try again.", Toast.LENGTH_SHORT).show();
					return;

					// Or you can continue without getting the location, remove the return; above and uncomment the line given below
					// address = "Location not available";
				} else {

					// Getting location co-ordinates
					latitude = mGPSService.getLatitude();
					longitude = mGPSService.getLongitude();
					//Toast.makeText(getApplicationContext(), "Latitude:" + latitude + " | Longitude: " + longitude, Toast.LENGTH_LONG).show();

					address = mGPSService.getLocationAddress();
					
					txtLocation.setVisibility(View.VISIBLE);
					txtLocation.setText("Address: " + address);
				}

				//Toast.makeText(getApplicationContext(), "Your address is: " + address, Toast.LENGTH_SHORT).show();
				
				// make sure you close the gps after using it. Save user's battery power
				mGPSService.closeGPS();
			}
		});
        
        btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			      	GregorianCalendar date = new GregorianCalendar();
			      	eventTime = (date.get(Calendar.HOUR) + ":" + date.get(Calendar.MINUTE));
			      	
			      	// Update event if it already exists
			      	if(flag==1)
			      	{
			      		eventTitle = edtTitle.getText().toString();
					    eventDescription = edtDescription.getText().toString();
					    
					   
							int eventStatus = updateEvent(eventDay, eventTime, eventTitle, eventDescription);	
							updateDBResultToast(eventStatus);
							
							int eventStatusLoc = addEventLocation(eventDay, address, latitude, longitude);	
							updateLocResultToast(eventStatusLoc);
							
							EditDayActivity.this.finish();
							
							Intent i = new Intent(EditDayActivity.this, ListViewActivity.class);
							startActivity(i);
					   }
			      	
			      	/*else if(flag==2) {
				      	//txtTime.setText(eventTime);
				      	eventTitle = edtTitle.getText().toString();
					    eventDescription = edtDescription.getText().toString();
					    
						int eventStatus = addEvent(eventDay, eventTime, eventTitle, eventDescription);	
						showDBResultToast(eventStatus);
					    
						Log.d("Time", eventTime);
						Log.d("Date", eventDay);
						Log.d("Title", eventTitle);
						Log.d("Description", eventDescription);
						
						EditDayActivity.this.finish();
						
						Intent i = new Intent(EditDayActivity.this, ListViewActivity.class);
						startActivity(i);
				      	}*/
			      	
			      	// Create a new event
			      	else if(flag==0) {
			      	//txtTime.setText(eventTime);
			      	eventTitle = edtTitle.getText().toString();
				    eventDescription = edtDescription.getText().toString();
				    
				    if(!eventTitle.equals("") && !eventDescription.equals("") && !address.equals(""))
				    {
				    
					int eventStatus = addEvent(eventDay, eventTime, eventTitle, eventDescription);	
					showDBResultToast(eventStatus);
				    
					int eventStatusLoc = addEventLocation(eventDay, address, latitude, longitude);	
					showLocResultToast(eventStatusLoc);
					
					Log.d("Time", eventTime);
					Log.d("Date", eventDay);
					Log.d("Title", eventTitle);
					Log.d("Description", eventDescription);
					Log.d("Address", address);
					Log.d("Latitude", Double.toString(latitude));
					Log.d("Longitude", Double.toString(longitude));
					
					EditDayActivity.this.finish();
					
					Intent i = new Intent(EditDayActivity.this, ListViewActivity.class);
					startActivity(i);
				    }
				    else 
				    	Toast.makeText(getApplicationContext(), "Please enter Title, Description & Find Location!", Toast.LENGTH_SHORT).show();
			    }
			}
			
			
			
			//Preparing a Content Value/Row for an event & inserting it in event_day table
			int addEvent(String date, String time, String title, String description){
				ContentValues cv = new ContentValues();
				
				cv.put(COL_EVENT_TIME, time);
				cv.put(COL_EVENT_DATE, date);
				cv.put(COL_EVENT_TITLE, title);
				cv.put(COL_EVENT_DESCRIPTION, description);
				//cv.put(locationLatitude, Double.toString(locLatitude));
				//cv.put(locationLongitude, Double.toString(locLongitude));
				
				try{
					eventDB.insert(TABLE_NAME, null, cv);
				}catch(Exception e){
					return 0;
				}
				return 1;
			}
			
			
			int updateEvent(String date, String time, String title, String description){
				ContentValues cv = new ContentValues();
				
				cv.put(COL_EVENT_TIME, time);
				cv.put(COL_EVENT_DATE, date);
				cv.put(COL_EVENT_TITLE, title);
				cv.put(COL_EVENT_DESCRIPTION, description);
				//cv.put(locationLatitude, Double.toString(locLatitude));
				//cv.put(locationLongitude, Double.toString(locLongitude));
				
				try{
					eventDB.update(TABLE_NAME, cv, COL_EVENT_DATE + "=?", new String[]{eventDay});
				}catch(Exception e){
					return 0;
				}
				return 1;
			}
			
			
			
			int addEventLocation(String date, String locAddress, double locLatitude, double locLongitude){
				ContentValues cv = new ContentValues();
				
				cv.put(COL_EVENT_DATE, date);
				cv.put(LOCATION_ADDRESS, locAddress);
				cv.put(LOCATION_LATITUDE, Double.toString(locLatitude));
				cv.put(LOCATION_LONGITUDE, Double.toString(locLongitude));
				
				try{
					eventDB.insert(TABLE_LOCATION, null, cv);
				}catch(Exception e){
					return 0;
				}
				return 1;
			}
			
			
			void showDBResultToast(int eventStatus){
				Toast.makeText(EditDayActivity.this, eventStatus==1?"Event Added Successfully!":"Event Not Accepted!", Toast.LENGTH_SHORT).show();
			}
			
			void showLocResultToast(int eventStatusLoc){
				Toast.makeText(EditDayActivity.this, eventStatusLoc==1?"Location Added Successfully!":"Location Not Accepted!", Toast.LENGTH_SHORT).show();
			}
			
			void updateDBResultToast(int eventStatus){
				Toast.makeText(EditDayActivity.this, eventStatus==1?"Event Updated Successfully!":"Event Not Accepted!", Toast.LENGTH_SHORT).show();
			}
			
			void updateLocResultToast(int eventStatusLoc){
				Toast.makeText(EditDayActivity.this, eventStatusLoc==1?"Location Updated Successfully!":"Location Not Accepted!", Toast.LENGTH_SHORT).show();
			}
		});
        
	}
}