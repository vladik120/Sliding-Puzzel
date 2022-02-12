package com.example.finalproject;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class MissedYouWorker extends Worker {
    private String FILENAME="EnteredApp.txt";
    String CHANNEL_ID = "my_channel_01";
    NotificationManager mNotiMgr;
    Boolean check=false;
    public MissedYouWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() { //runs in a different thread than the main thread
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel (CHANNEL_ID,CHANNEL_ID,NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = this.getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        while (!check) {
            if (!MainActivity.isActivityVisible()) {
                try {
                    Thread.sleep(10000); //just for testing, should be around 3 days.

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //display notification.
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext(), CHANNEL_ID);
                builder.setContentTitle("Sliding Puzzle");
                builder.setContentText("We missed you, please come back!");
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this.getApplicationContext());
                managerCompat.notify(1, builder.build());
                check=true;
            }

        }
        return Result.success();
    }
}