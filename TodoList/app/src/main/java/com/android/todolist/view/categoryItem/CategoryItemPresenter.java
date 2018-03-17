package com.android.todolist.view.categoryItem;

import com.android.todolist.model.categoryItem.CategoryItemEntity;

/**
 * Created by laxman on 16/3/18.
 */
public interface CategoryItemPresenter {
    void onCreate();
    void updateItem(CategoryItemEntity item);
    void add(CategoryItemEntity item);
    void listAllItems(long categoryId);
    void onItemClicked(CategoryItemEntity itemEntity);
}
