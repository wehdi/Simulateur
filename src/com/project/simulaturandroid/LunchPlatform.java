package com.project.simulaturandroid;

import jade.android.AgentContainerHandler;
import jade.android.AgentHandler;
import jade.android.RuntimeCallback;
import jade.android.RuntimeService;
import jade.android.RuntimeServiceBinder;
import jade.wrapper.StaleProxyException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.agent.Agent_Contexte;
import com.project.agent.Agent_Gestion;
import com.project.agent.Agent_Interface;

public class LunchPlatform extends Activity implements View.OnClickListener {
	private Button buttonLunch;

	private EditText textUserName;
	private EditText textPassword;
	private final String tostEmpty = "Veuiller remplir tous les champs !";

	private static final String TAG = "AgentLuncher";
	private RuntimeServiceBinder runtimeServiceBinder;
	private ServiceConnection serviceConnection;
	private AgentContainerHandler agentContainerHandler;
	private SharedPreferences sharedPreferences;
	private Beans bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lunch_main);
		bean = new Beans();
		buttonLunch = (Button) findViewById(R.id.buttonSeConnecter);
		buttonLunch.setOnClickListener(this);

		textUserName = (EditText) findViewById(R.id.textUserName);
		textPassword = (EditText) findViewById(R.id.textPassword);

		// initialisation des preferences (android)
		sharedPreferences = getPreferences(Context.MODE_PRIVATE);
		// cc.bindService(getApplicationContext());
		// ///
	}

	@Override
	protected void onStart() {
		super.onStart();
		bindService();
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.buttonSeConnecter:
			String userName = textUserName.getText().toString();
			String password = textPassword.getText().toString();
			if (userName.isEmpty() || password.isEmpty()) {
				Toast.makeText(getApplicationContext(), tostEmpty,
						Toast.LENGTH_SHORT).show();
			} else {
				/*
				 * Intent intent = new Intent(LunchPlatform.this, Loged.class);
				 * this.startActivity(intent);
				 */
				creatAgent("agentInterface", Agent_Interface.class.getName());
				bean.setLogin(userName);
				bean.setMdp(password);
				creatAgent("agentGestion", Agent_Gestion.class.getName());
				creatAgent("agentContexte", Agent_Contexte.class.getName());

			}
			break;
		/*
		 * case R.id.buttonLunch2: editText.setText(" ");
		 * 
		 * break;
		 */
		default:
			//
		}
	}

	public void toastErreur(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
				.show();

	}

	// Creat jade container :)
	private void bindService() {
		// Check runtime service
		if (runtimeServiceBinder == null) {
			// Create Runtime Service Binder here
			serviceConnection = new ServiceConnection() {
				@Override
				public void onServiceConnected(ComponentName componentName,
						IBinder service) {
					runtimeServiceBinder = (RuntimeServiceBinder) service;
					// Log.i(TAG,
					// "###Gateway successfully bound to RuntimeService");
					startMainContainer();
				}

				@Override
				public void onServiceDisconnected(ComponentName componentName) {
					// Log.i(TAG, "###Gateway unbound from RuntimeService");
				}
			};
			// Log.i(TAG, "###Binding Gateway to RuntimeService...");
			bindService(new Intent(getApplicationContext(),
					RuntimeService.class), serviceConnection,
					Context.BIND_AUTO_CREATE);
		} else {
			startMainContainer();
		}

	}

	private void startMainContainer() {
		runtimeServiceBinder
				.createMainAgentContainer(new RuntimeCallback<AgentContainerHandler>() {

					@Override
					public void onSuccess(AgentContainerHandler arg0) {
						agentContainerHandler = arg0;
						Log.i(TAG, "###Main-Container created...");
						Log.i(TAG, "###Container:"
								+ agentContainerHandler.getAgentContainer()
										.getName());
						Log.i(TAG, "###mainContainerHandler:"
								+ agentContainerHandler);

					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.i(TAG, "###Failed to create Main Container");

					}
				});

	}

	public void creatAgent(String name, String _TAG) {
		if (agentContainerHandler != null) {
			agentContainerHandler.createNewAgent(name, _TAG,
					new Object[] { LunchPlatform.this },
					new RuntimeCallback<AgentHandler>() {

						@Override
						public void onFailure(Throwable arg0) {
							Log.i(TAG, "###Failed to created an Agent");

						}

						@Override
						public void onSuccess(AgentHandler arg0) {
							try {
								Log.i(TAG, "###Success to create agent: "
										+ arg0.getAgentController().getName());

								arg0.getAgentController().start();
							} catch (StaleProxyException e) {
								e.printStackTrace();
							}
						}

					});
		} else {
			Log.e(TAG, "###Can't get Main-Container to create agent");
		}
	}

}
