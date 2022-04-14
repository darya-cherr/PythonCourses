package com.example.pythonourses.Parser;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pythonourses.LectureTextActivity;
import com.example.pythonourses.LecturesActivity;
import com.example.pythonourses.LecturesFragment;
import com.example.pythonourses.MainActivity;
import com.example.pythonourses.R;

import java.util.ArrayList;

public class ParseAdapter extends RecyclerView.Adapter<ParseAdapter.ViewHolder> {

    public ArrayList<ParseItem> parseItems;
    private Context context;

    public ParseAdapter(ArrayList<ParseItem> parseItems, Context context){
        this.parseItems = parseItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ParseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParseAdapter.ViewHolder holder, int position) {
        ParseItem parseItem = parseItems.get(position);
        holder.number.setText(parseItem.getNumber());
        holder.title.setText(parseItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return parseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView number, title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.item_number);
            title = itemView.findViewById(R.id.item_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();

            if(context instanceof MainActivity){
                ArrayList<ParseItem> lectures = LecturesFragment.getLecturesArray(position);
                Intent intent = new Intent(context, LecturesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("topic", parseItems.get(position).getTitle());
                intent.putExtra("lectures", lectures);
                context.startActivity(intent);
            }else{
                if(context instanceof LecturesActivity){
                    ParseItem parseItem = parseItems.get(position);
                    Intent intent = new Intent(context, LectureTextActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.putExtra("topic", parseItem.getTitle());
                    intent.putExtra("lectureUrl",parseItem.getLectureUrl());
                    context.startActivity(intent);
                }
            }

        }
    }
}
