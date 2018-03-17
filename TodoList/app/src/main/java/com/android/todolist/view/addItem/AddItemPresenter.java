package com.android.todolist.view.addItem;

import android.content.Intent;

import com.android.todolist.model.categoryItem.CategoryItemEntity;

/**
 * Created by laxman on 16/3/18.
 */
public interface AddItemPresenter {
    void onCreate();
    void updateItem(CategoryItemEntity item);
    void deleteItem(long itemId);
    void saveItem(CategoryItemEntity item);
    void uploadImage();
    void onStoragePermissionResponse(boolean result);
    void onImageSelectResponse(boolean result, Intent data);
}
