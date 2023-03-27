package com.example.studyreminder;

public class QuestionModel {

    private String  topic,question;


    //constructor
    public QuestionModel(String topic,String question) {
        this.topic=topic;
        this.question= question;

    }




    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String phoneNo) {
        this.question = question;
    }


}
