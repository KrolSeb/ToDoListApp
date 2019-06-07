package com.krolseb.todolistapp.interfaces;

import android.widget.ArrayAdapter;

public interface AddTaskInterface {
    interface Presenter {
        void onCreate();
    }

    interface View {
        void setCategories();
        void setDateAndTime(String fullDate);
        void setSpinnerAdapter(ArrayAdapter spinnerElementsAdapter);
        void setSpinnerContent();
        void showToast(String message);
        void showDialog();
    }
}
