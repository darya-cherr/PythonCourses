package com.example.pythonourses;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pythonourses.Parser.ParseAdapter;
import com.example.pythonourses.Parser.ParseItem;

import java.util.ArrayList;

public class TasksFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<ParseItem> parseItems = new ArrayList<>();

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ParseAdapter parseAdapter;

    public TasksFragment() {
    }

    public TasksFragment(ArrayList<ParseItem> parseItems) {
        this.parseItems = parseItems;
    }

    public static TasksFragment newInstance(String param1, String param2) {
        TasksFragment fragment = new TasksFragment();
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
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        recyclerView = view.findViewById(R.id.tasks_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        parseAdapter = new ParseAdapter(parseItems, getContext(), "tasks");
        recyclerView.setAdapter(parseAdapter);

        return view;
    }
}