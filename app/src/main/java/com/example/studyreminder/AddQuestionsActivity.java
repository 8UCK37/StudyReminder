package com.example.studyreminder;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studyreminder.R;
import com.example.studyreminder.dao.DbHelper;

import java.util.ArrayList;

public class AddQuestionsActivity extends AppCompatActivity {
    private ImageView back,uploadIcon;
    private EditText Topic,Question;
    private TextView saveBtn,uploadPrompt,setReminderBtn;
    private String topic,question;
    public static ArrayList<QuestionModel> questionList;
    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);
        db=new DbHelper(this);

        back=findViewById(R.id.back_tap);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddQuestionsActivity.this,MainActivity.class));
            }
        });

        uploadIcon=findViewById(R.id.uploadIcon);

        uploadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileDialog(view);
            }
        });

        uploadPrompt=findViewById(R.id.uploadText);

        uploadPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileDialog(view);
            }
        });

        setReminderBtn=findViewById(R.id.setReminder);
        setReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionList=MainActivity.questionModelList;
                if(questionList.size()>=1){
                    alarm();
                    Toast.makeText(getApplicationContext(),"Reminder set",Toast.LENGTH_SHORT).show();
                }
            }
        });


        Topic=findViewById(R.id.topicAdd);
        Question=findViewById(R.id.question);

        saveBtn=findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topic=Topic.getText().toString();
                question=Question.getText().toString();

                if(topic.length()==0){
                    Topic.setError("Topic Can't be empty");
                }else if(question.length()==0){
                    Question.setError("Question Can't be empty");
                }else{
                    Boolean checkUpdate = db.insertQuestionData(topic,question);
                    if(checkUpdate){
                        Toast.makeText(getApplicationContext(), "Question added GLHF", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddQuestionsActivity.this,AddQuestionsActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(), "Please use the update Button Instead", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    ActivityResultLauncher<Intent> sActivityResultLauncher =  registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData();
                        Uri uri = data.getData();
                    }
                }

            }


    );
    public void openFileDialog(View view){
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("*/*");
        data = Intent.createChooser(data,"Choose a file");
        sActivityResultLauncher.launch(data);
    }
    public void alarm(){

        long timeNow = System.currentTimeMillis();
        //need to randomize this time and everytime a button is pressed a new alarm is set
        long tensecinMillis = 1000*3;
        Intent intent = new Intent(AddQuestionsActivity.this,ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddQuestionsActivity.this,0,intent,PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                timeNow+tensecinMillis,pendingIntent);

    }
}