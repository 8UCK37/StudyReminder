package com.example.studyreminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.studyreminder.dao.DbHelper;
import com.example.studyreminder.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.PrintStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements QuestionCardAdapter.setClickListener{
    public static final String CHANNEL_ID="My Channel";
    public static final int NOTI_ID=42069;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    RecyclerView recyclerView;
    ArrayList<String> topics,questions;
    public static ArrayList<QuestionModel> questionModelList;
    DbHelper db;
    QuestionCardAdapter adapter;
    public static QuestionModel questionParcel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        alarm();
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.exam,null);
        BitmapDrawable bitmapDrawable =(BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddQuestionsActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        questionModelList=new ArrayList<>();
        recyclerView=findViewById(R.id.questionRecyclerView);

        adapter = new QuestionCardAdapter(initModel(questionModelList),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,activityIntent,0);

        Intent firstbroadCastIntent = new Intent (this,NotificationReceiver.class);
        firstbroadCastIntent.putExtra("toastMsg","OK nicu");
        PendingIntent btnOne = PendingIntent.getBroadcast(this,0,firstbroadCastIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent secondbroadCastIntent = new Intent (this,NotificationReceiver.class);
        secondbroadCastIntent.putExtra("toastMsg","better read up");
        PendingIntent btnSecond = PendingIntent.getBroadcast(this,1,secondbroadCastIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent thirdbroadCastIntent = new Intent (this,NotificationReceiver.class);
        thirdbroadCastIntent.putExtra("toastMsg","way to go");
        PendingIntent btnThird = PendingIntent.getBroadcast(this,2,thirdbroadCastIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager ntMng = (NotificationManager)  getSystemService(NOTIFICATION_SERVICE);
        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.exam)
                    .setContentText("test")
                    .setSubText("works")
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .addAction(R.mipmap.ic_launcher_round,"I know",btnOne)
                    .addAction(R.mipmap.ic_launcher_round,"I forgor",btnSecond)
                    .addAction(R.mipmap.ic_launcher_round,"Fuck this shit",btnThird)
                    .build();
            ntMng.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"new channel",NotificationManager.IMPORTANCE_HIGH));
        }else{
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.exam)
                    .setContentText("test")
                    .setSubText("works")
                    .build();

        }
        ntMng.notify(NOTI_ID,notification);

    }
    private ArrayList<QuestionModel> initModel(ArrayList<QuestionModel> list){
        db=new DbHelper(this);
        Cursor cursor=db.getdata();

        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"No data exists for this user",Toast.LENGTH_SHORT).show();
            return list;
        }else{
            while(cursor.moveToNext()){
                String topic=cursor.getString(1);
                String question=cursor.getString(2);
                QuestionModel c=new QuestionModel(topic,question);
                list.add(c);
            }

            return list;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onDeleteClicked(QuestionModel c) {

    }

    @Override
    public void onPlusClicked(QuestionModel c) {

    }

    @Override
    public void onMinusClicked(QuestionModel c) {

    }

    @Override
    public void onEditClicked(QuestionModel c) {

    }
    private void createNotificationChannel(){
            CharSequence  name = "new channel";
            String description = "reminder";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel= new NotificationChannel("notify-me",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

    }
    private void alarm(){
        long timeNow = System.currentTimeMillis();
        long tensecinMillis = 1000*10;
        Intent intent = new Intent(MainActivity.this,ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                timeNow+tensecinMillis,pendingIntent);
    }
}