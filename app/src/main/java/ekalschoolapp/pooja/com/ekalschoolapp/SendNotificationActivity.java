package ekalschoolapp.pooja.com.ekalschoolapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendNotificationActivity extends AppCompatActivity implements View.OnClickListener{

    Button sendnotification;
    private int currentNotificationID = 0;
    private EditText etMainNotificationText, etMainNotificationTitle;
    private Button btnMainSendSimpleNotification, btnMainSendExpandLayoutNotification, btnMainSendNotificationActionBtn, btnMainSendMaxPriorityNotification, btnMainSendMinPriorityNotification, btnMainSendCombinedNotification, btnMainClearAllNotification;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private String notificationTitle;
    private String notificationText;
    private Bitmap icon;
    private int combinedNotificationCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);



        etMainNotificationText = (EditText) findViewById(R.id.etMainNotificationText);
        etMainNotificationTitle = (EditText) findViewById(R.id.etMainNotificationTitle);
        btnMainSendSimpleNotification = (Button) findViewById(R.id.btnMainSendSimpleNotification);

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_launcher);

        btnMainSendSimpleNotification.setOnClickListener(this);
        btnMainClearAllNotification.setOnClickListener(this);
        setNotificationData();
        setDataForSimpleNotification();
    }

    private void clearAllNotifications() {
        if (notificationManager != null) {
            currentNotificationID = 0;
            notificationManager.cancelAll();
        }
    }

    private void sendNotification() {
        Intent notificationIntent = new Intent(this, SendNotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        currentNotificationID++;
        int notificationId = currentNotificationID;
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0;
        notificationManager.notify(notificationId, notification);
    }

    private void setNotificationData() {
        notificationTitle = this.getString(R.string.app_name);
        notificationText = "Hello..This is a Notification Test";
        if (!etMainNotificationText.getText().toString().equals("")) {
            notificationText = etMainNotificationText.getText().toString();
        }
        if (!etMainNotificationTitle.getText().toString().equals("")) {
            notificationTitle = etMainNotificationTitle.getText().toString();
        }
    }
    private void setDataForSimpleNotification() {
        notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText);
        sendNotification();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMainSendSimpleNotification:
                setDataForSimpleNotification();
                break;
            case R.id.btnMainClearAllNotification:
                clearAllNotifications();
                break;
        }
    }

}
