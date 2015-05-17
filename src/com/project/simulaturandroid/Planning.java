package com.project.simulaturandroid;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;

/**
 * 
 * 
 * @author ProBook 450g2 Classe qui gere le planning, elle recoit les donnees
 *         puis les affiches dans un gridView
 */
public class Planning extends Activity {
	GridView gridView;

	private Beans bean;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		bean = new Beans();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planning_layout);

		ArrayList<String> tab = new ArrayList<String>();
		tab.addAll(bean.getPlanningTabUpdated());
		adapter = new ArrayAdapter<String>(this,
				R.layout.activity_planning_layout, R.id.textt,
				bean.getPlanningTab());
		gridView = (GridView) findViewById(R.id.gridview1);
		gridView.setAdapter(adapter);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		// bean.getPlanningTab().clear();
		super.onDestroy();

		Log.i("dd", "doooooom" + bean.getPlanningTabUpdated());
	}

}