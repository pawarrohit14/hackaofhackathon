package info.androidhive.navigationdrawer.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import backend.gppmon.handler.ActionMessage;
import backend.gppmon.handler.BroadcastMessage;
import backend.gppmon.handler.MessageHandlerImpl;
import backend.gppmon.handler.RTMProviderImpl;
import backend.gppmon.handler.RTMResponseMessage;
import de.greenrobot.event.EventBus;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.event.BroadcastEvent;
import info.androidhive.navigationdrawer.event.LoginEvent;
import info.androidhive.navigationdrawer.service.parser.ResponseParser;
import info.androidhive.navigationdrawer.utils.AppConstants;
import info.androidhive.navigationdrawer.utils.ApplicationController;


/**
 * Created by Support305 on 2/9/2018.
 */

public class BroadcastService extends Service {

    public static final String TAG = "BROADCASTSERVICE";
    public static final String MY_PREFS_NAME = "eagle_eye_prefs";
    private ApplicationController applicationController;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Runnable r = new Runnable() {
            public void run() {
                applicationController = (ApplicationController) getApplicationContext();
                try {
                    RTMProviderImpl.getInstance().setLoginURL(AppConstants.LOGIN_URL);
                    RTMProviderImpl.getInstance().setMessageHandler(new SimpleMessageHandler());
                    LoginToRTMServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t = new Thread(r);
        t.start();
        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed

    }

    public void LoginToRTMServer() {
        //Store login state & userame in Pref
        SharedPreferences editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String userName = editor.getString("USER", "");
        String password = editor.getString("PASS", "");
        try {
            RTMProviderImpl.getInstance().login(userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class SimpleMessageHandler extends MessageHandlerImpl {
        @Override
        public void subscribed(String channel) {
            Log.i("SUBSCRIBED", channel);
            //Can be used by any application to know whether channel is subscribed or not
        }

        @Override
        public void handleAction(ActionMessage msg) {
            //Implement a logic for handling action message. To be  used by consumer
        }

        @Override
        public void handleEvent(BroadcastMessage msg, String ap) {
            Log.v(TAG, "Get message in handleEvent from server :: " + msg.getMessage());
            showNotification();
            playAlertTone();
            BroadcastEvent broadcastEvent = new BroadcastEvent(msg);
            EventBus.getDefault().post(broadcastEvent);

        }

        @Override
        public void handleResponse(RTMResponseMessage msg, String app) {
            //To be used by consumer. This will contain response.
            Log.v(TAG, "Get message in handleResponse from server :: " + msg.getResult());
            //Interface list
            if (msg.getMsgCode() == 2500) {
                applicationController.dismissProgressDialog();
                ResponseParser responseParser = new ResponseParser();
                responseParser.handleInterfaceResponse(msg);

            } else if (msg.getMsgCode() == 2501) {
                applicationController.dismissProgressDialog();
                ResponseParser responseParser = new ResponseParser();
                responseParser.handleQueueResponse(msg);

            } else if (msg.getMsgCode() == 2512) {
                applicationController.dismissProgressDialog();
                ResponseParser responseParser = new ResponseParser();
                responseParser.handleSysErrorResponse(msg);

            } else if (msg.getMsgCode() == 2505) {
                applicationController.dismissProgressDialog();
                ResponseParser responseParser = new ResponseParser();
                responseParser.handleBackoutResponse(msg);
            }
        }

        @Override
        public void connected() {
            super.connected();
            Log.i("ROHIT", "Connected to Server");
            EventBus.getDefault().post(new LoginEvent("login"));
        }
    }

    public void showNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("New Alert Notification");
        mBuilder.setContentText("You have received one alert !");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


        mBuilder.setContentIntent(contentIntent);
       // mBuilder.setAutoCancel(true);
        mNotificationManager.notify(12, mBuilder.build());
    }

    //ringtone
    private void playAlertTone() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(this, notification);
        r.play();
    }

}
