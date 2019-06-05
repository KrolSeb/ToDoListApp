package com.krolseb.todolistapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krolseb.todolistapp.R;
import com.krolseb.todolistapp.model.SingleTask;


public class TasksAdapter extends RecyclerView.Adapter<TasksViewHolder> {
    private ArrayList<SingleTask> tasks;
    private Context context;

    public TasksAdapter(Context context, ArrayList<SingleTask> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TasksViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder tasksViewHolder, int position) {
        tasksViewHolder.getTitleTextView().setText(String.valueOf(tasks.get(position).getTitle()));
        tasksViewHolder.getDateTextView().setText(String.valueOf(tasks.get(position).getDate()));
        tasksViewHolder.getCategoryTextView().setText(String.valueOf(tasks.get(position).getCategory()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

}