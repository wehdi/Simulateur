package com.project.simulaturandroid;
/**
 * @author ProBook 450g2
 * 
 * Class Service qui envoi une notification
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class NotifyService extends Service {

	
	class MyBinder extends Binder {
		Service getService() {
			return NotifyService.this;
			
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
	
		return new MyBinder();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		/**
		 * Creation du notification manager
		 */
		  NotificationManager mgr=
		            (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		        Notification note=new Notification(R.drawable.ic_launcher,
		                                                        "Notif Univ",
		                                                        System.currentTimeMillis());
		         
		        /**
		         * Pending intent qui va s'ouvrir ares le click sur la notification
		         */
		        PendingIntent i=PendingIntent.getActivity(this, 0,
		                                                new Intent(this, NotifyMessage.class),
		                                                0);
		        
		        /**
		         * Envoi de la notification
		         */
		         
		        note.setLatestEventInfo(this, "Notification de l'univ",
		                                "Le prof est abscent", i);
		         
		        
		        mgr.notify(-1, note);
		        /**
		         * Arreter le service pour ne pas qu'il boucle
		         */
		       stopService(intent);
		        
		 
	}
	
	

}
