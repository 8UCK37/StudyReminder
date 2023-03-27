package com.example.studyreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studyreminder.R;
import com.example.studyreminder.dao.DbHelper;

public class AddQuestionsActivity extends AppCompatActivity {
    private ImageView back;
    private EditText Topic,Question;
    private TextView saveBtn;
    private String topic,question;
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
}