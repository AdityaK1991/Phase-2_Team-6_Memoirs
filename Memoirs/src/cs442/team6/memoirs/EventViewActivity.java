package cs442.team6.memoirs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EventViewActivity extends Activity {
	
	TextView txtTitle, txtDescription;
	Button btnBack2;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_text_view);
	
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtDescription = (TextView)findViewById(R.id.txtDescription);
        btnBack2 = (Button)findViewById(R.id.btnBack2);
        
        btnBack2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EventViewActivity.this.finish();
			}
		});

	}
}
