package com.project.agent;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

@SuppressWarnings("serial")
public class Agent_Contexte extends Agent {
	private final String PREFS_HOST_ADDRESS = "HOST_ADDRESS";
	private final String PREFS_AGENT_NAME = "AGENT_NAME";
	private final String PREFS_FILE_NAME = "jade.preferences";
	private String agentName = "agentInterface";
	private String ipAddress = "192.168.2.2";
	private Context context;
	private final String TAG = "Agent_Contexte";

	@Override
	protected void setup() {
		super.setup();
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			if (args[0] instanceof Context) {
				context = (Context) args[0];
			}
		}
		addBehaviour(new InfoEvents());
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		ipAddress = sharedPreferences.getString(PREFS_HOST_ADDRESS, ipAddress);
		agentName = sharedPreferences.getString(PREFS_AGENT_NAME, agentName);

	}

	class InfoEvents extends Behaviour {
		private boolean stop = false;

		@Override
		public void action() {

			/*
			 * Agent_Contexte.this.context.startActivity(new Intent(
			 * Agent_Contexte.this.context,
			 * com.project.simulaturandroid.Notify.class));
			 */
			Log.i(TAG, "go go go go go");
			MessageTemplate modele = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("notify"));
			jade.lang.acl.ACLMessage reponse = myAgent.receive(modele);
			if (reponse != null) {
				// if (notify.getContent().toString().equals("kkk")) {
				Log.i(TAG, "je recu un messsage kh kh kh");
				
				
				 Agent_Contexte.this.context.startService(new Intent(
				  Agent_Contexte.this.context,
				  com.project.simulaturandroid.NotifyService.class)); 
				 block();
			} else {
	
				block();
			}

		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return false;
		}

	}

	@Override
	protected void takeDown() {
		Log.i("hiii", "test test aalalala finiiii");
		super.takeDown();
	}
}