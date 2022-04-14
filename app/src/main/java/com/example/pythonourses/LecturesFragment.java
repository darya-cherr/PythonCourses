package com.example.pythonourses;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pythonourses.Parser.ParseAdapter;
import com.example.pythonourses.Parser.ParseItem;

import java.util.ArrayList;

public class LecturesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<ParseItem> topics = new ArrayList<>();
    private static ArrayList<ArrayList<ParseItem>> lectures = new ArrayList<ArrayList<ParseItem>>();

    private RecyclerView recyclerView;

    private String mParam1;
    private String mParam2;
    private ParseAdapter parseAdapter;

    public LecturesFragment() {

    }

    public LecturesFragment(ArrayList<ParseItem> topics, ArrayList<ArrayList<ParseItem>> lectures) {
        this.topics = topics;
        this.lectures = lectures;
    }

    public static ArrayList<ParseItem> getLecturesArray(int position){
        return lectures.get(position);
    }


    public static LecturesFragment newInstance(String param1, String param2) {
        LecturesFragment fragment = new LecturesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lectures, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        parseAdapter = new ParseAdapter(topics, getContext(), "lectures");
        recyclerView.setAdapter(parseAdapter);

        return view;
    }
}