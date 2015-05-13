package com.project.simulaturandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * 
 * @author ProBook 450g2 Cette classe genere l'image de la MAP
 */
public class MapClass extends Activity {
	ImageView imagePosition;

	/**
	 * 
	 * Responsable de la map
	 * 
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapclass_layout);
		imagePosition = (ImageView) this.findViewById(R.id.DrawingImageView);
		Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager()
				.getDefaultDisplay().getWidth(), (int) getWindowManager()
				.getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		imagePosition.setImageBitmap(bitmap);

		/**
		 * Dessiner et positioner le cercle
		 */
		
	 NombreGroupeBean nbrbGroupeBean = new  NombreGroupeBean();
		int nbrGroupe =nbrbGroupeBean.getNmbrGroupe();
		float radius = 30;
		float x = 100;
		float y = 100;
		switch (nbrGroupe) {
		
		case 1:
			Paint paint1 = new Paint();
			canvas.drawCircle(x, y, radius, paint1);
			break;
		case 2:
			Paint paint2 = new Paint();
			Paint paint22 = new Paint();
			canvas.drawCircle(x, y, radius, paint2);
			canvas.drawCircle(x+100, y+100, radius, paint22);
			break;
		case 3:
			Paint paint3 = new Paint();
			Paint paint33 = new Paint();
			Paint paint333 = new Paint();
			canvas.drawCircle(x, y, radius, paint3);
			canvas.drawCircle(x+200, y+200, radius, paint33);
			canvas.drawCircle(x+100, y+100, radius, paint333);
			break;
		case 4:
			Paint paint4 = new Paint();
			Paint paint44 = new Paint();
			Paint paint444 = new Paint();
			Paint paint4444 = new Paint();
			canvas.drawCircle(x, y, radius, paint4);
			canvas.drawCircle(x+50, y+100, radius, paint4444);
			canvas.drawCircle(x+250, y+200, radius, paint44);
			canvas.drawCircle(x+150, y+100, radius, paint444);
			break;
			default:
				//
			
			
		
		}
			

		//Paint paint2 = new Paint();
		/*((Paint) painting.get(0)).setColor(Color.RED);
		
		float x2 = 300;
		float y2 = 200;
		float radius2 = 35;
		canvas.drawCircle(x2, y2, radius2, (Paint) painting.get(0));*/

	}

}
