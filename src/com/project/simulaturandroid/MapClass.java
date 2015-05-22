package com.project.simulaturandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 
 * @author ProBook 450g2 Cette classe genere l'image de la MAP
 */
public class MapClass extends Activity {
	private ImageView imagePosition;
	private boolean is1, is2, is3, is4 = false;

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

		NombreGroupeBean nbrbGroupeBean = new NombreGroupeBean();
		int nbrGroupe = nbrbGroupeBean.getNmbrGroupe();
		float radius = 30;
		float x = 130;
		float y = 170;

		switch (nbrGroupe) {

		case 1:
			Paint paint1 = new Paint();
			canvas.drawCircle(x, y, radius, paint1);
			is1 = true;
			is4 = false;
			is2 = false;
			is3 = false;
			break;
		case 2:
			Paint paint2 = new Paint();
			Paint paint22 = new Paint();
			canvas.drawCircle(x, y, radius, paint2);
			canvas.drawCircle(x, y + 85, radius, paint22);
			is2 = true;
			is1 = true;
			is4 = false;
			is3 = false;
			break;
		case 3:
			Paint paint3 = new Paint();
			Paint paint33 = new Paint();
			Paint paint333 = new Paint();
			canvas.drawCircle(x, y, radius, paint3);
			canvas.drawCircle(x, y + 85, radius, paint33);
			;
			canvas.drawCircle(x, y + 255, radius, paint333);
			is3 = true;
			is1 = true;
			is2 = true;
			is4 = false;
			break;
		case 4:
			Paint paint4 = new Paint();
			Paint paint44 = new Paint();
			Paint paint444 = new Paint();
			Paint paint4444 = new Paint();
			canvas.drawCircle(x, y, radius, paint4);
			canvas.drawCircle(x, y + 85, radius, paint44);
			;
			canvas.drawCircle(x, y + 255, radius, paint4444);
			canvas.drawCircle(x + 500, y + 170, radius, paint444);
			is4 = true;
			is1 = true;
			is2 = true;
			is3 = true;
			break;
		default:

		}

	}

	/**
	 * Touche sceen inforation sur les groupes de revisions
	 */

	public boolean onTouchEvent(MotionEvent event) {

		float x = (event.getX());
		float y = event.getY();
		if (is1 && !is2 && !is3 && !is4) {
			if (x > 90 && x < 175 && y < 190 && y > 130) {

				setInformation("1", "Groupe1");
			}

		} else if (is2 && is1 && !is3 && !is4) {
			if (x > 90 && x < 175 && y < 190 && y > 130) {
				setInformation("1", "groupe 1");

			} else if (x > 90 && x < 175 && y < 280 && y > 215) {
				setInformation("2", "groupe 2");
			}
		} else if (is3 && is2 && is3 & !is4) {
			if (x > 90 && x < 175 && y < 190 && y > 130) {
				setInformation("1", "groupe 1");

			} else if (x > 90 && x < 175 && y < 280 && y > 215) {
				setInformation("2", "groupe 2");
			} else if (x > 90 && x < 175 && y < 440 && y > 385) {
				setInformation("3", "groupe 3");
			}
		} else if (is3 && is2 && is3 & is4) {
			if (x > 90 && x < 175 && y < 190 && y > 130) {
				setInformation("1", "groupe 1");

			} else if (x > 90 && x < 175 && y < 280 && y > 215) {
				setInformation("2", "groupe 2");
			} else if (x > 90 && x < 175 && y < 440 && y > 385) {
				setInformation("3", "groupe 3");
			} else if (x > 600 && x < 660 && y < 365 && y > 300) {
				setInformation("4", "groupe 4");
			}
		}
		return super.onTouchEvent(event);
	}

	/**
	 * Cree une AlertDialog
	 * 
	 * @param title
	 * @param msg
	 */

	private void setInformation(String nbr, String msg) {
		new AlertDialog.Builder(this)
				.setTitle("Information sur le groupe " + nbr)
				.setMessage(msg)
				.setCancelable(true)
				.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								dialog.cancel();
							}
						})

				.setIcon(android.R.drawable.ic_dialog_info).show();

	}
}
