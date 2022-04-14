package com.example.pythonourses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.pythonourses.Parser.ParseAdapter;
import com.example.pythonourses.Parser.ParseItem;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar navigationBar;
    private FragmentManager fragmentManager;

    private ArrayList<ParseItem> parseTitle = new ArrayList<>();
    private ArrayList<ParseItem> parseLecture;
    private ArrayList<ArrayList<ParseItem>> lecturesArray = new ArrayList<ArrayList<ParseItem>>();

    private Runnable runnable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigationBar = findViewById(R.id.navigation_bar);

        runnable = new Runnable() {
            @Override
            public void run() {
                parseData("https://metanit.com/python/tutorial/");
            }
        };
        Thread parserThread = new Thread(runnable);
        parserThread.start();

        if(savedInstanceState == null){
            navigationBar.setItemSelected(R.id.home, true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        }

        navigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;
                switch(id){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.tasks:
                        fragment = new TasksFragment();
                        break;
                    case R.id.lectures:
                        fragment = new LecturesFragment(parseTitle, lecturesArray);
                        break;
                }

                if(fragment != null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }
            }
        });

    }

    private void parseData(String url){
        try{
            Document document = Jsoup.connect(url).get();

            Elements data = document.getElementsByClass("content").select("li");
            int counter = 1;
            int size = data.size();
            for (int i = 0; i < size; i++){
                String text = data
                        .select("a")
                        .eq(i)
                        .text();
                if(text.contains("Глава")){

                    String number = text.substring(6, text.indexOf('.'));
                    String title = text.substring(text.indexOf('.')+2);
                    parseTitle.add(new ParseItem(number, title));
                    counter = 1;
                    if(i > 0) lecturesArray.add(parseLecture);
                    parseLecture = new ArrayList<>();

                }else{

                    String number = String.valueOf(counter);
                    String title = text.substring(text.indexOf('.')+1);
                    String lectureUrl = url + data.select("a")
                            .eq(i)
                            .attr("href");
                    parseLecture.add(new ParseItem(number, title, lectureUrl));
                    counter++;

                }
            }
            lecturesArray.add(parseLecture);
        }catch (IOException e){
            e.printStackTrace();
            Log.d("MyLog", e.getMessage());
        }
    }



}