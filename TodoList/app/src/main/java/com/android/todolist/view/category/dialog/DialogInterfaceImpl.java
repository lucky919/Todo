package com.android.todolist.view.category.dialog;

import android.text.TextUtils;

import com.android.todolist.concurrency.AppExecutors;
import com.android.todolist.utils.Util;
import com.android.todolist.data.DBHelper;
import com.android.todolist.model.category.CategoryEntity;

/**
 * Created by laxman on 16/3/18.
 */
public class DialogInterfaceImpl implements DialogPresenter {

    private DialogView dialogView;
    private DBHelper dbHelper;
    private AppExecutors executors;

    DialogInterfaceImpl(DialogView dialogView, DBHelper dbHelper, AppExecutors executors) {
        this.dialogView = dialogView;
        this.dbHelper = dbHelper;
        this.executors = executors;
    }

    @Override
    public void validate(final String categoryName) {
        if (TextUtils.isEmpty(categoryName)) {
            dialogView.showErrorMessage(Util.MSG_EMPTY_CATEGORY);
        } else {
            executors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final CategoryEntity entity = dbHelper.getCategoryDAO().getCategoryByName(categoryName);
                    executors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (entity == null) {
                                dialogView.sendResult(categoryName);
                            } else {
                                dialogView.showErrorMessage(Util.MSG_CATEGORY_EXISTS);
                            }
                        }
                    });
                }
            });
        }
    }
}
