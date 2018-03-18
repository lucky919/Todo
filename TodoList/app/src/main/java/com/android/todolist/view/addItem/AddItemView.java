package com.android.todolist.view.addItem;

import android.content.Intent;

/**
 * Created by laxman on 16/3/18.
 */
public interface AddItemView {
    boolean validateIntentObject();
    void setupViews();
    void populateViews();
    void showMessage(String msg);
    void onInsertComplete(boolean status);
    void onUpdateComplete(boolean status);
    void onDeleteComplete(boolean status);
    boolean checkPermissions();
    void requestStoragePermissions();
    void selectAndLoadImage();
    String fetchImagePathFromIntent(Intent data);
    void inflateImage(String imagePath);
    void showDeleteDialog(long itemId);
}
