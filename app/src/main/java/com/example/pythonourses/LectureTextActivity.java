package com.example.pythonourses;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pythonourses.Parser.ParseItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LectureTextActivity extends AppCompatActivity {

    private TextView topic, lectureText;
    private ImageButton backButton;
    private StringBuilder SBtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_text);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
    }

    public void init(){
        topic = findViewById(R.id.lecture_topic);
        backButton = findViewById(R.id.lecture_back_btn);
        lectureText = findViewById(R.id.lecture_textview);

        topic.setText(getIntent().getStringExtra("topic"));
        /*url = getIntent().getStringExtra("lectureUrl");*/

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Content content = new Content();
        content.execute();

    }

    private StringBuilder parseData(String url){
        StringBuilder parsedText = new StringBuilder();

        try{
            Document document = Jsoup.connect(url).get();

            Elements data = document.getElementsByClass("articleText").select("p");
            int size = data.size();
            for (int i = 0; i < size; i++){
                String text = data
                        .eq(i)
                        .text();
                parsedText.append(text);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d("MyLog", e.getMessage());
        }
        return parsedText;
    }

    private class Content extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            lectureText.setText(SBtext);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                SBtext = new StringBuilder();
                String detailUrl = getIntent().getStringExtra("lectureUrl");
                Document document = Jsoup.connect(detailUrl).get();

                Elements data = document.getElementsByClass("item center menC").select("p");
                data.remove(data.last());
                int size = data.size();
                for (int i = 0; i < size; i++){
                    String text = data
                            .eq(i)
                            .text();
                    SBtext.append(text);
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.d("MyLog", e.getMessage());
            }
            return null;
        }
    }


}