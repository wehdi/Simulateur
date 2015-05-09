package com.project.simulaturandroid;
/**
 * 
 * Class Beans contien different getter et setter
 * 
 *  @author ProBook 450g2
 */

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Beans implements Serializable, jade.util.leap.Serializable {
	
		private static ArrayList<String> planningTab = new ArrayList<>();
		private static String login = null;
		private static String mdp = null;
		
		public static String getLogin() {
			return login;
		}

		public static void setLogin(String login) {
			Beans.login = login;
		}

		public static String getMdp() {
			return mdp;
		}

		public static void setMdp(String mdp) {
			Beans.mdp = mdp;
		}

		
		public Beans() {	
	}

		public ArrayList<String> getPlanningTab() {
			return planningTab;
		}

		public void setPlanningTab(ArrayList<String> planningTab) {
			this.planningTab =planningTab;
		}
}


