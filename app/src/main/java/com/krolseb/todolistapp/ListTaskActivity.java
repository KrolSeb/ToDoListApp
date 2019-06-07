package com.krolseb.todolistapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.krolseb.todolistapp.adapters.RecyclerViewTaskAdapter;
import com.krolseb.todolistapp.model.FileService;
import com.krolseb.todolistapp.model.Task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ListTaskActivity extends AppCompatActivity {
    private List<Task> tasks;
    private Context context;
    private FileService fileService;

    private RecyclerView recyclerView;
    @BindView(R.id.addNewTaskButton)
    protected Button addNewTaskButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);
        ButterKnife.bind(this);

        context = this;
        fileService = new FileService(context);
        tasks = new ArrayList<>();
        setRecyclerViewProperties();
        getTasksList();
    }

    @OnClick(R.id.addNewTaskButton)
    public void onAddNewTaskButtonClick() {
        Intent intent = new Intent(ListTaskActivity.this, AddTaskActivity.class);
        startActivity(intent);
        finish();
    }

    private void setRecyclerViewProperties() {
        recyclerView = findViewById(R.id.recycler_view_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getTasksList() {
        if (fileService.isFileExists()) {
            try {
                tasks = fileService.readTasksFromFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            setRecyclerViewContent();
        }
        else {
            showDialog();
        }
    }

    private void setRecyclerViewContent() {
        RecyclerViewTaskAdapter recyclerViewTaskAdapter = new RecyclerViewTaskAdapter(ListTaskActivity.this, (ArrayList<Task>) tasks);
        recyclerView.setAdapter(recyclerViewTaskAdapter);
    }

    private void showDialog() {
        AlertDialog alertDialog = createDialog();
        alertDialog.show();
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListTaskActivity.this);
        builder.setTitle(context.getResources().getString(R.string.dialog_no_tasks_title));
        builder.setMessage(context.getResources().getString(R.string.dialog_no_tasks_message));
        builder.setCancelable(false);
        builder.setPositiveButton(context.getResources().getString(R.string.dialog_positive_button_text), (dialog, which) -> dialog.cancel());

        return builder.create();
    }

}