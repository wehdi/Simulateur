package com.project.simulaturandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * 
 * @author ProBook 450g2
 * 
 *         L'interface apres connexion, de multiples choix sont offerent a
 *         l'etudiant !
 *
 */

@SuppressLint("NewApi")
public class Loged extends Activity implements View.OnClickListener {
	public Loged() {

	}

	private Button buttonExit;
	private Button buttonPlanning;
	private Button buttonHelp;
	private Button buttonIHelp;
	private Button buttonCreat;
	private Button buttonSearch;
	private ProgressDialog barProgress;
	private Handler updateBarHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * Instantiation des composant graphique
		 */
		setContentView(R.layout.activity_login_layout);
		this.buttonPlanning = (Button) findViewById(R.id.buttonPlanning);
		this.buttonExit = (Button) findViewById(R.id.buttonExit);
		buttonHelp = (Button) findViewById(R.id.buttonHelp);
		buttonIHelp = (Button) findViewById(R.id.buttonIHelp);
		buttonCreat = (Button) findViewById(R.id.buttonCreate);
		buttonSearch = (Button) findViewById(R.id.buttonSearch);
		buttonPlanning.setOnClickListener(this);
		buttonExit.setOnClickListener(this);
		buttonHelp.setOnClickListener(this);
		buttonIHelp.setOnClickListener(this);
		buttonCreat.setOnClickListener(this);
		buttonSearch.setOnClickListener(this);

		updateBarHandler = new Handler();
	}

	/**
	 * Definitions du comportements des bouttons
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.buttonPlanning:
			lunchLoading(view);
			break;
		case R.id.buttonSearch:
			Intent intentMap = new Intent(Loged.this, MapClass.class);
			this.startActivity(intentMap);
			break;
		case R.id.buttonExit:
			System.exit(0);
			break;
		case R.id.buttonCreate:
			Intent intentCreat = new Intent(Loged.this, CreatGroupe.class);
			this.startActivity(intentCreat);
			break;
		case R.id.buttonHelp:
			Intent intentHelpMe = new Intent(Loged.this, HelpMe.class);
			this.startActivity(intentHelpMe);
			break;
		case R.id.buttonIHelp :
			Intent intentIHelp = new Intent(Loged.this, IHelp.class);
			this.startActivity(intentIHelp);
			break;
		default:
			Log.e("Loged", "Default switch used !!");

		}
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	private void lunchLoading(View view) {
		final ProgressDialog loadpProgressDialog = ProgressDialog.show(this,
				"Attendez SVP...", "Chargement du contenu");
		loadpProgressDialog.setCancelable(true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);

				} catch (Exception e) {
					// TODO: handle exception
				}
				loadpProgressDialog.dismiss();
				// finish();
				Intent intent = new Intent(Loged.this, Planning.class);
				Loged.this.startActivity(intent);

			}

		}).start();
		;
	}

	/*
	 * private void lunchBarDialog(View view) { barProgress = new
	 * ProgressDialog(this);
	 * 
	 * barProgress.setTitle("Telechargement"); barProgress.setMessage("mehdi0");
	 * barProgress.setProgressStyle(barProgress.STYLE_HORIZONTAL);
	 * barProgress.setProgress(0); barProgress.setMax(20); barProgress.show();
	 * 
	 * new Thread(new Runnable() {
	 * 
	 * public void run() { try { while (barProgress.getProgress() <=
	 * barProgress.getMax()) {
	 * 
	 * Thread.sleep(2000); updateBarHandler.post(new Runnable() {
	 * 
	 * @Override public void run() { barProgress.incrementProgressBy(2); } });
	 * 
	 * if( barProgress.getProgress() == barProgress.getMax())
	 * barProgress.dismiss();
	 * 
	 * } } catch (Exception e) {
	 * 
	 * }
	 * 
	 * } }).start(); }
	 */
}
