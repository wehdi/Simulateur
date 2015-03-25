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
	private Button buttonHelp;
	private Button buttonIHelp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_layout);
		this.buttonPlanning = (Button) findViewById(R.id.buttonPlanning);
		this.buttonExit = (Button) findViewById(R.id.buttonExit);
		buttonHelp = (Button) findViewById(R.id.buttonHelp);
		buttonIHelp = (Button) findViewById(R.id.buttonIHelp);
		buttonPlanning.setOnClickListener(this);
		buttonExit.setOnClickListener(this);
		buttonHelp.setOnClickListener(this);
		buttonIHelp.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.buttonPlanning:
			Intent intent = new Intent(Loged.this, Planning.class);
			this.startActivity(intent);
			break;
		case R.id.buttonExit:
			System.exit(0);
			break;
		default:
			Log.e("Loged", "Default switch used !!");

		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}
}
