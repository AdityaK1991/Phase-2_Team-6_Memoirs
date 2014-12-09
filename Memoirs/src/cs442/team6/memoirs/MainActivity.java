package cs442.team6.memoirs;

import java.io.FileInputStream;
import java.util.GregorianCalendar;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	EditText edtPass;
	Button btnEnter, btnExit, btnRegister;
	TextView txtMemoirs;
	
	Typeface font;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        
        ActionBar actionBar = getActionBar();
		actionBar.hide();
		
        edtPass = (EditText)findViewById(R.id.edtPass);
        btnEnter = (Button)findViewById(R.id.btnEnter);
        btnExit = (Button)findViewById(R.id.btnExit);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        
        txtMemoirs = (TextView)findViewById(R.id.txtMemoris);
        font = Typeface.createFromAsset(getAssets(), "MP.ttf");
		txtMemoirs.setTypeface(font);
        
        btnEnter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try{
					 
			         FileInputStream fin = openFileInput(RegistrationActivity.fileConfirmPass);
			         int c;
			         String temp="";
			         while( (c = fin.read()) != -1){
			            temp = temp + Character.toString((char)c);
			         }
				         if(edtPass.getText().toString().equals(temp))
				         {
				        	 Log.d("Pass", edtPass.getText().toString());
				        	 
				        	 Intent i = new Intent(MainActivity.this, ListViewActivity.class);
							 startActivity(i);
							 Toast.makeText(getBaseContext(),"Login successful!", Toast.LENGTH_SHORT).show();
	
				         }	
				         else{
				        	 Log.d("Pass", edtPass.getText().toString());
				        	 Log.d("Temp", temp);
				        	 Toast.makeText(getBaseContext(),"In-correct password!", Toast.LENGTH_SHORT).show();
				         }
			      }catch(Exception e){
			    	  	 Toast.makeText(getBaseContext(),"Please re-enter password!", Toast.LENGTH_SHORT).show();
			      }
				
				
			}
		});
        
        btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
				startActivity(i);
			}
		});
        
        btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.finish();	
			}
		});
    }

    @Override 
	public void onBackPressed() { 
		this.finish(); 
		super.onBackPressed(); 
		} 

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
