package com.enterat.gcm;

import com.enterat.R;
import com.enterat.interfaces.LoginActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class GCMIntentService extends IntentService 
{
	private static final int NOTIF_ALERTA_ID = 1;

	public GCMIntentService() {
        super("GCMIntentService");
    }
	
	@Override
    protected void onHandleIntent(Intent intent) 
	{
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        
        String messageType = gcm.getMessageType(intent);
        Bundle extras = intent.getExtras();

        if (!extras.isEmpty()) 
        {  
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) 
            {
            	mostrarNotification(extras.getString("msg"));
            }
        }
        
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }
	
	private void mostrarNotification(String msg) 
	{
		NotificationManager mNotificationManager =    
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 
		
		NotificationCompat.Builder mBuilder = 
			new NotificationCompat.Builder(this)  
				.setSmallIcon(R.drawable.ic_launcher)  
				.setContentTitle("EnteraT - Tienes un nuevo aviso")  
				.setContentText(msg);
		
		Intent notIntent =  new Intent(this, LoginActivity.class);    
		PendingIntent contIntent = PendingIntent.getActivity(     
				this, 0, notIntent, 0);   
		mBuilder.setAutoCancel(true);
		mBuilder.setContentIntent(contIntent);
		
		mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
		
    }
	
	
	
}
