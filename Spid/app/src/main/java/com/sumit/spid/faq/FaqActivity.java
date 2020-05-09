package com.sumit.spid.faq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sumit.spid.R;
import com.sumit.spid.history.History;
import com.sumit.spid.history.HistoryAdapter;
import com.sumit.spid.history.HistoryData;

import java.util.ArrayList;
import java.util.List;

public class FaqActivity extends AppCompatActivity {


    private RecyclerView faqRecyclerView;
    private List<FaqData> faqDataArrayList = new ArrayList<>();
    private FaqAdapter faqAdapter;
    RecyclerView.LayoutManager layoutManager;

    TextView faq_question_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Faq");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        faq_question_text  = findViewById(R.id.have_question_btn);
        faq_question_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent faqIntent = new Intent (FaqActivity.this,AskQuestionFaq.class);
                startActivity(faqIntent);
            }
        });

        faqRecyclerView = findViewById(R.id.faq_recycler_view);
        faqRecyclerView.setHasFixedSize(true);
        faqAdapter = new FaqAdapter(FaqActivity.this,faqDataArrayList);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        faqRecyclerView.setLayoutManager(layoutManager);
        faqRecyclerView.setAdapter(faqAdapter);
        faqRecyclerView.setItemAnimator(new DefaultItemAnimator());
        faqData();
    }

    private void faqData() {

        FaqData faqData = new FaqData(getString(R.string.question)+ getString(R.string.questio_one),getString(R.string.answer)+ getString(R.string.answer_one),"SPID","100","3");
        faqDataArrayList.add(faqData);
        faqData = new FaqData(getString(R.string.question)+ getString(R.string.questio_two),getString(R.string.answer)+ getString(R.string.answer_two),"SPID","98","0");
        faqDataArrayList.add(faqData);
        faqData = new FaqData(getString(R.string.question)+ getString(R.string.questio_three),getString(R.string.answer)+ getString(R.string.answer_two),"SPID","190","16");
        faqDataArrayList.add(faqData);
        faqData = new FaqData(getString(R.string.question)+ getString(R.string.questio_five),getString(R.string.answer)+ getString(R.string.answer_five),"SPID","190","16");
        faqDataArrayList.add(faqData);
        faqData = new FaqData(getString(R.string.question)+ getString(R.string.questio_six),getString(R.string.answer)+ getString(R.string.answer_six),"SPID","190","16");
        faqDataArrayList.add(faqData);
        faqData = new FaqData(getString(R.string.question)+ getString(R.string.questio_seven),getString(R.string.answer)+ getString(R.string.answer_seven),"SPID","190","16");
        faqDataArrayList.add(faqData);



        faqAdapter.notifyDataSetChanged();
    }
}
