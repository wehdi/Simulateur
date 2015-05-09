package com.project.agent;

/**
 * 
 * @author ProBook 450g2
 * 
 * Classe de gestion qui est chargé de :
 *  - Recevoir le planning depuis l'agent Controller
 *  -l'envoyer a la vu pour etre pour etre affiché
 *  - Envoyer une reponse a l'agent scoloar pour stop l'arrivé de msg
 * 
 */

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.ArrayList;

import android.util.Log;

import com.project.simulaturandroid.Beans;
import com.project.simulaturandroid.Const;

@SuppressWarnings("serial")
public class Agent_Gestion extends Agent {
	private final String TAG = "Agent_Gestion";
	private Beans bean;

	protected void setup() {
		super.setup();
		bean = new Beans();
		addBehaviour(new Do(this));
	}

	/**
	 * Le smrtphone recoit le planning depuis le serveur
	 * 
	 *
	 */
	class Do extends Behaviour {
		private boolean stop = false;

		public Do(Agent a) {
			super(a);
		}

		@SuppressWarnings("unchecked")
		public void action() {
			/**
			 * Recevoir le planning qui est envoyer par l'agent Controller
			 */
			MessageTemplate model = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("go"));
			ACLMessage msg = myAgent.receive(model);
			if (msg != null) {
				try {
					/**
					 * rempli le planning et l'envoyer pour pourvoir etre
					 * afficher
					 */
					bean.setPlanningTab((ArrayList<String>) msg
							.getContentObject());
					/**
					 * envoi une reponse pour stoper l'envoi du planning pour
					 * eviter la saturation et le crash
					 */
					ACLMessage reponsemessage = new ACLMessage(
							ACLMessage.INFORM);
					reponsemessage.setConversationId("stop_planning");
					reponsemessage.setContent("stop");
					AID dummyAid = new AID();
					/**
					 * l'adresse ip du destinataire se trouve dans la constante
					 * ipAdresse
					 */
					dummyAid.setName("agentController@" + Const.ipAdress
							+ ":1099/JADE");
					dummyAid.addAddresses("http://" + Const.ipAdress
							+ ":7778/acc");
					reponsemessage.addReceiver(dummyAid);
					send(reponsemessage);
					stop = true;

				} catch (UnreadableException e) {
					Log.i("Agent_Gestion", "ACLMessage probleme");
					e.printStackTrace();
				}
				block();

			} else {
				block();
			}
		}

		public boolean done() {
			return stop;
		}
	}

}
