package com.project.agent;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import android.R.bool;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.project.simulaturandroid.LunchPlatform;

public class AgentLogin extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Codec codec = new SLCodec();
	private jade.lang.acl.ACLMessage message;

	private LunchPlatform err;

	private Context context;
	private String agentName = "agentLogin";
	private boolean stop = false;

	private final String PREFS_HOST_ADDRESS = "HOST_ADDRESS";
	private final String PREFS_AGENT_NAME = "AGENT_NAME";
	private final String PREFS_FILE_NAME = "jademos";

	@Override
	protected void setup() {

		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			if (args[0] instanceof Context) {
				context = (Context) args[0];
			}
		}
		addBehaviour(new Lo(this));
		// Get ipAddress and agentName
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		// ipAddress = sharedPreferences.getString(Constants.PREFS_HOST_ADDRESS,
		// ipAddress);
		agentName = sharedPreferences.getString(PREFS_AGENT_NAME, agentName);
	}

	protected void takeDown() {
	}

	class Lo extends Behaviour {
		public Lo(Agent a) {
			super(a);
		}

		String r = "ok";

		public void action() {
			Log.i("JeSuis!!!", "AGENTTTTT");
			if (r.equals("ok")) {
				AgentLogin.this.context.startActivity(new Intent(
						AgentLogin.this.context,
						com.project.simulaturandroid.Loged.class));
				stop = true;
				Log.i("Agent :", "Its ok ");
				doWait(2000);
			} else {
				LunchPlatform err = new LunchPlatform();
				err.toastErreur("Je suis le souci");
				Log.i("Agent", "Im in else !!!");
				doWait(2000);

			}

		}

		@Override
		public boolean done() {
			Log.i("Agent :", "Is Done .");
			return stop;
		}

	}
}
