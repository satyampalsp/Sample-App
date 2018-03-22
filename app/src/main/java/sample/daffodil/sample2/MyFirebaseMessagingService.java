package sample.daffodil.sample2;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by DAFFODIL-29 on 3/15/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d("From", ": " + remoteMessage.getFrom());
        Log.d("Notification", " Message Body: " + remoteMessage.getNotification().getBody());
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_logo)
                .setContentTitle("New Notification")
                .setContentText("Open app please");
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}