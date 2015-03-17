package com.project.simulaturandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

@SuppressLint("NewApi")
public class Loged extends Activity implements View.OnClickListener {
	public Loged() {
		// TODO Auto-generated constructor stub
	}
	private Button buttonExit;
	private Button buttonPlanning;
	private Button button1;
	private Button button2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_layout);
		this.buttonPlanning = (Button) findViewById(R.id.buttonPlanning);
		this.buttonExit = (Button) findViewById(R.id.buttonExit);
		button1 = (Button) findViewById(R.id.button2);
		button2 = (Button) findViewById(R.id.button3);
		buttonPlanning.setOnClickListener(this);
		buttonExit.setOnClickListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.buttonPlanning:			
			Intent intent = new Intent(Loged.this, Planning.class);
			Log.i("voici :" ,"voici ici");
			this.startActivity(intent);
			Log.i("voici :" ,"voici ici2");
			finish();
			break;
		case R.id.buttonExit:
			break;
		default:
			Log.e("Loged", "Default switch used !!");
			finishAffinity();
		}
	}	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
}
