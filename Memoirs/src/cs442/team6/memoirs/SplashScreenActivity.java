package cs442.team6.memoirs;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends Activity {
	
	ImageView imgLogo;
	TextView txtLogo;
	Typeface font;
	Animation fadeIn, slide;
	private static final int TIME = 3 * 1000;// 3 seconds 
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState); 
		
		setContentView(R.layout.activity_splash_screen); 
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		fadeIn = AnimationUtils.loadAnimation(this, R.anim.abc_fade_in);
		slide = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		
		font = Typeface.createFromAsset(getAssets(), "MP.ttf");

		imgLogo = (ImageView)findViewById(R.id.imgLogo);
		txtLogo = (TextView)findViewById(R.id.txtLogo);
		
		txtLogo.setTypeface(font);
		
		imgLogo.setAnimation(fadeIn);
		txtLogo.setAnimation(slide);
		
		new Handler().postDelayed(new Runnable() { 
			@Override public void run() { 
				Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class); 
				startActivity(intent);
				SplashScreenActivity.this.finish(); 
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out); 
				} 
			}, TIME); 
		
	} 
	@Override 
	public void onBackPressed() { 
		this.finish(); 
		super.onBackPressed(); 
		} 
	} 