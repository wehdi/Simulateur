package com.project.agent;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.project.simulaturandroid.Generate_Planning;

@SuppressWarnings("serial")
public class Agent_Gestion extends Agent {
	/**
	 * 
	 */
	private final String TAG = "Agent_Gestion";
	private final String PREFS_AGENT_NAME = "AGENT_NAME";
	private final String PREFS_FILE_NAME = "jademos";
	private Context context;
	private String agentName = "agentGestion";
	private String ipAddress = "192.168.2.10";
	public static String PREFS_HOST_ADDRESS = "HOST_ADDRESS";
	private ArrayList<String> content = null;
	private ArrayList<String> jourTab = null;
	private ArrayList<String> heurTab = null;
	private ArrayList<String> moduleTab = null;
	private Generate_Planning generate_Planning;

	@Override
	protected void setup() {
		super.setup();
		generate_Planning = new Generate_Planning();
		jourTab = new ArrayList<String>();
		heurTab = new ArrayList<String>();
		moduleTab = new ArrayList<String>();
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

	class Do extends Behaviour {
		private boolean stop = false;

		public Do(Agent a) {
			super(a);
		}

		public void action() {
			ACLMessage msg = myAgent.receive();
			if (msg != null) {
				ArrayList<String> content2 = new ArrayList<String>();
				try {
					content = (ArrayList<String>) msg.getContentObject();
					Log.i(TAG, "yooo" + content.get(0));
					jourTab.addAll(content.subList(0,
							content.indexOf("day") - 1));
					Log.i(TAG, "lalala" + jourTab.get(0) + jourTab.get(6));
					heurTab.addAll(content.subList(content.indexOf("day") + 1,
							content.indexOf("heur") - 1));
					moduleTab.addAll(content.subList(
							content.indexOf("heur") + 1, content.size() - 1));

					content2.addAll(generate_Planning.setPlanning(jourTab,
							heurTab, moduleTab));
					Log.i(TAG,
							" mehdi" + content2.get(0) + " " + content2.get(1)
									+ content2.get(55));
					generate_Planning.setPp(content2);
					stop = true;
				} catch (UnreadableException e) {
					Log.i("Agent_Gestion", "ACLMessage probleme");
					e.printStackTrace();
				}

			} else {
				Log.i("Agent_gestion", "le else est appele");
				block();
			}

		}

		public boolean done() {
			return stop;

		}
	}

	private ArrayList<String> setPlanning(ArrayList<String> jourTab,
			ArrayList<String> heurTab, ArrayList<String> moduleTab) {
		ArrayList<String> plan;
		plan = new ArrayList<String>();
		for (int j = 0; j < 65; j++) {
			plan.add(" aa");
		}

		plan.set(0, "   ");
		plan.set(1, "Dimanche");
		plan.set(2, "Lundi");
		plan.set(3, "Mardi");
		plan.set(4, "Mercredi");
		plan.set(5, "Jeudi");
		plan.set(6, "Samedi");

		plan.set(7, "8h-9h");
		plan.set(14, "9h-10h");
		plan.set(21, "10h-11h");
		plan.set(28, "11h-12");
		plan.set(35, "12h-13h");
		plan.set(42, "13h-14h");
		plan.set(49, "14h-15h");
		plan.set(56, "15h-16h");
		while (!jourTab.isEmpty()) {
			if (jourTab.get(0).equalsIgnoreCase("Dimanche")) {
		
				
				if (heurTab.get(0).equalsIgnoreCase("8")) {
					plan.set(8, moduleTab.get(0));
				}

				else if (heurTab.get(0).equals("9")) {
					plan.set(15, moduleTab.get(0));
				} else if (heurTab.get(0).equals("10")) {
					plan.set(22, moduleTab.get(0));
				} else if (heurTab.get(0).equals("11")) {
					plan.set(29, moduleTab.get(0));
				} else if (heurTab.get(0).equals("12")) {
					plan.set(36, moduleTab.get(0));
				} else if (heurTab.get(0).equals("13")) {
					plan.set(43, moduleTab.get(0));
				} else if (heurTab.get(0).equals("14")) {
					plan.set(50, moduleTab.get(0));
				} else if (heurTab.get(0).equals("15")) {
					plan.set(57, moduleTab.get(0));
				}
			}
			/*
			 * else if (jourTab.get(0).equalsIgnoreCase("Lundi")) { if
			 * (heurTab.get(0).equals("8")) plan.add(9, moduleTab.get(0)); else
			 * if (heurTab.get(0).equals("9")) { plan.add(16, moduleTab.get(0));
			 * } else if (heurTab.get(0).equals("10")) { plan.add(23,
			 * moduleTab.get(0)); } else if (heurTab.get(0).equals("11")) {
			 * plan.add(30, moduleTab.get(0)); } else if
			 * (heurTab.get(0).equals("12")) { plan.add(37, moduleTab.get(0)); }
			 * else if (heurTab.get(0).equals("13")) { plan.add(44,
			 * moduleTab.get(0)); } else if (heurTab.get(0).equals("14")) {
			 * plan.add(51, moduleTab.get(0)); } else if
			 * (heurTab.get(0).equals("15")) { plan.add(58, moduleTab.get(0)); }
			 * }
			 */
			moduleTab.remove(0);
			jourTab.remove(0);
			heurTab.remove(0);
		}
		return plan;
	}

}
