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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TasksActivity extends AppCompatActivity {

    private TextView topic, lectureText;
    private ImageButton backButton;
    private StringBuilder SBtext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();

    }

    public void init(){
        topic = findViewById(R.id.task_topic);
        backButton = findViewById(R.id.task_back_btn);
        lectureText = findViewById(R.id.task_textview);

        topic.setText(getIntent().getStringExtra("topic"));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Content content = new Content();
        content.execute();
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

                Elements data = document.getElementsByClass("mYVXT").select("span");
                int size = data.size();
                for (int i = 0; i < size; i++){
                    String text = data
                            .eq(i)
                            .text();
                    Log.d("MyLog", text);
                    SBtext.append(text).append("\n");
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.d("MyLog", e.getMessage());
            }
            return null;
        }
    }
}