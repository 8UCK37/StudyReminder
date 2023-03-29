package com.example.studyreminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.studyreminder.dao.DbHelper;

import java.util.ArrayList;
import java.util.Collections;

public class ReminderBroadcast extends BroadcastReceiver {
    public static ArrayList<QuestionModel> questionList;
    DbHelper db;
    @SuppressLint("MissingPermission")

    @Override
    public void onReceive(Context context, Intent intent) {

        questionList=MainActivity.questionModelList;
        Collections.shuffle(questionList);
        for(QuestionModel qm : questionList){
            System.out.println(qm.getTopic() + " : " + qm.getQuestion());
        }
        QuestionModel qm = questionList.get(0);
        System.out.println("first ele"+qm.getTopic() + " : " + qm.getQuestion());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify-me")
                .setSmallIcon(R.drawable.exam)
                .setContentTitle(qm.getTopic())
                .setContentText(qm.getQuestion())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent buttonIntentOne = new Intent(context, NotificationReceiver.class);
        buttonIntentOne.setAction("ACTION_BUTTON_CLICK");
        buttonIntentOne.putExtra("notificationId", 0);
        buttonIntentOne.putExtra("toastMsg", "I Know!");
        PendingIntent buttonPendingIntentOne = PendingIntent.getBroadcast(context, 0, buttonIntentOne, PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.baseline_how_to_reg_24, "I Know", buttonPendingIntentOne);

        Intent buttonIntentTwo = new Intent(context, NotificationReceiver.class);
        buttonIntentTwo.setAction("ACTION_BUTTON_CLICK");
        buttonIntentTwo.putExtra("notificationId", 0);
        buttonIntentTwo.putExtra("toastMsg", "I Forgor!");
        PendingIntent buttonPendingIntentTwo = PendingIntent.getBroadcast(context, 1, buttonIntentTwo, PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.baseline_how_to_reg_24, "I Forgor", buttonPendingIntentTwo);

        Intent buttonIntentThree = new Intent(context, NotificationReceiver.class);
        buttonIntentThree.setAction("ACTION_BUTTON_CLICK");
        buttonIntentThree.putExtra("notificationId", 0);
        buttonIntentThree.putExtra("toastMsg", "IDC!");
        PendingIntent buttonPendingIntentThree = PendingIntent.getBroadcast(context, 2, buttonIntentThree, PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.baseline_how_to_reg_24, "IDC!", buttonPendingIntentThree);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent newIntent = new Intent(context, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, newIntent, PendingIntent.FLAG_IMMUTABLE);
        long newAlarmTime = System.currentTimeMillis() + 1000*3;
        alarmManager.set(AlarmManager.RTC_WAKEUP, newAlarmTime, pendingIntent);

        NotificationManagerCompat notiManager = NotificationManagerCompat.from(context);

        notiManager.notify(200, builder.build());
    }

}
