package com.android.todolist.view.addItem;

import android.content.Intent;

import com.android.todolist.concurrency.AppExecutors;
import com.android.todolist.Utils.Util;
import com.android.todolist.data.DBHelper;
import com.android.todolist.model.categoryItem.CategoryItemEntity;

/**
 * Created by laxman on 16/3/18.
 */
public class AdItemPresenterImpl implements AddItemPresenter {

    private DBHelper dbHelper;
    private AddItemView itemView;
    private AppExecutors executors;

    public AdItemPresenterImpl(DBHelper dbHelper, AddItemView itemView) {
        this.dbHelper = dbHelper;
        this.itemView = itemView;
        this.executors = AppExecutors.getInstance();
    }

    @Override
    public void onCreate() {
        if (itemView.validateIntentObject()) {
            itemView.setupViews();
            itemView.populateViews();
        }
    }

    @Override
    public void updateItem(final CategoryItemEntity item) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final int res = dbHelper.getCategoryItemDAO().update(item);
                executors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        itemView.onUpdateComplete(res > 0);
                    }
                });
            }
        });
    }

    @Override
    public void deleteItem(final long itemId) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final int res = dbHelper.getCategoryItemDAO().deleteById(itemId);
                executors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        itemView.onDeleteComplete(res > 0);
                    }
                });
            }
        });
    }

    @Override
    public void saveItem(final CategoryItemEntity item) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                dbHelper.getCategoryItemDAO().insert(item);
                executors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        itemView.onInsertComplete(true);
                    }
                });
            }
        });
    }

    @Override
    public void uploadImage() {
        if (itemView.checkPermissions()) {
            itemView.selectAndLoadImage();
        } else {
            itemView.requestStoragePermissions();
        }
    }

    @Override
    public void onStoragePermissionResponse(boolean result) {
        if (result) {
            itemView.selectAndLoadImage();
        } else {
            itemView.showMessage(Util.MSG_INSUFFICIENT_PERMISSIONS);
        }
    }

    @Override
    public void onImageSelectResponse(boolean result, Intent data) {
        if (result) {
            String imagePath = itemView.fetchImagePathFromIntent(data);
            if (imagePath != null) {
                itemView.inflateImage(imagePath);
            }
        }
    }
}
