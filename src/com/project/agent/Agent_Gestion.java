package com.project.agent;

import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

@SuppressWarnings("serial")
public class Agent_Gestion extends Agent {
	/**
	 * 
	 */

	private final String PREFS_AGENT_NAME = "AGENT_NAME";
	private final String PREFS_FILE_NAME = "jademos";
	private Context context;
	private String agentName = "agentGestion";
	private String ipAddress = "192.168.2.10";
	public static String PREFS_HOST_ADDRESS = "HOST_ADDRESS";

	@Override
	protected void setup() {
		super.setup();
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			if (args[0] instanceof Context) {
				context = (Context) args[0];
			}
		}
		addBehaviour(new Do(this));
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		ipAddress = sharedPreferences.getString(PREFS_HOST_ADDRESS, ipAddress);
		agentName = sharedPreferences.getString(PREFS_AGENT_NAME, agentName);
	}

	@SuppressWarnings("serial")
	class Do extends CyclicBehaviour {
		public Do(Agent a) {
			super(a);
		}

		@Override
		public void action() {
			ACLMessage msg = myAgent.receive();
			if (msg != null) {
				ArrayList content = null;
				try {
					content = (ArrayList) msg.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.i("Agent_gesiton", content.get(0).toString());
				doWait(3000);
			} else {
				Log.i("Agent_gestion", "le else est appele");
				block();
			}

		}

	}

}
