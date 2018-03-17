package com.android.todolist.view.categoryItem;

import android.os.Bundle;

import com.android.todolist.model.categoryItem.CategoryItemEntity;

import java.util.List;

/**
 * Created by laxman on 16/3/18.
 */
public interface CategoryItemView {
    boolean validateIntentObjects();
    void setUpViews();
    void initialLoadingViaBundle();
    void setItems(List<CategoryItemEntity> items);
    void showRecyclerView(boolean shouldShow);
    void goToNextActivity(Bundle bundle);
}
