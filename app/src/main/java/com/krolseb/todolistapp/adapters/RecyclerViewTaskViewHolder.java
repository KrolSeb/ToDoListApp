package com.krolseb.todolistapp.adapters;

import com.krolseb.todolistapp.R;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewTaskViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private TextView dateTextView;
    private TextView categoryTextView;

    RecyclerViewTaskViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
        categoryTextView = itemView.findViewById(R.id.categoryTextView);
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public TextView getDateTextView() {
        return dateTextView;
    }

    public TextView getCategoryTextView() {
        return categoryTextView;
    }
}
