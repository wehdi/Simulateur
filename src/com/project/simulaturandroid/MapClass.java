package com.project.simulaturandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 
 * @author ProBook 450g2 Cette classe genere l'image de la MAP
 */
public class MapClass extends Activity {
	ImageView imageMapClass;
	private Button button;

	/**
	 * 
	 * Responsable de la map
	 * 
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapclass_layout);
		// imageMapClass = (ImageView)findViewById(R.id.imageView1);
		this.button = (Button) findViewById(R.layout.activity_mapclass_layout);

	}

}
