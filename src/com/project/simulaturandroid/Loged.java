package com.project.simulaturandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Loged extends Activity implements View.OnClickListener {
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
	public void onClick(View v) {
		switch (v.getId()) {
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

}
