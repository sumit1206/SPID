package com.sumit.spid.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sumit.spid.R;
import com.sumit.spid.home.HomeData.PeopleSaysData;
import java.util.ArrayList;

public class PeopleSaysAdapter extends RecyclerView.Adapter<PeopleSaysAdapter.PeopleSaysViewHolder>{

    ArrayList<PeopleSaysData> peopleSaysDataArrayList;
    private Context peopleSaysContext;

    public PeopleSaysAdapter(ArrayList<PeopleSaysData> peopleSaysDataArrayList, Context peopleSaysContext) {
        this.peopleSaysDataArrayList = peopleSaysDataArrayList;
        this.peopleSaysContext = peopleSaysContext;
    }

    @NonNull
    @Override
    public PeopleSaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_people_says, parent, false);
        return new PeopleSaysAdapter.PeopleSaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleSaysViewHolder holder, int position) {

        holder.peopleQuestion.setText(peopleSaysDataArrayList.get(position).getPeopleQuestion());
        holder.peopleAnswer.setText(peopleSaysDataArrayList.get(position).getPeopleAnswer());

    }

    @Override
    public int getItemCount() {
        return peopleSaysDataArrayList.size();
    }

    public class PeopleSaysViewHolder extends RecyclerView.ViewHolder{

        TextView peopleQuestion,peopleAnswer;
        public PeopleSaysViewHolder(@NonNull View itemView) {
            super(itemView);

            peopleQuestion = itemView.findViewById(R.id.people_question);
            peopleAnswer = itemView.findViewById(R.id.people_answer);
        }
    }
}
