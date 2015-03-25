package com.project.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.project.simulaturandroid.Beans;

@SuppressWarnings("serial")
public class Agent_Gestion extends Agent {
	private final String TAG = "Agent_Gestion";
	private final String PREFS_AGENT_NAME = "AGENT_NAME";
	private final String PREFS_FILE_NAME = "jade.preferences";
	private Context context;
	private String agentName = "agentBdd";
	private String ipAddress = "192.168.2.4";
	public static String PREFS_HOST_ADDRESS = "HOST_ADDRESS";
	private ArrayList<String> content = null;
	private Beans bean;
	ArrayList<String> pp = new ArrayList<>();

	protected void setup() {
		super.setup();
		bean = new Beans();
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			if (args[0] instanceof Context) {
				context = (Context) args[0];
			}
		}
		addBehaviour(new Do(this));
		// comportementSequentiel.addSubBehaviour(new Do(this));
		// addBehaviour(comportementSequentiel);

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		ipAddress = sharedPreferences.getString(PREFS_HOST_ADDRESS, ipAddress);
		agentName = sharedPreferences.getString(PREFS_AGENT_NAME, agentName);
	}

	// Le smrtphone recoit le planning depuis le serveur
	class Do extends Behaviour {
		private boolean stop = false;

		public Do(Agent a) {
			super(a);
		}

		public void action() {
			Log.i(TAG, "Gestion start !");
			MessageTemplate model = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("go"));
			ACLMessage msg = myAgent.receive(model);
			if (msg != null) {
				try {
					//rempli le planning
					content = (ArrayList<String>) msg.getContentObject();
					bean.setPlanningTab(content);
					//envoi un rep pour stoper l'envoi du planning
					ACLMessage reponsemessage = new ACLMessage(ACLMessage.INFORM);
					reponsemessage.setConversationId("stop_planning");
					reponsemessage.setContent("stop");
					AID dummyAid = new AID();
					dummyAid.setName("agentBdd@192.168.2.4:1099/JADE");
					dummyAid.addAddresses("http://192.168.2.4:7778/acc");
					reponsemessage.addReceiver(dummyAid);
					send(reponsemessage);
					

				} catch (UnreadableException e) {
					Log.i("Agent_Gestion", "ACLMessage probleme");
					e.printStackTrace();
				}stop = true;

			} else {
				block();
			}
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return stop;
		}
	}

	/*
	 * class Do2 extends CyclicBehaviour { public Do2(Agent_Gestion
	 * agentGestion) { }
	 * 
	 * @Override public void action() { ACLMessage message = new
	 * ACLMessage(ACLMessage.INFORM); // message.setLanguage(codec.getName());
	 * message.setContent("hello je suis obile"); AID dummyAid = new AID();
	 * dummyAid.setName("agentBdd@192.168.2.4:1099/JADE");
	 * dummyAid.addAddresses("http://192.168.2.4:7778/acc");
	 * message.addReceiver(dummyAid); send(message); }
	 * 
	 * }
	 */

}
