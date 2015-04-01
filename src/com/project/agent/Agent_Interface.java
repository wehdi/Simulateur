package com.project.agent;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.introspection.ACLMessage;
import jade.lang.acl.MessageTemplate;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.project.simulaturandroid.Beans;
import com.project.simulaturandroid.LunchPlatform;

public class Agent_Interface extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Codec codec = new SLCodec();
	private jade.lang.acl.ACLMessage message;
	private String agentName = "agentBdd";
	private String ipAddress = "192.168.2.2";

	private LunchPlatform err;
	private final String TAG = "Agent_Interface";
	private Context context;
	private boolean stop = false;

	private final String PREFS_HOST_ADDRESS = "HOST_ADDRESS";
	private final String PREFS_AGENT_NAME = "AGENT_NAME";
	private final String PREFS_FILE_NAME = "jade.preferences";
	private Beans bean;
	private LunchPlatform lunchPlatform;

	@Override
	protected void setup() {
		bean = new Beans();
		// lunchPlatform = new LunchPlatform();

		ParallelBehaviour comportementParallel = new ParallelBehaviour();

		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			if (args[0] instanceof Context) {
				context = (Context) args[0];
			}
		}
		comportementParallel.addSubBehaviour(new Lo(this));
		comportementParallel.addSubBehaviour(new Lo2(this));
		addBehaviour(comportementParallel);

		// Get ipAddress and agentName
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		ipAddress = sharedPreferences.getString(PREFS_HOST_ADDRESS, ipAddress);
		agentName = sharedPreferences.getString(PREFS_AGENT_NAME, agentName);
	}

	public class Lo extends Behaviour {
		private Beans bean;
		private static final long serialVersionUID = 1L;

		public Lo(Agent a) {
			super(a);
			bean = new Beans();

		}

		public void action() {
			String userName = bean.getLogin();
			String mdp = bean.getMdp();
			// envoi des ids entrées
			jade.lang.acl.ACLMessage requestLoginMessage = new jade.lang.acl.ACLMessage(
					jade.lang.acl.ACLMessage.INFORM);
			requestLoginMessage.setConversationId("id");
			requestLoginMessage.setContent(userName + "|" + mdp);
			AID dummyAid = new AID();
			dummyAid.setName("agentBdd@192.168.2.2:1099/JADE");
			dummyAid.addAddresses("http://192.168.2.2:7778/acc");
			requestLoginMessage.addReceiver(dummyAid);
			send(requestLoginMessage);

		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return stop;
		}
	}

	// class de receptions des messages
	public class Lo2 extends Behaviour {
		private static final long serialVersionUID = 1L;

		public Lo2(Agent a) {
			super(a);
		}

		public void action() {
			MessageTemplate model = MessageTemplate.and(MessageTemplate
					.MatchPerformative(jade.lang.acl.ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("resp"));
			jade.lang.acl.ACLMessage reponseLoginMessage = receive(model);
			if (reponseLoginMessage != null) {
				String validateConnection = reponseLoginMessage.getContent()
						.toString();
				
				if (validateConnection.equals("ok")) {
					Agent_Interface.this.context.startActivity(new Intent(
							Agent_Interface.this.context,
							com.project.simulaturandroid.Loged.class));
					stop = true;
				} else {
					Log.i(TAG, "je n'ai pas pu me connecter"
							+ validateConnection);
				}
			} else {
				block();

			}
		}

		@Override
		public boolean done() {

			return stop;
		}

	}
}
