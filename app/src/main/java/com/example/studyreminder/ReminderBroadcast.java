package com.example.studyreminder;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Collections;

public class ReminderBroadcast extends BroadcastReceiver {
    public static ArrayList<QuestionModel> questionList;
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
        NotificationManagerCompat notiManager = NotificationManagerCompat.from(context);

        notiManager.notify(200, builder.build());
    }
}
