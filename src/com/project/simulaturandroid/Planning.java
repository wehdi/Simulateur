package com.project.simulaturandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class Planning extends Activity {
	GridView gridView;
	private Beans bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planning_layout);

		bean = new Beans();
		gridView = (GridView) findViewById(R.id.gridview1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.activity_planning_layout, R.id.textt,
				bean.getPlanningTab());
		gridView.setAdapter(adapter);

	}
}