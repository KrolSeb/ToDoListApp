package com.krolseb.todolistapp.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;

import com.krolseb.todolistapp.R;
import com.krolseb.todolistapp.adapters.RecyclerViewTaskAdapter;
import com.krolseb.todolistapp.interfaces.ListTaskInterface;
import com.krolseb.todolistapp.presenter.ListTaskPresenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ListTaskActivity extends AppCompatActivity implements ListTaskInterface.View{
    private RecyclerView recyclerView;
    @BindView(R.id.addNewTaskButton)
    protected Button addNewTaskButton;

    private ListTaskPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);
        ButterKnife.bind(this);
        setRecyclerViewProperties();

        presenter = new ListTaskPresenter(this, ListTaskActivity.this);
        presenter.onCreate();
        showTasksList();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showTasksList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setRecyclerViewProperties() {
        recyclerView = findViewById(R.id.recycler_view_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setRecyclerViewContent(RecyclerViewTaskAdapter recyclerViewTaskAdapter) {
        recyclerView.setAdapter(recyclerViewTaskAdapter);
    }

    @Override
    public void showTasksList() {
        presenter.getTasks();
    }

    @Override
    public void showDialog(AlertDialog alertDialog) {
        alertDialog.show();
    }

    @OnClick(R.id.addNewTaskButton)
    public void onAddNewTaskButtonClick() {
        startActivity(presenter.getIntent());
    }

}