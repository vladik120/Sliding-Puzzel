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
        try {
            FileInputStream fin = this.getApplicationContext().openFileInput(FILENAME);
            ByteBuffer bf = ByteBuffer.allocate(1000 * 10);
            int numberOfBytes = fin.read(bf.array());
            if (numberOfBytes != -1) {
                String convert = new String(bf.array());
                convert = convert.substring(0, numberOfBytes);
                if (convert.equals("False")) {
                    //display notification.
                    //test
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext(),CHANNEL_ID);
                    builder.setContentTitle("Test title");
                    builder.setContentText("Test Text");
                    builder.setSmallIcon(R.drawable.ic_launcher_background);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this.getApplicationContext());
                    managerCompat.notify(1,builder.build());
                }
                return Result.success();
            }
            fin.close();
            return null; //here write what we wanna do as a service.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.failure();
    }
}
