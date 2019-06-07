package com.krolseb.todolistapp.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.krolseb.todolistapp.R;
import com.krolseb.todolistapp.adapters.RecyclerViewTaskAdapter;
import com.krolseb.todolistapp.interfaces.ListTaskInterface;
import com.krolseb.todolistapp.model.FileService;
import com.krolseb.todolistapp.model.Task;
import com.krolseb.todolistapp.view.AddTaskActivity;

import java.io.IOException;
import java.util.ArrayList;


public class ListTaskPresenter implements ListTaskInterface.Presenter {
    private ListTaskInterface.View view;
    private FileService fileService;
    private Context context;

    public ListTaskPresenter(ListTaskInterface.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onCreate() {
        this.fileService = new FileService(context);
    }

    public void getTasks() {
        if (fileService.isFileExists()) {
            try {
                RecyclerViewTaskAdapter recyclerViewTaskAdapter = new RecyclerViewTaskAdapter(context, (ArrayList<Task>) fileService.readTasksFromFile());
                view.setRecyclerViewContent(recyclerViewTaskAdapter);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            view.showDialog(createDialog());
        }
    }

    public Intent getIntent() {
        return new Intent(context, AddTaskActivity.class);
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.dialog_no_tasks_title));
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.dialog_no_tasks_message));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.dialog_positive_button_text),
                (dialog, which) -> dialog.cancel());

        return alertDialogBuilder.create();
    }

}