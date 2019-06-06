package com.krolseb.todolistapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.krolseb.todolistapp.adapters.TasksAdapter;
import com.krolseb.todolistapp.model.SingleTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    private static final String DATA_READ_ATTRIBUTES_SEPARATOR = "\\|";
    private static final String FILE_WITH_TASKS = "tasks.txt";

    private List<SingleTask> tasks;
    private Context context;

    private RecyclerView recyclerView;
    @BindView(R.id.addNewTaskButton)
    protected Button addNewTaskButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;
        tasks = new ArrayList<>();
        setRecyclerViewProperties();
        getTasksList();
    }

    @OnClick(R.id.addNewTaskButton)
    public void onAddNewTaskButtonClick() { }

    private void setRecyclerViewProperties() {
        recyclerView = findViewById(R.id.recycler_view_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getTasksList() {
        File file = new File(getApplicationContext().getFilesDir(),FILE_WITH_TASKS);
        if (file.exists() && !(file.length() == 0)) {
            try {
                getTasksFromFile();
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
        TasksAdapter tasksAdapter = new TasksAdapter(MainActivity.this, (ArrayList<SingleTask>) tasks);
        recyclerView.setAdapter(tasksAdapter);
    }

    public void getTasksFromFile() throws IOException {
        tasks.clear();

        FileInputStream fileInputStream = openFileInput(FILE_WITH_TASKS);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        while((line = bufferedReader.readLine()) != null){
            String [] separatedAttributes = line.split(DATA_READ_ATTRIBUTES_SEPARATOR);
            SingleTask singleTask = new SingleTask();
            singleTask.setTitle(separatedAttributes[0]);
            singleTask.setDate(separatedAttributes[1]);
            singleTask.setCategory(separatedAttributes[2]);
            tasks.add(singleTask);
        }

        fileInputStream.close();
    }

    private void showDialog() {
        AlertDialog alertDialog = createDialog();
        alertDialog.show();
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(context.getResources().getString(R.string.dialog_no_tasks_title));
        builder.setMessage(context.getResources().getString(R.string.dialog_no_tasks_message));
        builder.setCancelable(false);
        builder.setPositiveButton(context.getResources().getString(R.string.dialog_positive_button_text), (dialog, which) -> dialog.cancel());

        return builder.create();
    }

}