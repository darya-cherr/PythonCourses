package com.example.pythonourses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pythonourses.Parser.ParseAdapter;
import com.example.pythonourses.Parser.ParseItem;

import java.util.ArrayList;

public class LecturesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ParseAdapter parseAdapter;
    private TextView topic;
    private ImageButton backButton;
    private ArrayList<ParseItem> lectures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        backButton = findViewById(R.id.lectures_back_btn);
        recyclerView = findViewById(R.id.lectures_recycler_view);
        recyclerView.setHasFixedSize(true);

        topic = findViewById(R.id.topic);
        topic.setText(getIntent().getStringExtra("topic"));
        lectures = (ArrayList<ParseItem>) getIntent().getSerializableExtra("lectures");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        parseAdapter = new ParseAdapter(lectures, this, "lectures");
        recyclerView.setAdapter(parseAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}