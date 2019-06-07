package com.krolseb.todolistapp.interfaces;

import android.app.AlertDialog;
import com.krolseb.todolistapp.adapters.RecyclerViewTaskAdapter;

public interface ListTaskInterface {
    interface Presenter {
        void onCreate();
    }

    interface View {
        void setRecyclerViewProperties();
        void setRecyclerViewContent(RecyclerViewTaskAdapter recyclerViewTaskAdapter);
        void showTasksList();
        void showDialog(AlertDialog alertDialog);
    }
}
