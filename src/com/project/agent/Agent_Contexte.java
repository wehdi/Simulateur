package com.project.agent;

/**
 * @author ProBook 450g2
 * 
 *  Cette classe se charge de la gestion du contexte
 */
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import com.project.metier.Beans;

@SuppressWarnings("serial")
public class Agent_Contexte extends Agent {

	private Context context;
	private final String TAG = "Agent_Contexte";

	@Override
	protected void setup() {

		super.setup();
		/**
		 * Recupmeration du contexte
		 */
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			if (args[0] instanceof Context) {
				context = (Context) args[0];
			}
		}
		addBehaviour(new InfoEvents());
		addBehaviour(new WaitingEventChangeMode());

	}

	/**
	 * 
	 * Envoi des notifications lors du chagement de contexte
	 * 
	 * @author ProBook 450g2
	 *
	 */
	class InfoEvents extends CyclicBehaviour {

		@Override
		public void action() {

			/**
			 * Attente de message pour l'envoi d'une notification
			 */

			MessageTemplate modele = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("notify"));
			jade.lang.acl.ACLMessage reponse = myAgent.receive(modele);
			if (reponse != null) {
				/**
				 * Lance l'intent qui envoi la notification (service)
				 */
				Intent myIntent = new Intent(Agent_Contexte.this.context,
						com.project.metier.NotifyService.class);
				/**
				 * Ajout des msgs de notifications
				 */
				myIntent.putExtra("titre", "Bienvenu a l'université");
				myIntent.putExtra("corp_titre", "Bienvenu " + Beans.getLogin());
				myIntent.putExtra("corp", "Voici les nouvelles informations :");
				Agent_Contexte.this.context.startService(myIntent);

				block();
			} else {

				block();
			}

		}

	}

	private class WaitingEventChangeMode extends CyclicBehaviour {

		public void action() {
			MessageTemplate modele = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("mode"));
			jade.lang.acl.ACLMessage reponse = myAgent.receive(modele);
			if (reponse != null) {
				String mode = reponse.getContent().toString();
				if (mode.equals("0")) {
					AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);   
					        am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
					          
				} else {
					AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);   
			        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				}

			} else
				block();

		}
	}
}