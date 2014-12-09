package cs442.team6.memoirs;

import java.io.FileOutputStream;
import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

	EditText edtName, edtRPass, edtRConfirmPass;
	Button btnExit, btnUserRegister;
	public static String fileName = "NameFile";
	public static String filePass = "PassFile";
	public static String fileConfirmPass = "CPassFile";
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_view);
        
        ActionBar actionBar = getActionBar();
		actionBar.hide();
        
        edtName = (EditText)findViewById(R.id.edtName);
        edtRPass = (EditText)findViewById(R.id.edtRPass);
        edtRConfirmPass = (EditText)findViewById(R.id.edtRConfirmPass);
        
        btnUserRegister = (Button)findViewById(R.id.btnUserRegistration);
        btnExit = (Button)findViewById(R.id.btnExit);
        
        btnUserRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(edtName.getText().toString() != "" && edtRPass.getText().toString() != "" 
						&& edtRConfirmPass.getText().toString() != "")
					
				{
				    try {
				        	FileOutputStream fOutName = openFileOutput(fileName,MODE_WORLD_READABLE);
							fOutName.write(edtName.getText().toString().getBytes());
							fOutName.close();
							
							FileOutputStream fOutPass = openFileOutput(filePass,MODE_WORLD_READABLE);
							fOutPass.write(edtRPass.getText().toString().getBytes());
							fOutPass.close();
							
							FileOutputStream fOutConfirmPass = openFileOutput(fileConfirmPass,MODE_WORLD_READABLE);
							fOutConfirmPass.write(edtRConfirmPass.getText().toString().getBytes());
							fOutConfirmPass.close();
							
							Toast.makeText(getBaseContext(),"File saved", Toast.LENGTH_SHORT).show();
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							Toast.makeText(getBaseContext(),"File not saved", Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
		         
				// TODO Auto-generated method stub
				RegistrationActivity.this.finish();
					}
				
				else{
					Toast.makeText(getBaseContext(), "Please complete your profile!", Toast.LENGTH_LONG).show();
				}
				}
		});
    }    
}