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
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.project.metier.Beans;
import com.project.metier.Const;
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
		addBehaviour(new GetPlanning(this));
		addBehaviour(new GroupeRevision(this));
		addBehaviour(new NotifyABSProf(this));
		addBehaviour(new UpdatePlanning());

		Beans.setAgentGestion(this);
	}

	/**
	 * Le smrtphone recoit le planning depuis le serveur
	 * 
	 *
	 */
	private class GetPlanning extends Behaviour {
		private boolean stop = false;

		public GetPlanning(Agent a) {
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
	 * Envoi une notification quand le prof est abscent
	 * 
	 * @author ProBook 450g2
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
			if (message != null) {
				Intent myIntent = new Intent(Agent_Gestion.this.context,
						com.project.metier.NotifyService.class);
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

	/**
	 * 
	 * @author ProBook 450g2
	 *
	 */
	private class UpdatePlanning extends Behaviour {

		@SuppressWarnings("unchecked")
		@Override
		public void action() {
			MessageTemplate model = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("update"));
			ACLMessage msgg = myAgent.receive(model);
			if (msgg != null) {

				/**
				 * rempli le planning et l'envoyer pour pourvoir etre afficher
				 */
				try {
					bean.setPlanningTab((ArrayList<String>) msgg
							.getContentObject());
					bean.setPlanningTabUpdated((ArrayList<String>) msgg
							.getContentObject());
					block();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else
				block();

		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return false;
		}

	}

	/**
	 * Behaviour qui envoi une notification pour signaler la creation d'un
	 * groupe
	 * 
	 * @author ProBook 450g2
	 *
	 */
	private class CreatGroupeBehaviour extends OneShotBehaviour {
		private String name;
		private String jour;
		private String heur;
		private String module;
		private ArrayList<String> infoArray;

		public CreatGroupeBehaviour(String module, String jour, String heur,
				String name) {
			super();
			this.name = name;
			this.jour = jour;
			this.heur = heur;
			this.module = module;
			infoArray = new ArrayList<String>();
			infoArray.add(this.name);
			infoArray.add(this.module);
			infoArray.add(this.jour);
			infoArray.add(this.heur);
		}

		public void action() {

			ACLMessage creationMessage = new ACLMessage(ACLMessage.INFORM);
			creationMessage.setConversationId("creat");
			try {
				creationMessage.setContentObject(infoArray);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * creationMessage.setContent(this.name + "1" + this.module + "2" +
			 * this.jour + "3" + this.heur);
			 */
			AID dummyAid = new AID();
			dummyAid.setName("agentScolar@" + Const.ipAdress + ":1099/JADE");
			dummyAid.addAddresses("http://" + Const.ipAdress + ":7778/acc");
			creationMessage.addReceiver(dummyAid);
			send(creationMessage);
		}

	}

	/**
	 * methode qui est appelé pour la creation du groupe
	 */
	public void creatGroupe(String module, String jour, String heur, String name) {
		addBehaviour(new CreatGroupeBehaviour(module, jour, heur, name));
	}

	/**
	 * Comportement qui envoie une demande d'aide !
	 */
	private class HelpMeBehaviour extends OneShotBehaviour {
		private String message;
		private String module;
		private String name;
		private ArrayList<String> info;

		public HelpMeBehaviour(String message, String module, String name) {
			super();
			this.message = message;
			this.module = module;
			this.name = name;
			info = new ArrayList<String>();
		}

		public void action() {
			ACLMessage creationMessage = new ACLMessage(ACLMessage.INFORM);
			creationMessage.setConversationId("helpme");
			try {
				info.add(message);
				info.add(module);
				info.add(name);
				creationMessage.setContentObject(info);
			} catch (IOException e) {

				e.printStackTrace();
			}
			AID dummyAid = new AID();
			dummyAid.setName("agentScolar@" + Const.ipAdress + ":1099/JADE");
			dummyAid.addAddresses("http://" + Const.ipAdress + ":7778/acc");
			creationMessage.addReceiver(dummyAid);
			send(creationMessage);

		}

	}
	public void sendHelpMe(String message,String module,String name){
		addBehaviour(new HelpMeBehaviour(message, module, name));
		
	}
}
