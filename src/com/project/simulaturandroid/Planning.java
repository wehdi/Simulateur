package com.project.simulaturandroid;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class Planning extends Activity {
	GridView gridView;
	private Generate_Planning generate_Planning;
 private  Loged l;
	static final String[] numbers = new String[] {
			// 7-1
			"  ", "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Samedi",
			"8h-9h", "Module", "Module", "Module", "Module", "Module",
			"Module", "9h-10h", "Mercredi", "Module", "M", "N", "O", "fgf",
			"10h-11h", "Q", "R", "S", "T", "Samedi", "Module", "11h-12h",
			"Module", "Module", "Module", "Module", "Module", "Module",
			"12h-13h", "W", "X", "Y", "Z", "kk", "fgf", "13h-14h", "W", "X",
			"Y", "Z", "kk", "fgf", "14h-15h", "W", "X", "Y", "Z", "kk", "fgf",
			"15h-16h", "W", "X", "Y", "Z", "kk", "fgf", };

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planning_layout);
		generate_Planning = new Generate_Planning();
		gridView = (GridView) findViewById(R.id.gridview1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.activity_planning_layout, R.id.textt,
				generate_Planning.getPp());
        
		gridView.setAdapter(adapter);
		l= new Loged();
	try {
		finalize();
	} catch (Throwable e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}