package com.example.pythonourses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

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
    private ArrayList<ParseItem> parseTasks = new ArrayList<>();

    String[] urlArray = new String[]
            {"/%D0%BB-%D1%80-1-%D0%B2%D0%B2%D0%B5%D0%B4%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B2-%D1%8F%D0%B7%D1%8B%D0%BA-%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F-python",
            "/%D0%BB-%D1%80-2-%D0%BC%D0%B0%D1%82%D0%B5%D0%BC%D0%B0%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D0%B5-%D0%BE%D0%BF%D0%B5%D1%80%D0%B0%D1%86%D0%B8%D0%B8-%D0%B2-python",
            "/%D0%BB-%D1%80-3-%D1%81%D1%82%D1%80%D1%83%D0%BA%D1%82%D1%83%D1%80%D0%B0-%D0%B2%D0%B5%D1%82%D0%B2%D0%BB%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B2-python",
            "/%D0%BB-%D1%80-4-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0-%D1%81-%D1%86%D0%B8%D0%BA%D0%BB%D0%B0%D0%BC%D0%B8-%D0%B2-python",
            "/%D0%BB-%D1%80-5-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0-%D1%81%D0%BE-%D1%81%D1%82%D1%80%D0%BE%D0%BA%D0%B0%D0%BC%D0%B8-%D0%B2-python",
            "/%D0%BB-%D1%80-6-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0-%D1%81%D0%BE-%D1%81%D0%BF%D0%B8%D1%81%D0%BA%D0%B0%D0%BC%D0%B8-%D0%BE%D0%B4%D0%BD%D0%BE%D0%BC%D0%B5%D1%80%D0%BD%D1%8B%D0%B5-%D0%BC%D0%B0%D1%81%D1%81%D0%B8%D0%B2%D1%8B",
            "/%D0%BB-%D1%80-7-%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%B8-%D0%B8-%D0%BF%D1%80%D0%BE%D1%86%D0%B5%D0%B4%D1%83%D1%80%D1%8B-%D0%B2-python",
            "/%D0%BB-%D1%80-8-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0-%D1%81-%D0%B4%D0%B2%D1%83%D0%BC%D0%B5%D1%80%D0%BD%D1%8B%D0%BC%D0%B8-%D0%BC%D0%B0%D1%81%D1%81%D0%B8%D0%B2%D0%B0%D0%BC%D0%B8"
    };


    private Runnable runnableLectures;
    private Runnable runnableTasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigationBar = findViewById(R.id.navigation_bar);

        runnableLectures = new Runnable() {
            @Override
            public void run() {
                parseLecturesData("https://metanit.com/python/tutorial/");
            }
        };
        Thread parserThread = new Thread(runnableLectures);
        parserThread.start();

        runnableTasks = new Runnable() {
            @Override
            public void run() {
                parseTasksData("https://sites.google.com/site/moiboarkin/%D0%BB%D0%B0%D0%B1%D0%BE%D1%80%D0%B0%D1%82%D0%BE%D1%80%D0%BD%D1%8B%D0%B5-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%8B/5-%D0%BA%D1%83%D1%80%D1%81/%D0%BB%D0%B0%D0%B1%D0%BE%D1%80%D0%B0%D1%82%D0%BE%D1%80%D0%BD%D1%8B%D0%B5-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%8B-%D0%BF%D0%BE-python");
            }
        };
        Thread parserThread2 = new Thread(runnableTasks);
        parserThread2.start();

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
                        fragment = new TasksFragment(parseTasks);
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

    private void parseLecturesData(String url){
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

    private void parseTasksData(String url){
        try {
            Document document = Jsoup.connect(url).get();

            Elements data = document.getElementsByClass("tyJCtd mGzaTb baZpAe").select("a");
            data.remove(data.last());
            int size = data.size();
            for (int i = 0; i < size; i++) {
                String text = data
                        .eq(i)
                        .text();
                String number = text.substring(6, text.indexOf(')'));
                String title = text.substring(text.indexOf(')') + 2);
                String taskUrl = url + urlArray[i];
                parseTasks.add(new ParseItem(number, title, taskUrl));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("MyLog", e.getMessage());
        }
    }
}