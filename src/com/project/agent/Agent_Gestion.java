package com.project.agent;

/**
 * 
 * @author ProBook 450g2
 * 
 * Classe de gestion qui est chargé de :
 *  - Recevoir le planning depuis l'agent Controller
 *  -l'envoyer a la vu pour etre pour etre affiché
 *  - Envoyer une reponse a l'agent scoloar pour stop l'arrivé de msg
 *  -Gerer la revision l nbr de groupe qui existe
 * 
 */

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.project.simulaturandroid.Beans;
import com.project.simulaturandroid.Const;
import com.project.simulaturandroid.NombreGroupeBean;

@SuppressWarnings("serial")
public class Agent_Gestion extends Agent {
	private Context context;
	private final String TAG = "Agent_Gestion";
	private Beans bean;

	protected void setup() {
		super.setup();

		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			if (args[0] instanceof Context) {
				context = (Context) args[0];
			}
		}
		bean = new Beans();
		addBehaviour(new Do(this));
		addBehaviour(new GroupeRevision(this));
		addBehaviour(new NotifyABSProf(this));
	}

	/**
	 * Le smrtphone recoit le planning depuis le serveur
	 * 
	 *
	 */
	private class Do extends Behaviour {
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

	/**
	 * Comportement qui permet de recevoir le nombre de groupe actuellement
	 * entrain de reviser
	 */
	private class GroupeRevision extends Behaviour {
		NombreGroupeBean nbrGroupeBen = new NombreGroupeBean();

		public GroupeRevision(Agent_Gestion agent_Gestion) {

		}

		public void action() {

			MessageTemplate model = MessageTemplate.and(MessageTemplate
					.MatchPerformative(jade.lang.acl.ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("revision"));
			jade.lang.acl.ACLMessage msgNombrGroupe = receive(model);
			/**
			 * 
			 */
			if (msgNombrGroupe != null) {
				int nbrGroupe = Integer.parseInt(msgNombrGroupe.getContent()
						.toString());
				nbrGroupeBen.setNmbrGroupe(nbrGroupe);
			} else {
				block();
			}
		}

		@Override
		public boolean done() {

			return false;
		}

	}

	/**
	 * 
	 * 
	 */
	@SuppressWarnings("unused")
	private class NotifyABSProf extends Behaviour {

		public NotifyABSProf(Agent_Gestion agent_Gestion) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void action() {
			MessageTemplate model = MessageTemplate.and(MessageTemplate
					.MatchPerformative(jade.lang.acl.ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("abs"));
			jade.lang.acl.ACLMessage message = receive(model);
			/**
			 * 
			 */
			if (message != null) {
				Intent myIntent = new Intent(Agent_Gestion.this.context,
						com.project.simulaturandroid.NotifyService.class);
				/**
				 * Ajout des msgs de notifications
				 */
				myIntent.putExtra("titre", "Abscence du prof");
				myIntent.putExtra("corp_titre", "Notification d'abscence");
				myIntent.putExtra("corp",
						"Le prof est abscent changement dans le cours");
				Agent_Gestion.this.context.startService(myIntent);

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
}
