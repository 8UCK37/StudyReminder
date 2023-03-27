package com.example.studyreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("toastMsg");
        Toast.makeText(context.getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }
}
