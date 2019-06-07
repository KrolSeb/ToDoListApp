package com.krolseb.todolistapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.krolseb.todolistapp.R;
import com.krolseb.todolistapp.interfaces.AddTaskInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.krolseb.todolistapp.presenter.AddTaskPresenter;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;


public class AddTaskActivity extends AppCompatActivity implements AddTaskInterface.View {
    @BindView(R.id.titleEditText)
    protected TextInputEditText titleEditText;
    @BindView(R.id.dateAndTimeEditText)
    protected TextInputEditText dateAndTimeEditText;
    @BindView(R.id.datePickerButton)
    protected Button datePickerButton;
    @BindView(R.id.timePickerButton)
    protected Button timePickerButton;
    @BindView(R.id.categoriesSpinner)
    protected Spinner categoriesSpinner;
    @BindView(R.id.addTaskButton)
    protected Button addTaskButton;
    @BindView(R.id.abortButton)
    protected Button abortButton;

    private AddTaskPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);

        presenter = new AddTaskPresenter(this, AddTaskActivity.this);
        presenter.onCreate();

        setCategories();
        setSpinnerContent();
    }

    @Override
    public void setCategories() {
        presenter.setCategories();
    }

    @Override
    public void setSpinnerContent() {
        presenter.getSpinnerAdapter();
    }

    @Override
    public void setSpinnerAdapter(ArrayAdapter spinnerElementsAdapter) {
        categoriesSpinner.setAdapter(spinnerElementsAdapter);
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.setCategory(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    @OnClick(R.id.datePickerButton)
    public void onButtonDatePickerClick() {
        presenter.getDateAndTime(AddTaskActivity.this);
    }

    @Override
    public void setDateAndTime(String fullDate) {
        dateAndTimeEditText.setText(fullDate);
    }

    @OnClick(R.id.timePickerButton)
    public void onButtonTimePickerClick() {
        presenter.getTimeAndDate(AddTaskActivity.this);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDialog() {
        presenter.createDialog().show();
    }

    @OnClick(R.id.addTaskButton)
    public void onAddTaskButtonClick() {
        presenter.setTitle(String.valueOf(titleEditText.getText()));
        presenter.setDate(String.valueOf(dateAndTimeEditText.getText()));
        presenter.addTask();
    }

    @OnClick(R.id.abortButton)
    public void onAbortButtonClick() {
        finish();
    }

}