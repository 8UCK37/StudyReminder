package com.example.studyreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyreminder.R;

import java.util.ArrayList;


public class QuestionCardAdapter extends RecyclerView.Adapter<QuestionCardAdapter.QuestionCardViewHolder>  {
    private Context context;
    private setClickListener clickListener;
    private ArrayList<QuestionModel> questionlist;

    public QuestionCardAdapter(ArrayList<QuestionModel> questionlist,setClickListener clickListener) {
        this.questionlist = questionlist;
        this.clickListener=clickListener;
    }


    @NonNull
    @Override
    public QuestionCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.question_card,parent,false);
        return new QuestionCardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionCardViewHolder holder, int position) {
        QuestionModel contact=questionlist.get(position);
        String question = String.valueOf(contact.getQuestion());
        holder.Topic.setText(String.valueOf(contact.getTopic()));
        holder.Question.setText(question);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onEditClicked(contact);
            }
        });
        holder.dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onDeleteClicked(contact);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (questionlist == null) {
            return 0;
        } else {
            return questionlist.size();
        }
    }

    public interface setClickListener{
        void onDeleteClicked(QuestionModel c);

        void onPlusClicked(QuestionModel c);

        void onMinusClicked(QuestionModel c);

        void onEditClicked(QuestionModel c);
    }



    public class QuestionCardViewHolder extends RecyclerView.ViewHolder {
        TextView Topic, Question;
        ImageView dlt,edit;

        public QuestionCardViewHolder(@NonNull View itemView) {

            super(itemView);
            edit=itemView.findViewById(R.id.EditBtn);
            Topic =itemView.findViewById(R.id.topicDisp);
            Question =itemView.findViewById(R.id.quesDisp);
            dlt=itemView.findViewById(R.id.DeleteBtn);
        }
    }
}
