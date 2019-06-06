package com.krolseb.todolistapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krolseb.todolistapp.R;
import com.krolseb.todolistapp.model.Task;


public class RecyclerViewTaskAdapter extends RecyclerView.Adapter<RecyclerViewTaskViewHolder> {
    private ArrayList<Task> tasks;
    private Context context;

    public RecyclerViewTaskAdapter(Context context, ArrayList<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public RecyclerViewTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewTaskViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewTaskViewHolder recyclerViewTaskViewHolder, int position) {
        recyclerViewTaskViewHolder.getTitleTextView().setText(String.valueOf(tasks.get(position).getTitle()));
        recyclerViewTaskViewHolder.getDateTextView().setText(String.valueOf(tasks.get(position).getDate()));
        recyclerViewTaskViewHolder.getCategoryTextView().setText(String.valueOf(tasks.get(position).getCategory()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

}