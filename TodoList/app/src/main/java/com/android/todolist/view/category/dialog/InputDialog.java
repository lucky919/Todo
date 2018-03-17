package com.android.todolist.view.category.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.todolist.concurrency.AppExecutors;
import com.android.todolist.R;
import com.android.todolist.data.DBHelper;

/**
 * Created by laxman on 16/3/18.
 */
public class InputDialog extends AlertDialog implements DialogView {

    private EditText et;
    private DialogCallback callback;
    private DialogPresenter dialogPresenter;
    private DBHelper dbHelper;
    private AppExecutors executors;

    public InputDialog(@NonNull Context context, DialogCallback callback, DBHelper dbHelper) {
        super(context);
        this.callback = callback;
        this.dbHelper = dbHelper;
        this.executors = AppExecutors.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setCancelable(true);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_dialog, null);
        setView(view);
        et = view.findViewById(R.id.user_input);
        et.requestFocus();

        dialogPresenter = new DialogInterfaceImpl(this, dbHelper, executors);
        setButton(AlertDialog.BUTTON_POSITIVE, getContext().getString(R.string.button_add), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        setButton(AlertDialog.BUTTON_NEGATIVE, getContext().getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input = et.getText().toString().trim().toLowerCase();
                        dialogPresenter.validate(input);
                    }
                });
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public void sendResult(String categoryName) {
        callback.onDialogResult(categoryName);
        dismiss();
    }

    @Override
    public void showErrorMessage(String message) {
        et.setError(message);
    }

    public interface DialogCallback {
        void onDialogResult(String categoryName);
    }
}
