package electionmns.com.electionappmns.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

import electionmns.com.electionappmns.Activities.SplashScreenActivity;
import electionmns.com.electionappmns.R;


/**
 * Created by Neha on 8/27/2016.
 */
public class PushService extends FirebaseMessagingService {
    private static final String TAG = "PushInstanceDService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        System.out.println("--------remotemessagefrom---"+remoteMessage.getFrom()+"|"+remoteMessage.getNotification().getBody());
        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification());
    }

    //This method is only generating push notification

    private void sendNotification(RemoteMessage.Notification notiObject) {
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder=null;
//        if(notiObject.getBody().length()>30) {
            notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle(notificationBuilder)
                    .bigText(notiObject.getBody())
                    .setBigContentTitle(notiObject.getTitle())
                    .setSummaryText(""))
                    .setContentTitle(notiObject.getTitle())
                    .setContentText(notiObject.getBody())
                    .setSmallIcon(R.mipmap.ic_launcher);
//        }else {
//
//             notificationBuilder = new NotificationCompat.Builder(this)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle(notiObject.getTitle())
//                    .setContentText(notiObject.getBody())
//                    .setAutoCancel(true)
//                    .setSound(defaultSoundUri)
//                    .setContentIntent(pendingIntent);
//        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
//        String pattern = "dd-M-yyyy hh:mm aaa";
//        final String dateInString = new SimpleDateFormat(pattern).format(new Date());
//        String insertquery = "\n" +
//                "insert into classdata(Name,Subjects,Division) " +
//                "values('" + notiObject.getTitle() + "','" + notiObject.getBody() + "" +
//                "','" + dateInString + "')";
//        System.out.println("------insert query----" + insertquery);
//        String path = AppSettings.getdburl(getApplicationContext());
//        final SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase(path, Context.MODE_PRIVATE, null);
//        mydatabase.execSQL(insertquery);
    }
}
