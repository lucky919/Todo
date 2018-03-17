package com.android.todolist.view.category.dialog;

/**
 * Created by laxman on 16/3/18.
 */
public interface DialogView {

    void sendResult(String categoryName);
    void showErrorMessage(String message);

}
